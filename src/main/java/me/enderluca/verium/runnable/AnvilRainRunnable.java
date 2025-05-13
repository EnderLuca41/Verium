package me.enderluca.verium.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.function.BooleanSupplier;

public class AnvilRainRunnable implements Runnable {

    private final int amount;
    private final int radius;
    private final int height;
    private final int heightVariation;

    private final BooleanSupplier isActive;
    private Random rand;


    /**
     *
     * @param amount Amount of anvils per interval
     * @param radius Radius around each player where the anvils will fall
     * @param height Height where the anvils will spawn relative to the player
     * */
    public AnvilRainRunnable(int amount, int radius, int height, int heightVariation, BooleanSupplier isActive) {
        this.amount = amount;
        this.radius = radius;
        this.height = height;
        this.heightVariation = heightVariation;
        this.isActive = isActive;
        this.rand = new Random();
    }

    @Override
    public void run() {
        if(!isActive.getAsBoolean())
            return;

        for(Player p : Bukkit.getOnlinePlayers()){
            Location loc = p.getLocation();
            int posY = loc.getBlockY() + height;
            for(int i = 0; i < amount; i++){
                int randX = rand.nextInt(loc.getBlockX() - radius, loc.getBlockX() + radius + 1);
                int randZ = rand.nextInt(loc.getBlockZ() - radius, loc.getBlockZ() + radius + 1);
                Location anvilLoc = new Location(loc.getWorld(), randX, posY, randZ + rand.nextInt(-heightVariation, heightVariation + 1));
                if(anvilLoc.getBlock().isEmpty()){
                    anvilLoc.getBlock().setType(org.bukkit.Material.ANVIL);
                }
            }
        }

        Bukkit.broadcastMessage("Interval spawned");
    }
}
