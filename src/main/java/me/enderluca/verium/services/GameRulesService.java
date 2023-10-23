package me.enderluca.verium.services;

import me.enderluca.verium.GameRulesConfig;
import me.enderluca.verium.listener.NoHungerListener;
import me.enderluca.verium.listener.PvPListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GameRulesService {
    private final GameRulesConfig config;
    public GameRulesService(Plugin owner, FileConfiguration fileConfig){
        config = new GameRulesConfig();

        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new NoHungerListener(config), owner);
        Bukkit.getPluginManager().registerEvents(new PvPListener(config), owner);
    }


    public void saveConfig(FileConfiguration dest){
        dest.set("gamerules.nohunger", config.getNoHunger());
        dest.set("gamerules.pvp", config.getPvp());
    }
    public void loadConfig(FileConfiguration src){
        config.setNoHunger(src.getBoolean("gamerules.nohunger", false));
        config.setPvp(src.getBoolean("gamerules.pvp", true));
    }

    public void setNoHunger(boolean val){
        config.setNoHunger(val);

        if(val)
            for(Player p : Bukkit.getOnlinePlayers()){
                p.setFoodLevel(20);
            }
    }

    public boolean getNoHunger(){
        return config.getNoHunger();
    }

    public void setPvp(boolean val){
        config.setPvp(val);
    }

    public boolean getPvp(){
        return config.getPvp();
    }
}
