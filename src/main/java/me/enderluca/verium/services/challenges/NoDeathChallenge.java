package me.enderluca.verium.services.challenges;

import me.enderluca.verium.ChallengeType;
import me.enderluca.verium.interfaces.Challenge;
import me.enderluca.verium.util.MessageUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class NoDeathChallenge implements Challenge, Listener {

    private boolean enabled;
    private boolean paused;
    private boolean failed;

    @Nullable
    private BaseComponent[] failedMessage;

    Consumer<BaseComponent[]> onFail;

    public NoDeathChallenge(Plugin owner, FileConfiguration fileConfig, Consumer<BaseComponent[]> onFail){
        this.onFail = onFail;

        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(this, owner);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean val) {
        enabled = val;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void setPaused(boolean val) {
        paused = val;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Nullable
    @Override
    public BaseComponent[] getFailedMessage() {
        return failedMessage;
    }

    @Override
    public void reset() {
        failed = false;
    }

    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("challenges.nodeath.enabled", false);
        paused = src.getBoolean("challenges.nodeath.paused", false);
        failed = src.getBoolean("challenges.nodeath.failed", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("challenges.nodeath.enabled", enabled);
        dest.set("challenges.nodeath.paused", paused);
        dest.set("challenges.nodeath.failed", failed);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) {
        dest.set("challenges.nodeath.failed", false);
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.NoDeath;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event){
        if(enabled && !paused && !failed)
            return;

        BaseComponent[] deathMessage = MessageUtil.buildDeathMessage(event.getEntity().getDisplayName(), event.getDeathMessage());
        event.setDeathMessage(TextComponent.toLegacyText(deathMessage));
        this.failedMessage = deathMessage;
        onFail.accept(null); //Since we already replaced the death message, we don't need to pass the message
    }
}
