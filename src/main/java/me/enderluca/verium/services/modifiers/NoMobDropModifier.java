package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.listener.modifiers.NoMobDropListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class NoMobDropModifier implements GameModifier {

    private boolean enabled;
    private boolean paused;

    public NoMobDropModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        owner.getServer().getPluginManager().registerEvents(new NoMobDropListener(() -> enabled && !paused), owner);
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
}
