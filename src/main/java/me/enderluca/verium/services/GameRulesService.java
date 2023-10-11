package me.enderluca.verium.services;

import me.enderluca.verium.util.GameRulesConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class GameRulesService {
    private final GameRulesConfig config;
    public GameRulesService(Plugin owner, FileConfiguration fileConfig){
        config = new GameRulesConfig();

        loadConfig(fileConfig);
    }


    public void loadConfig(FileConfiguration fileConfig){

    }

    public void saveConfig(FileConfiguration fileConfig){

    }
}
