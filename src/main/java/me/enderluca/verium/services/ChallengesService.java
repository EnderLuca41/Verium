package me.enderluca.verium.services;

import me.enderluca.verium.ChallengesConfig;
import me.enderluca.verium.listener.NoCraftingListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ChallengesService {
    private final ChallengesConfig config;
    public ChallengesService(Plugin owner, FileConfiguration fileConfig){
        config = new ChallengesConfig();
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new NoCraftingListener(config), owner);
    }

    public void saveConfig(FileConfiguration dest){
        dest.set("challenges.nocrafting", config.getNoCrafting());
    }

    public void loadConfig(FileConfiguration src){
        config.setNoCrafting(src.getBoolean("challenges.nocrafting", false));
    }

    public void setNoCrafting(boolean val){
        config.setNoCrafting(val);
    }

    public boolean getNoCrafting(){
        return config.getNoCrafting();
    }
}
