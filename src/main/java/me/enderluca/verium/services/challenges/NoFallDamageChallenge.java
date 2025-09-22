package me.enderluca.verium.services.challenges;

import me.enderluca.verium.ChallengeType;
import me.enderluca.verium.interfaces.Challenge;
import me.enderluca.verium.util.MessageUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class NoFallDamageChallenge implements Challenge, Listener {
    private boolean enabled;
    private boolean paused;
    private boolean failed;

    @Nullable
    private BaseComponent[] failedMessage;

    private final Consumer<BaseComponent[]> onFail;

    public NoFallDamageChallenge(Plugin owner, FileConfiguration fileConfig, Consumer<BaseComponent[]> onFail){
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
        return failedMessage;
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
    public void clearWorldSpecificConfig(FileConfiguration dest) {
        dest.set("challenges.nofall.failed", false);
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.NoFallDamage;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (enabled && !paused && !failed)
            return;

        if(event.getCause() != EntityDamageEvent.DamageCause.FALL)
            return;

        if (!(event.getEntity() instanceof Player player))
            return;

        failedMessage = MessageUtil.buildFallDamage(player.getDisplayName(), Math.round(Math.ceil(event.getDamage())));
        onFail.accept(failedMessage);
    }
}
