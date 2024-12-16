package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.listener.modifiers.UhcListener;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class UhcModifier implements GameModifier {

    private boolean enabled;
    private boolean paused;

    public UhcModifier(Plugin owner, FileConfiguration fileConfig){
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
        enabled = src.getBoolean("modifiers.uhc.enabled", false);
        paused = src.getBoolean("modifiers.uhc.paused", false);

        updateWorlds();
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.uhc.enabled", enabled);
        dest.set("modifiers.uhc.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { }

    @Override
    public GameModifierType getType() {
        return GameModifierType.Uhc;
    }
}
