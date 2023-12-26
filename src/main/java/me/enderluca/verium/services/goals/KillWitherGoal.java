package me.enderluca.verium.services.goals;

import me.enderluca.verium.GoalType;
import me.enderluca.verium.interfaces.Goal;

import me.enderluca.verium.listener.KillWitherListener;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;


public class KillWitherGoal implements Goal {

    private boolean enabled;
    private boolean paused;
    private boolean completed;

    @Nullable
    private BaseComponent[] completeMessage;

    private final Consumer<BaseComponent[]> onGoalComplete;

    public KillWitherGoal(Plugin owner, FileConfiguration fileConfig, Consumer<BaseComponent[]> onGoalComplete){
        this.onGoalComplete = onGoalComplete;

        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new KillWitherListener(() -> enabled && !paused && !completed, this::onGoalComplete), owner);
    }

    public void onGoalComplete(@Nullable BaseComponent[] message){
        completed = true;
        completeMessage = message;

        onGoalComplete.accept(message);
    }

    @Override
    public void reset(){
        completed = false;
    }

    @Override
    public boolean isCompleted(){
        return completed;
    }

    @Nullable
    @Override
    public BaseComponent[] getCompleteMessage(){
        return completeMessage;
    }


    @Override
    public void setEnabled(boolean val){
        if(val == enabled)
            return;

        reset();
        enabled = val;
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }


    @Override
    public void setPaused(boolean val){
        paused = val;
    }

    @Override
    public boolean isPaused(){
        return paused;
    }

    public void loadConfig(FileConfiguration src){
        enabled = src.getBoolean("goals.killwither.enabled", false);
        paused = src.getBoolean("goals.killwither.paused", false);
        completed = src.getBoolean("goals.killwither.completed", false);
    }

    public void saveConfig(FileConfiguration dest){
        dest.set("goals.killwither.enabled", enabled);
        dest.set("goals.killwither.paused", paused);
        dest.set("goals.killwither.completed", completed);
    }

    public void clearWorldSpecificConfig(FileConfiguration dest){
        dest.set("goals.killwither.completed", null);
    }

    public GoalType getType(){
        return GoalType.KillWither;
    }
}
