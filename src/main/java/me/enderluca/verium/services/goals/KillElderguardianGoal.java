package me.enderluca.verium.services.goals;

import me.enderluca.verium.GoalType;
import me.enderluca.verium.interfaces.Goal;

import me.enderluca.verium.listener.goals.KillElderguardianListener;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class KillElderguardianGoal implements Goal {

    private boolean enabled;
    private boolean paused;
    private boolean completed;

    @Nullable
    private BaseComponent[] completeMessage;

    private final Consumer<BaseComponent[]> onGoalComplete;

    public KillElderguardianGoal(Plugin owner, FileConfiguration fileConfig, Consumer<BaseComponent[]> onGoalComplete){
        this.onGoalComplete = onGoalComplete;

        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new KillElderguardianListener(() -> enabled && !paused && !completed, this::onGoalComplete), owner);
    }

    public void onGoalComplete(BaseComponent[] message){
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
        enabled = src.getBoolean("goals.killelderguardian.enabled", false);
        paused = src.getBoolean("goals.killelderguardian.paused", false);
        completed = src.getBoolean("goals.killelderguardian.completed", false);
    }

    public void saveConfig(FileConfiguration dest){
        dest.set("goals.killelderguardian.enabled", enabled);
        dest.set("goals.killelderguardian.paused", paused);
        dest.set("goals.killelderguardian.completed", completed);
    }

    public void clearWorldSpecificConfig(FileConfiguration dest){
        dest.set("goals.killelderguardian.completed", null);
    }


    public GoalType getType(){
        return GoalType.KillElderguardian;
    }
}
