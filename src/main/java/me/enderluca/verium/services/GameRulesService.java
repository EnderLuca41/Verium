package me.enderluca.verium.services;

import me.enderluca.verium.GameRulesConfig;
import me.enderluca.verium.listener.NoHungerListener;
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
    }


    public void saveConfig(FileConfiguration dest){
        dest.set("gamerules.nohunger", config.getNoHunger());
    }
    public void loadConfig(FileConfiguration src){
        config.setNoHunger(src.getBoolean("gamerules.nohunger", false));
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
}
