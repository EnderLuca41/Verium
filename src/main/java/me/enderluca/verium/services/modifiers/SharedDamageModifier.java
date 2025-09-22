package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class SharedDamageModifier implements GameModifier, Listener {

    private boolean enabled;
    private boolean paused;

    public SharedDamageModifier(Plugin owner, FileConfiguration config){
        loadConfig(config);

        owner.getServer().getPluginManager().registerEvents(this, owner);
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
        enabled = src.getBoolean("modifiers.shareddamage.enabled", false);
        paused = src.getBoolean("modifiers.shareddamage.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.shareddamage.enabled", enabled);
        dest.set("modifiers.shareddamage.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { } //No world specific data

    @Override
    public GameModifierType getType() {
        return GameModifierType.SharedDamage;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerDamage(EntityDamageEvent event){
        if(enabled && !paused)
            return;

        if(!(event.getEntity() instanceof Player player))
            return;

        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getUniqueId() == player.getUniqueId())
                continue;

            double newHealth = p.getHealth() - event.getFinalDamage();
            if (newHealth < 0)
                newHealth = 0;
            p.setHealth(newHealth); //Does not invoke damage event
        }
    }
}
