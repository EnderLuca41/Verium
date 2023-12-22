package me.enderluca.verium.services.gamerules;

import me.enderluca.verium.GameruleType;
import me.enderluca.verium.interfaces.Gamerule;
import me.enderluca.verium.listener.UhcListener;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class UhcGamerule implements Gamerule {

    private boolean enabled;
    private boolean paused;

    public UhcGamerule(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new UhcListener(() -> enabled && !paused), owner);
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
        paused = val;
        updateWorlds();
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    /**
     * Updates the gamerules of the worlds based on the current state of {@code enabled} and {@code paused}
     */
    private void updateWorlds(){
        for(World world : Bukkit.getWorlds()){
            world.setGameRule(GameRule.NATURAL_REGENERATION, !(enabled && !paused));
        }
    }

    @Override
    public void loadConfig(FileConfiguration src){
        enabled = src.getBoolean("gamerules.uhc.enabled", false);
        paused = src.getBoolean("gamerules.uhc.paused", false);

        updateWorlds();
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("gamerules.uhc.enabled", enabled);
        dest.set("gamerules.uhc.paused", paused);
    }

    @Override
    public void clearWoldSpecificConfig(FileConfiguration dest) {
        dest.set("gamerules.uhc.paused", null);
    }

    @Override
    public GameruleType getType() {
        return GameruleType.Uhc;
    }
}
