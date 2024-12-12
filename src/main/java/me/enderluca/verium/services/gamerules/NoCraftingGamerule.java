package me.enderluca.verium.services.gamerules;

import me.enderluca.verium.GameruleType;
import me.enderluca.verium.interfaces.Gamerule;
import me.enderluca.verium.listener.challenges.NoCraftingListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class NoCraftingGamerule implements Gamerule {

    private boolean enabled;
    private boolean paused;

    public NoCraftingGamerule(Plugin owner, FileConfiguration fileConfig){
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
    public GameruleType getType() {
        return GameruleType.NoCrafting;
    }

    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("gamerules.nocrafting.enabled", false);
        paused = src.getBoolean("gamerules.nocrafting.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("gamerules.nocrafting.enabled", enabled);
        dest.set("gamerules.nocrafting.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { }
}
