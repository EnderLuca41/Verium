package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.IntVector;
import me.enderluca.verium.interfaces.GameModifier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Modifiers that puts player items into a chest upon death.
 */
public class GravestoneModifier implements GameModifier, Listener {

    private static final int GRAVESTONE_SIZE = 54;

    private boolean enabled;
    private boolean paused;

    //
    // As of the current implementation the dimension (World.Environment) is not stored together with the position.
    // This has the potential that a new gravestone will override the data of an old one in a different dimension.
    // Also opening chest at the same coordinates in a different dimension as a gravestone will open the gravestone inventory.
    // However, all this is very unlikely meaning it will not get fixed.
    //

    Map<IntVector, Inventory> gravestones = new HashMap<>();

    public GravestoneModifier(Plugin owner, FileConfiguration fileConfig) {
        loadConfig(fileConfig);
        Bukkit.getPluginManager().registerEvents(this, owner);
    }

    @Override
    public void setEnabled(boolean val) {
        this.enabled = val;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setPaused(boolean val) {
        this.paused = val;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("modifiers.gravestone.enabled", false);
        paused = src.getBoolean("modifiers.gravestone.paused", false);
        String basePath = "modifiers.gravestone.gravestones.";
        if(!src.contains(basePath))
            return;

        for(String key : src.getConfigurationSection(basePath).getKeys(false)){
            String path = basePath + key;
            ItemStack[] contents = new ItemStack[GRAVESTONE_SIZE];

            for(int i = 0; ; i++){
                if(!src.contains(path + "." + i))
                    break;
                contents[i] = src.getItemStack(path + "." + i);
            }

            Inventory inv = Bukkit.createInventory(null, GRAVESTONE_SIZE, "Gravestone " + key);
            inv.setContents(contents);
            gravestones.put(IntVector.fromString(key), inv);
        }

    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.gravestone.enabled", enabled);
        dest.set("modifiers.gravestone.paused", paused);
        String basePath = "modifiers.gravestone.gravestones.";
        dest.set("modifiers.gravestone.gravestones", null); //Clear old data
        for(Map.Entry<IntVector, Inventory> entry : gravestones.entrySet()){
            String path = basePath + entry.getKey().toString() + ".";

            int i = 0;
            for(ItemStack item : entry.getValue().getContents()){
                dest.set(path + i, item);
                i++;
            }
        }
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) {
        dest.set("modifiers.gravestone.gravestones", null);
    }

    @Override
    public GameModifierType getType() {
        return GameModifierType.Gravestone;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event){
        if(!enabled || paused)
            return;

        World world = event.getEntity().getWorld();

        IntVector pos = new IntVector(event.getEntity().getLocation().toVector());
        int posY = pos.y();
        //Account for location being above the build limit or in the void
        if(posY > world.getMaxHeight() - 1)
            posY = world.getMaxHeight() - 1;
        else if(posY < world.getMinHeight())
            posY = world.getMinHeight();
        pos = new IntVector(pos.x(), posY, pos.z());

        Inventory gravestoneInv = Bukkit.createInventory(null, GRAVESTONE_SIZE, "Gravestone " + pos);

        for(ItemStack item : event.getDrops()) {
            gravestoneInv.addItem(item);
        }

        event.getDrops().clear();

        gravestones.put(new IntVector(pos.x(), pos.y(), pos.z()), gravestoneInv);

        world.getBlockAt(new Location(world, pos.x(), pos.y(), pos.z())).setType(Material.CHEST);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!enabled || paused)
            return;
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.CHEST) //Null impossible since we just checked hasBlock()
            return;

        IntVector pos = new IntVector(event.getClickedBlock().getLocation().toVector());
        Inventory inv = gravestones.getOrDefault(pos, null);

        if (inv == null)
            return;

        //Check if Click is a right click
        if(!(event.getAction() == Action.RIGHT_CLICK_BLOCK))
            return;

        event.getPlayer().openInventory(inv);
        event.getPlayer().getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), pos.x(), pos.y(), pos.z())).setType(Material.AIR);
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event){
        if(!enabled || paused)
            return;

        IntVector pos = new IntVector(event.getBlock().getLocation().toVector());
        Inventory inv = gravestones.getOrDefault(pos, null);

        if(inv == null)
            return;

        for(ItemStack item : inv.getContents()){
            if(item == null || item.getType() == Material.AIR)
                continue;
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
        }

        event.setDropItems(false); //Chest does not drop
        gravestones.remove(pos);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event){
        if(!enabled || paused)
            return;

        String playerName = event.getPlayer().getName();

        IntVector pos = null;
        for(Map.Entry<IntVector, Inventory> entry : gravestones.entrySet()){
            if(entry.getValue().getViewers().isEmpty())
                continue;
            if(entry.getValue().getViewers().getFirst().getName().equals(playerName)){
                pos = entry.getKey();
                break;
            }
        }

        if(pos == null)
            return;

        Inventory inv = gravestones.get(pos);
        for(ItemStack item : inv.getContents()){
            if(item == null || item.getType() == Material.AIR)
                continue;
            event.getPlayer().getLocation().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item); //Player location is inside a world, so getWorld() is safe
        }

        gravestones.remove(pos);
    }
}
