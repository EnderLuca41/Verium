package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class NoHungerModifier implements GameModifier, Listener {

    private boolean enabled;
    private boolean paused;

    public NoHungerModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(this, owner);
    }

    @Override
    public void setEnabled(boolean val){
        enabled = val;

        if(!val || paused)
            return;

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setFoodLevel(20);
        }
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }


    @Override
    public void setPaused(boolean val){
        paused = val;

        if(val || !enabled)
            return;

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setFoodLevel(20);
        }
    }

    @Override
    public boolean isPaused() {
        return paused;
    }


    @Override
    public void loadConfig(FileConfiguration src){
        enabled = src.getBoolean("modifiers.nohunger.enabled", false);
        paused = src.getBoolean("modifiers.nohunger.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest){
        dest.set("modifiers.nohunger.enabled", enabled);
        dest.set("modifiers.nohunger.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { }

    @Override
    public GameModifierType getType(){
        return GameModifierType.NoHunger;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        if(enabled && !paused)
            return;

        event.setCancelled(true);
        event.getEntity().setFoodLevel(20); //Just to be sure
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event){
        if(enabled && !paused)
            return;

        event.getPlayer().setFoodLevel(20);
    }
}
