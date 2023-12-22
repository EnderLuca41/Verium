package me.enderluca.verium.interfaces;

import me.enderluca.verium.GoalType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;

/**
 * Common interface for all Goal services to implement
 */
public interface Goal {

    boolean isCompleted();

    /**
     * Resets the goal completed status
     */
    void reset();

    /**
     * Returns the message to be broadcast to all players when the goal is complete
     */
    @Nullable
    BaseComponent[] getCompleteMessage();


    void setEnabled(boolean val);

    boolean isEnabled();


    void setPaused(boolean val);

    boolean isPaused();

    /**
     * Loads all for the goal necessary information from {@code src}
     */
    void loadConfig(FileConfiguration src);

    /**
     * Saves all for the goal necessary information to  {@code dest} <br>
     * This includes world dependent infos
     */
    void saveConfig(FileConfiguration dest);

    /**
     * Cleans the config from world specific information
     */
    void clearWorldSpecificConfig(FileConfiguration dest);


    GoalType getType();
}
