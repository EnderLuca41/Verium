package me.enderluca.verium.interfaces;

import me.enderluca.verium.GameruleType;

import org.bukkit.configuration.file.FileConfiguration;

public interface Gamerule {
    void setEnabled(boolean val);
    boolean isEnabled();

    void setPaused(boolean val);
    boolean isPaused();

    GameruleType getType();

    /**
     * Loads for the gamerule important information from {@code src}
     */
    void loadConfig(FileConfiguration src);

    /**
     * Save for the gamerule important information to {@code dest} <br>
     * This includes infos that are world-dependent
     */
    void saveConfig(FileConfiguration dest);

    /**
     * Cleans the config from world specific information
     */
    void clearWorldSpecificConfig(FileConfiguration dest);
}
