package me.enderluca.verium.services;

import me.enderluca.verium.commands.HealthCommand;
import me.enderluca.verium.listener.HealthpointsListener;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnegative;

/**
 * Service that manages the max hp of the players
 */
public class HealthpointsService {

    public double maxHp;

    public HealthpointsService(JavaPlugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new HealthpointsListener(this::getMaxHp), owner);

        owner.getCommand("setmaxhealth").setExecutor(new HealthCommand(this::setMaxHp));
    }

    public void setMaxHp(@Nonnegative double val){
        maxHp = val;
        applyMaxHp();
    }

    public double getMaxHp(){
        return maxHp;
    }

    public void loadConfig(FileConfiguration src){
        setMaxHp(src.getInt("maxhp", 20));
    }

    public void saveConfig(FileConfiguration dest){
        dest.set("maxhp", maxHp);
    }

    private void applyMaxHp(){
        for(Player p : Bukkit.getOnlinePlayers()){
            //Exception impossible since player entity has this attribute
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHp);
        }
    }
}
