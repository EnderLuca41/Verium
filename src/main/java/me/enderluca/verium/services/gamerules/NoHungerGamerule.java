package me.enderluca.verium.services.gamerules;

import me.enderluca.verium.GameruleType;
import me.enderluca.verium.interfaces.Gamerule;

import me.enderluca.verium.listener.NoHungerListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NoHungerGamerule implements Gamerule {

    private boolean enabled;
    private boolean paused;

    public NoHungerGamerule(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new NoHungerListener(() -> enabled && !paused), owner);
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
        enabled = src.getBoolean("gamerules.nohunger.enabled", false);
        paused = src.getBoolean("gamerules.nohunger.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest){
        dest.set("gamerules.nohunger.enabled", enabled);
        dest.set("gamerules.nohunger.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) {
        dest.set("gamerules.nohunger.paused", null);
    }

    @Override
    public GameruleType getType(){
        return GameruleType.NoHunger;
    }
}
