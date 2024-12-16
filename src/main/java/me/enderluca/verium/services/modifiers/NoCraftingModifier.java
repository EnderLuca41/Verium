package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.listener.challenges.NoCraftingListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class NoCraftingModifier implements GameModifier {

    private boolean enabled;
    private boolean paused;

    public NoCraftingModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new NoCraftingListener(() -> enabled && !paused), owner);
    }

    @Override
    public void setEnabled(boolean val) {
        enabled = val;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setPaused(boolean val) {
        paused = val;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public GameModifierType getType() {
        return GameModifierType.NoCrafting;
    }

    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("modifiers.nocrafting.enabled", false);
        paused = src.getBoolean("modifiers.nocrafting.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.modifiers.enabled", enabled);
        dest.set("modifiers.modifiers.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { }
}
