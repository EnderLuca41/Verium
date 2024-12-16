package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;

import me.enderluca.verium.listener.modifiers.PvpListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class PvpModifier implements GameModifier {

    private boolean enabled;
    private boolean paused;

    public PvpModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new PvpListener(() -> enabled && !paused), owner);
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
        this.paused = val;
        updateWorlds();
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    /**
     * Updates the world with setPVP
     */
    private void updateWorlds(){
        for(World w : Bukkit.getWorlds()){
            w.setPVP(enabled && !paused);
        }
    }

    @Override
    public void loadConfig(FileConfiguration src){
        enabled = src.getBoolean("modifiers.pvp.enabled", false);
        paused = src.getBoolean("modifiers.pvp.paused", false);

        updateWorlds();
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.pvp.enabled", enabled);
        dest.set("modifiers.pvp.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { }

    @Override
    public GameModifierType getType() {
        return GameModifierType.Pvp;
    }
}
