package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Modifier that enables soup PVP, meaning soups can be consumed to instantly heal
 */
public class SoupModifier implements GameModifier, Listener {

    private boolean enabled;
    private boolean paused;

    public final int HEAL_AMOUNT = 4; //Amount on soup heals

    public SoupModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);
        owner.getServer().getPluginManager().registerEvents(this, owner);
    }

    void onSoupConsumed(Player player, int slot){
        player.getInventory().setItem(slot, new ItemStack(Material.BOWL));
        PlayerUtil.heal(player, HEAL_AMOUNT);
    }

    @Override
    public void setEnabled(boolean val){
        enabled = val;
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }

    @Override
    public void setPaused(boolean val){
        paused = val;
    }

    @Override
    public boolean isPaused(){
        return paused;
    }

    @Override
    public GameModifierType getType() {
        return GameModifierType.Soup;
    }

    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("modifiers.soup.enabled", false);
        paused = src.getBoolean("modifiers.soup.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.soup.enabled", enabled);
        dest.set("modifiers.soup.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { } //No world specific config for this modifier

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event){
        if(enabled && !paused)
            return;

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
            return;

        if(event.getPlayer().getInventory().getItemInMainHand().getType() != Material.MUSHROOM_STEW)
            return;

        event.setCancelled(true);
        event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.BOWL));
        PlayerUtil.heal(event.getPlayer(), HEAL_AMOUNT);
    }
}
