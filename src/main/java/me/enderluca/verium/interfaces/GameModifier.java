package me.enderluca.verium.interfaces;

import me.enderluca.verium.GameModifierType;

import org.bukkit.configuration.file.FileConfiguration;

public interface GameModifier {
    void setEnabled(boolean val);
    boolean isEnabled();

    void setPaused(boolean val);
    boolean isPaused();

    GameModifierType getType();

    /**
     * Loads for the game modifier important information from {@code src}
     */
    void loadConfig(FileConfiguration src);

    /**
     * Save for the game modifier important information to {@code dest} <br>
     * This includes infos that are world-dependent
     */
    void saveConfig(FileConfiguration dest);

    /**
     * Cleans the config from world specific information
     */
    void clearWorldSpecificConfig(FileConfiguration dest);
}
