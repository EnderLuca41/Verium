package me.enderluca.verium.services.gamerules;

import me.enderluca.verium.GameruleType;
import me.enderluca.verium.interfaces.Gamerule;
import me.enderluca.verium.listener.gamerules.NoVillagerListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class NoVillagerGamerule implements Gamerule {

    private boolean enabled;
    private boolean paused;

    public NoVillagerGamerule(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new NoVillagerListener(() -> enabled && !paused), owner);
    }

    @Override
    public void setEnabled(boolean val){
        enabled = val;
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }


    @Override
    public void setPaused(boolean val){
        paused = val;
    }

    @Override
    public boolean isPaused(){
        return paused;
    }


    @Override
    public void loadConfig(FileConfiguration src){
        enabled = src.getBoolean("gamerules.novillager.enabled", false);
        paused = src.getBoolean("gamerules.novillager.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest){
        dest.set("gamerules.novillager.enabled", enabled);
        dest.set("gamerules.novillager.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest){ }

    @Override
    public GameruleType getType(){
        return GameruleType.NoVillager;
    }
}
