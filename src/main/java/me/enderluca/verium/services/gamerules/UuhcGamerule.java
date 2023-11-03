package me.enderluca.verium.services.gamerules;

import me.enderluca.verium.GameruleType;
import me.enderluca.verium.interfaces.Gamerule;
import me.enderluca.verium.listener.UuhcListener;
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
        src.set("gamerules.uuhc.enabled", enabled);
        src.set("gamerules.uuhc.paused", paused);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        enabled = dest.getBoolean("gamerules.uuhc.enabled", false);
        paused = dest.getBoolean("gamerules.uuhc.paused", false);
    }

    @Override
    public void cleanWoldSpecificConfig(FileConfiguration dest) {
        dest.set("gamerules.uuhc.paused", null);
    }


    @Override
    public GameruleType getType(){
        return GameruleType.Uuhc;
    }
}
