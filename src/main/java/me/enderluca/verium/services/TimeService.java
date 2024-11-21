package me.enderluca.verium.services;

import me.enderluca.verium.listener.TimeListener;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnegative;

/**
 * Manages the in-game time, not to be confused with TimerService.
 */
public class TimeService {

    protected boolean paused;

    protected boolean frozen;

    //The time the world is frozen at as specified by the player
    protected long frozenTime = 0;


    public TimeService(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        TimeListener timeListener = new TimeListener(this::updateTime);
        Bukkit.getPluginManager().registerEvents(timeListener, owner);
    }

    public boolean isFrozen(){
        return frozen;
    }

    public void setFrozen(boolean frozen){
        this.frozen = frozen;
        updateTime();
    }


    public boolean isPaused(){
        return paused;
    }

    public void setPaused(boolean paused){
        this.paused = paused;
        updateTime();
    }

    public void updateTime(){
        for(World world : Bukkit.getWorlds()){
            updateTime(world);
        }
    }

    public void updateTime(World world){
        if(frozen)
            world.setTime(frozenTime);

        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, !(frozen || paused));
    }

    public void setTime(@Nonnegative long ticks){
        long realTicks = ticks % 24000;
        for(World world : Bukkit.getWorlds()){
            world.setTime(realTicks);
        }

        if(frozen)
            frozenTime = ticks;
    }

    public void setTime(int hours, int minutes, int seconds){
        double time = (hours * 1000) + (minutes * 16.666666666666667) + (seconds * 0.2777777777777778) + 24000 - 6000;
        setTime(Math.round(time % 24000));
    }

    public void loadConfig(FileConfiguration src){
        paused = src.getBoolean("time.paused", false);
        frozen = src.getBoolean("time.frozen", false);
        frozenTime = src.getLong("time.frozenTime", 0);
        updateTime();
    }

    public void saveConfig(FileConfiguration dest){
        dest.set("time.paused", paused);
        dest.set("time.frozen", frozen);
        dest.set("time.frozenTime", frozenTime);
    }
}