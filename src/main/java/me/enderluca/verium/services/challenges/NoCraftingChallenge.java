package me.enderluca.verium.services.challenges;

import me.enderluca.verium.interfaces.Challenge;
import me.enderluca.verium.ChallengeType;
import me.enderluca.verium.listener.NoCraftingListener;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;

public class NoCraftingChallenge implements Challenge {

    private boolean enabled;
    private boolean paused;

    public NoCraftingChallenge(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new NoCraftingListener(() -> enabled && !paused), owner);
    }

    @Override
    public boolean isFailed() {
        return false; //Challenge cannot fail
    }

    @Nullable
    @Override
    public BaseComponent[] getFailedMessage() {
        return null; //Challenge cannot fail
    }

    @Override
    public void reset() { } //Nothing to reset

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

    @Override
    public ChallengeType getType() {
        return ChallengeType.NoCrafting;
    }

    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("challenges.nocrafting.enabled", false);
        paused = src.getBoolean("challenges.nocrafting.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("challenges.nocrafting.enabled", enabled);
        dest.set("challenges.nocrafting.paused", paused);
    }

    @Override
    public void cleanWoldSpecificConfig(FileConfiguration dest) { }
}
