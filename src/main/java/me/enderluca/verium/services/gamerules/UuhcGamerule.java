package me.enderluca.verium.services.gamerules;

import me.enderluca.verium.GameruleType;
import me.enderluca.verium.interfaces.Gamerule;
import me.enderluca.verium.listener.gamerules.UuhcListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class UuhcGamerule implements Gamerule {

    private boolean enabled;
    private boolean paused;

    public UuhcGamerule(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new UuhcListener(() -> enabled && !paused), owner);
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
        enabled = src.getBoolean("gamerules.uuhc.enabled", false);
        paused = src.getBoolean("gamerules.uuhc.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("gamerules.uuhc.enabled", enabled);
        dest.set("gamerules.uuhc.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { }


    @Override
    public GameruleType getType(){
        return GameruleType.Uuhc;
    }
}
