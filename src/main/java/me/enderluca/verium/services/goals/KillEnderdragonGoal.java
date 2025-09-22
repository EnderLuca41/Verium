package me.enderluca.verium.services.goals;

import me.enderluca.verium.GoalType;
import me.enderluca.verium.interfaces.Goal;

import me.enderluca.verium.util.MessageUtil;
import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class KillEnderdragonGoal implements Goal, Listener {

    private boolean enabled;
    private boolean paused;
    private boolean completed;

    @Nullable
    private BaseComponent[] completeMessage;

    private final Consumer<BaseComponent[]> onGoalComplete;

    public KillEnderdragonGoal(Plugin owner, FileConfiguration fileConfig, Consumer<BaseComponent[]> onGoalComplete){
        completeMessage = null;
        this.onGoalComplete = onGoalComplete;

        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(this, owner);
    }

    @Override
    public void reset() {
        completed = false;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Nullable
    @Override
    public BaseComponent[] getCompleteMessage() {
        return completeMessage;
    }


    @Override
    public void setEnabled(boolean val) {
        if(enabled == val)
            return;

        reset();
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
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("goals.killenderdragon.enabled", false);
        paused = src.getBoolean("goals.killenderdragon.paused", false);
        completed = src.getBoolean("goals.killenderdragon.completed", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("goals.killenderdragon.enabled", enabled);
        dest.set("goals.killenderdragon.paused", paused);
        dest.set("goals.killenderdragon.completed", completed);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) {
        dest.set("goals.killenderdragon.completed", null);
    }

    @Override
    public GoalType getType() {
        return GoalType.KillEnderdragon;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event){
        if(enabled && !paused && !completed)
            return;

        Entity entity = event.getEntity();

        if(entity.getType() != EntityType.ENDER_DRAGON)
            return;

        EnderDragon dragon = (EnderDragon) entity;
        BaseComponent[] message = MessageUtil.buildKillEnderdragonComplete(dragon.getKiller());
        completed = true;
        completeMessage = message;
        onGoalComplete.accept(message);

    }
}
