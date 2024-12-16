package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.listener.modifiers.NoVillagerListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class NoVillagerModifier implements GameModifier {

    private boolean enabled;
    private boolean paused;

    public NoVillagerModifier(Plugin owner, FileConfiguration fileConfig){
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
        enabled = src.getBoolean("modifiers.novillager.enabled", false);
        paused = src.getBoolean("modifiers.novillager.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest){
        dest.set("modifiers.novillager.enabled", enabled);
        dest.set("modifiers.novillager.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest){ }

    @Override
    public GameModifierType getType(){
        return GameModifierType.NoVillager;
    }
}
