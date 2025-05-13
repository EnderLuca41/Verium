package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.runnable.AnvilRainRunnable;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Creates a periodic rain of anvils around each player. <br> <br>
 * NOTE: This modification may be converted to a challenge at a layer, as it is not yet clear if this modification is "failable".
 */
public class AnvilRainModifier implements GameModifier {

    private final int AMOUNT = 8; //Amount of anvils per interval
    private final int COOLDOWN = 1; //Cooldown between anvils in seconds
    private final int RADIUS = 24; //Radius of the anvil rain around the players
    private final int HEIGHT = 20; //Height where the anvil will spawn relative to the player
    private final int HEIGHT_VARIATION = 2; //Variation of the height where the anvil will spawn relative to the player

    private boolean enabled;
    private boolean paused;

    public AnvilRainModifier(Plugin owner, FileConfiguration fileConfig) {
        loadConfig(fileConfig);
        registerTask(owner);
    }

    public void registerTask(Plugin owner){
        AnvilRainRunnable runnable = new AnvilRainRunnable(AMOUNT, RADIUS, HEIGHT, HEIGHT_VARIATION, () -> enabled && !paused);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(owner, runnable, 0, COOLDOWN*20);

    }

    @Override
    public void setEnabled(boolean val) {
        enabled = val;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setPaused(boolean val) {
        paused = val;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("modifiers.anvilrain.enabled", false);
        paused = src.getBoolean("modifiers.anvilrain.paused", false);
    }

    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.anvilrain.enabled", enabled);
        dest.set("modifiers.anvilrain.paused", paused);
    }

    public void clearWorldSpecificConfig(FileConfiguration dest) {} // No world-specific config for this modifier

    @Override
    public GameModifierType getType() {
        return GameModifierType.AnvilRain;
    }
}
