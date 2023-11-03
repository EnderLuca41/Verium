package me.enderluca.verium.interfaces;

import me.enderluca.verium.ChallengeType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;

/**
 * Common interface for all challenge services to implement
 */
public interface Challenge {

    /**
     * Returns true if challenge condition were Failed
     * Returns false if the challenge is still running or is disabled
     */
    boolean isFailed();

    /**
     * Gets the message to be print out as a result of the challenge fail
     */
    @Nullable
    BaseComponent[] getFailedMessage();

    /**
     * Resets the challenge failed status
     */
    void reset();

    void setEnabled(boolean val);
    boolean isEnabled();

    void setPaused(boolean val);
    boolean isPaused();

    ChallengeType getType();


    /**
     * Loads for the challenge important information from {@code src}
     */
    void loadConfig(FileConfiguration src);

    /**
     * Save for the challenge important information to {@code dest}
     */
    void saveConfig(FileConfiguration dest);

    /**
     * Removes all world specific infos of the challenge from the config
     * This method will mainly be called on world reset
     */
    void cleanWoldSpecificConfig(FileConfiguration dest);
}
