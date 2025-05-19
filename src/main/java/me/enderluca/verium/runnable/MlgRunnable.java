package me.enderluca.verium.runnable;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

public class MlgRunnable extends BukkitRunnable {

    private final boolean allPlayers;
    private final int height;
    private final int heightVariation;
    @Nonnull
    private final Consumer<List<Player>> mlgStarted;

    @Nullable
    private Map<UUID, ItemStack[]> inventories;
    @Nullable
    private Map<UUID, Location> originalLocations;

    @Nonnull
    private final Material[] MLG_TYPES = {
            Material.WATER_BUCKET,
            Material.COBWEB,
            Material.SLIME_BLOCK
    };

    /**
     * @param allPlayers If true, all players will need to do an MLG, if false only one random player will need to do it
     */
    public MlgRunnable(boolean allPlayers, int height, int heightVariation, @Nonnull Consumer<List<Player>> mlgStarted){
        this.allPlayers = allPlayers;
        this.height = height;
        this.heightVariation = heightVariation;
        this.mlgStarted = mlgStarted;
    }

    @Override
    public void run() {
        World mlgWorld = Bukkit.getWorld("mlg");
        if(Objects.isNull(mlgWorld)){
            Bukkit.getLogger().log(Level.WARNING, "MLG world not found.");
            return;
        }

        List<Player> players = new ArrayList<>();
        if(!allPlayers){
            int num = Bukkit.getOnlinePlayers().size();
            Random rand = new Random();
            int randIndex = rand.nextInt(num);
            players.add((Player) Bukkit.getOnlinePlayers().toArray()[randIndex]);
        }
        else {
            players.addAll(Bukkit.getOnlinePlayers());
        }

        //Save Inventory and locations
        inventories = new HashMap<>();
        originalLocations = new HashMap<>();
        for(Player player : players){
            inventories.put(player.getUniqueId(), player.getInventory().getContents());
            player.getInventory().clear();
            originalLocations.put(player.getUniqueId(), player.getLocation());
        }

        int yLoc = -60 + height + (int) (Math.random() * heightVariation);
        Location mlgLoc = new Location(mlgWorld, 0, yLoc, 0);
        for(Player player : players) {
            Material type = MLG_TYPES[(int) (Math.random() * MLG_TYPES.length)];
            player.teleport(mlgLoc);
            player.setVelocity(new Vector(0, 0, 0));
            player.setFallDistance(0);
            player.getInventory().setHeldItemSlot(0);
            player.getInventory().setItem(0, new ItemStack(type));
            mlgLoc.add(4, 0, 0); //So that players don't fall on top of each other
        }

        mlgStarted.accept(players);
    }

    /**
     * Restores the inventories of the players after the MLG, and teleports them back to their original location
     */
    public void resetPlayers(){
        if(inventories == null)
            return;

        for(Map.Entry<UUID, ItemStack[]> entry : inventories.entrySet()){
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player == null)
                continue;

            ItemStack[] inventory = entry.getValue();
            player.getInventory().clear();
            player.getInventory().setContents(inventory);
            assert originalLocations != null;
            player.teleport(originalLocations.get(entry.getKey()));
            player.setVelocity(new Vector(0, 0, 0));
        }
    }
}
