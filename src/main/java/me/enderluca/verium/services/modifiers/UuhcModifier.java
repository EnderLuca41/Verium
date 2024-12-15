package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.listener.modifiers.UuhcListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class UuhcModifier implements GameModifier {

    private boolean enabled;
    private boolean paused;

    public UuhcModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new UuhcListener(() -> enabled && !paused), owner);
    }


    @Override
    public void setEnabled(boolean val) {
        this.enabled = val;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    @Override
    public void setPaused(boolean val) {
        this.paused = val;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("modifiers.uuhc.enabled", false);
        paused = src.getBoolean("modifiers.uuhc.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.uuhc.enabled", enabled);
        dest.set("modifiers.uuhc.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { }


    @Override
    public GameModifierType getType(){
        return GameModifierType.Uuhc;
    }
}
