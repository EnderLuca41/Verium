package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.listener.modifiers.SharedDamageListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class SharedDamageModifier implements GameModifier {

    private boolean enabled;
    private boolean paused;

    public SharedDamageModifier(Plugin owner, FileConfiguration config){
        loadConfig(config);

        owner.getServer().getPluginManager().registerEvents(new SharedDamageListener(() -> enabled && !paused), owner);
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
        enabled = src.getBoolean("modifiers.shareddamage.enabled", false);
        paused = src.getBoolean("modifiers.shareddamage.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.shareddamage.enabled", enabled);
        dest.set("modifiers.shareddamage.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { } //No world specific data

    @Override
    public GameModifierType getType() {
        return GameModifierType.SharedDamage;
    }
}
