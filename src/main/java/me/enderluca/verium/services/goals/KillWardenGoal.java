package me.enderluca.verium.services.goals;

import me.enderluca.verium.GoalType;
import me.enderluca.verium.interfaces.Goal;

import me.enderluca.verium.listener.KillWardenListener;
import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class KillWardenGoal implements Goal {


    private boolean enabled;
    private boolean paused;
    public boolean completed;

    @Nullable
    private BaseComponent[] completeMessage;

    private final Consumer<BaseComponent[]> onGoalComplete;

    public KillWardenGoal(Plugin owner, FileConfiguration fileConfig, Consumer<BaseComponent[]> onGoalComplete){
        this.onGoalComplete = onGoalComplete;

        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new KillWardenListener(() -> enabled && !paused && !completed, this::onGoalComplete), owner);
    }

    private void onGoalComplete(BaseComponent[] message){
        completeMessage = message;
        completed = true;

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


    @Override
    public void loadConfig(FileConfiguration src){
        enabled = src.getBoolean("goals.killwarden.enabled", false);
        paused = src.getBoolean("goals.killwarden.paused", false);
        completed = src.getBoolean("goals.killwarden.completed", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest){
        dest.set("goals.killwarden.enabled", enabled);
        dest.set("goals.killwarden.paused", paused);
        dest.set("gaols.killwarden.completed", completed);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest){
        dest.set("goals.killwarden.completed", null);
    }


    @Override
    public GoalType getType(){
        return GoalType.KillWarden;
    }
}
