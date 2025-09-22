package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class NoMobDropModifier implements GameModifier, Listener {

    private boolean enabled;
    private boolean paused;

    public NoMobDropModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

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
        enabled = src.getBoolean("modifiers.nomobdrop.enabled", false);
        paused = src.getBoolean("modifiers.nomobdrop.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.nomobdrop.enabled", enabled);
        dest.set("modifiers.nomobdrop.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { } //No world specific data

    @Override
    public GameModifierType getType() {
        return GameModifierType.NoMobDrop;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityDeath(@Nonnull EntityDeathEvent event){
        if(enabled && !paused)
            return;
        if(event.getEntity() instanceof Player)
            return;
        event.getDrops().clear();

        //NOTE: EntityDropItemEvent is not usable because it also called when items are dropped for example when trading with piglins
    }
}
