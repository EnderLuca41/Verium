package me.enderluca.verium.services.challenges;

import me.enderluca.verium.ChallengeType;
import me.enderluca.verium.interfaces.Challenge;
import me.enderluca.verium.listener.NoFallDamageListener;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class NoFallDamageChallenge implements Challenge {
    private boolean enabled;
    private boolean paused;
    private boolean failed;

    private final Consumer<BaseComponent[]> onFail;

    public NoFallDamageChallenge(Plugin owner, FileConfiguration fileConfig, Consumer<BaseComponent[]> onFail){
        this.onFail = onFail;

        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new NoFallDamageListener(() -> enabled && !paused && !failed, this::onFail), owner);
    }

    private void onFail(BaseComponent[] message){
        failed = true;
        onFail.accept(message);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean val) {
        this.enabled = val;
    }

    @Override
    public boolean isPaused(){
        return paused;
    }

    @Override
    public void setPaused(boolean val){
        paused = val;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Nullable
    @Override
    public BaseComponent[] getFailedMessage() {
        return new BaseComponent[0];
    }

    @Override
    public void reset() {
        failed = false;
    }



    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("challenges.nofall.enabled", false);
        paused = src.getBoolean("challenges.nofall.paused", false);
        failed = src.getBoolean("challenges.nofall.failed", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("challenges.nofall.enabled", enabled);
        dest.set("challenges.nofall.paused", paused);
        dest.set("challenges.nofall.failed", failed);
    }

    @Override
    public void clearWoldSpecificConfig(FileConfiguration dest) {
        dest.set("challenges.nofall.failed", false);
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.NoFallDamage;
    }
}
