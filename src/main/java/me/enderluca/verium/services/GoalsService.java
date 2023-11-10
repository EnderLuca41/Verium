package me.enderluca.verium.services;

import me.enderluca.verium.GoalType;
import me.enderluca.verium.interfaces.Goal;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GoalsService {

    private final List<Goal> goals;

    private final Runnable onAllGoalsComplete;

    public GoalsService(Plugin owner, FileConfiguration fileConfig, Runnable onAllGoalsComplete){
        this.goals = new ArrayList<>();
        this.onAllGoalsComplete = onAllGoalsComplete;
    }

    private void onGoalComplete(@Nullable BaseComponent[] message){
        if(message != null)
            Bukkit.spigot().broadcast(message);

        for(Goal goal : goals){
            if(goal.isEnabled() && !goal.isCompleted())
                return;
        }

        //Every enabled goal is completed
        onAllGoalsComplete.run();
    }


    public void loadConfig(FileConfiguration src){
        for(Goal goal : goals)
            goal.loadConfig(src);
    }

    public void saveConfig(FileConfiguration dest){
        for(Goal goal : goals)
            goal.loadConfig(dest);
    }

    public void clearWorldSpecificConfig(FileConfiguration dest){
        for(Goal goal : goals)
            goal.loadConfig(dest);
    }



    public void setPausedAll(boolean val){
        goals.forEach(g -> g.setPaused(val));
    }

    @Nullable
    public Goal getGoal(GoalType type){
        return goals.stream()
                .filter(g -> g.getType() == type)
                .findFirst()
                .orElse(null);
    }
}
