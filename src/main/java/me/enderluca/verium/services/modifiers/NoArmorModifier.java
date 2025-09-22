package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;

import me.enderluca.verium.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class NoArmorModifier implements GameModifier, Listener {

    private boolean enabled;
    private boolean paused;

    public NoArmorModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(this, owner);
    }


    @Override
    public void setEnabled(boolean val){
        if(val == enabled)
            return;

        enabled = val;

        if(enabled && !paused)
            resetArmor();
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }


    @Override
    public void setPaused(boolean val){
        if(val == paused)
            return;

        paused = val;

        if(!paused && enabled)
            resetArmor();
    }

    @Override
    public boolean isPaused(){
        return paused;
    }


    @Override
    public void loadConfig(FileConfiguration src){
        enabled = src.getBoolean("modifiers.noarmor.enabled", false);
        paused = src.getBoolean("modifiers.noarmor.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest){
        dest.set("modifiers.noarmor.enabled", enabled);
        dest.set("modifiers.noarmor.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest){ }


    /**
     * Drops all equipped armor of all players at their location
     */
    private void resetArmor(){
        ItemStack[] emptyArmorSlots = {null, null, null, null};

        for(Player p : Bukkit.getOnlinePlayers()){
            for(ItemStack item : p.getInventory().getContents()){
                if(Objects.isNull(item))
                    continue;

                if(!ItemUtil.isArmor(item))
                    continue;

                p.getInventory().remove(item);
                p.getWorld().dropItem(p.getLocation(), item);
            }

            ItemStack[] emptyArmor = {null, null, null, null};
            p.getInventory().setArmorContents(emptyArmor);
        }


    }


    @Override
    public GameModifierType getType(){
        return GameModifierType.NoArmor;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true) //High priority to ensure the player cannot obtain armor but to also allow other plugins to cancel the event
    public void onItemPickup(EntityPickupItemEvent event){
        if(enabled && !paused)
            return;

        if(!(event.getEntity() instanceof Player))
            return;

        if(!ItemUtil.isArmor(event.getItem()))
            return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event){
        if(enabled && !paused)
            return;

        if(Objects.isNull(event.getCurrentItem()))
            return;

        if(!ItemUtil.isArmor(event.getCurrentItem()))
            return;

        event.setCancelled(true);
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event){
        if(enabled && !paused)
            return;

        if(Objects.isNull(event.getCursor()))
            return;

        if(!ItemUtil.isArmor(event.getCursor()))
            return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onManipulateArmorStand(PlayerArmorStandManipulateEvent event){
        if(enabled && !paused)
            return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJon(PlayerJoinEvent event){
        if(enabled && !paused)
            return;

        Player p = event.getPlayer();

        for(ItemStack item : p.getInventory().getContents()){
            if(Objects.isNull(item))
                continue;

            if(!ItemUtil.isArmor(item))
                continue;

            p.getInventory().remove(item);
            p.getWorld().dropItem(p.getLocation(), item);
        }

        ItemStack[] emptyArmor = {null, null, null, null};
        p.getInventory().setArmorContents(emptyArmor);
    }
}
