package me.enderluca.verium.services.gamerules;

import me.enderluca.verium.GameruleType;
import me.enderluca.verium.interfaces.Gamerule;

import me.enderluca.verium.listener.PvpListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class PvpGamerule implements Gamerule {

    private boolean enabled;
    private boolean paused;

    public PvpGamerule(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new PvpListener(() -> enabled && !paused), owner);
    }

    @Override
    public void setEnabled(boolean val) {
        enabled = val;
        updateWorlds();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    @Override
    public void setPaused(boolean val) {
        this.paused = val;
        updateWorlds();
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    /**
     * Updates the world with setPVP
     */
    private void updateWorlds(){
        for(World w : Bukkit.getWorlds()){
            w.setPVP(enabled && !paused);
        }
    }

    @Override
    public void loadConfig(FileConfiguration src){
        enabled = src.getBoolean("gamerules.pvp.enabled", false);
        paused = src.getBoolean("gamerules.pvp.paused", false);

        updateWorlds();
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("gamerules.pvp.enabled", enabled);
        dest.set("gamerules.pvp.paused", paused);
    }

    @Override
    public void cleanWoldSpecificConfig(FileConfiguration dest) {
        dest.set("gamerules.pvp.paused", null);
    }

    @Override
    public GameruleType getType() {
        return GameruleType.Pvp;
    }
}
