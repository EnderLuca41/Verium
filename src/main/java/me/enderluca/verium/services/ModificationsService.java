package me.enderluca.verium.services;

import me.enderluca.verium.listener.ModificationsListener;
import me.enderluca.verium.util.FireworkUtil;
import me.enderluca.verium.util.MessageUtil;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;

//TODO: Find better name for this class

/**
 * Manages gamerules and challenges
 */
public class ModificationsService {

    private final ChallengesService challenges;
    private final GamerulesService gamerules;
    private final GoalsService goals;

    private final TimerService timer;

    private final Plugin owner;

    private boolean allPaused;


    public ModificationsService(Plugin owner, FileConfiguration fileConfig, TimerService timer){
        this.gamerules = new GamerulesService(owner, fileConfig);
        this.challenges = new ChallengesService(owner, fileConfig, this::onChallengeFail);
        this.goals = new GoalsService(owner, fileConfig, this::onAllGoalsComplete);
        this.timer = timer;
        this.owner = owner;

        allPaused = fileConfig.getBoolean("modifications.allpaused", false);

        Bukkit.getPluginManager().registerEvents(new ModificationsListener(() -> allPaused), owner);
    }

    private void onChallengeFail(@Nullable BaseComponent[] message){
        pause();

        if(message != null)
            Bukkit.spigot().broadcast(message);
    }

    private void onAllGoalsComplete(){
        pause();

        Long timerSeconds = null;
        if(timer.isEnabled())
            timerSeconds = timer.getEplSeconds();

        Bukkit.spigot().broadcast(MessageUtil.buildAllGoalsComplete(timerSeconds));

        FireworkUtil.createWinFirework(Bukkit.getOnlinePlayers(), owner);
        FireworkUtil.createWinFireworkBurst(Bukkit.getOnlinePlayers(), owner);
    }

    /**
     * Pauses the current active challenges and gamerules, puts all players into spectator <br>
     * and pauses the timer
     */
    public void pause(){
        if(allPaused)
            return;

        allPaused = true;

        timer.pause();

        challenges.setPausedAll(true);
        gamerules.setPausedAll(true);
        goals.setPausedAll(true);

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setGameMode(GameMode.SPECTATOR);
        }
    }

    /**
     * Resumes all challenges and gamerule, reset the failed challenges, puts all players into survival again <br>
     * and starts the timer
     */
    public void resume(){
        if(!allPaused)
            return;

        allPaused = false;

        challenges.resetFailed();

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setGameMode(GameMode.SURVIVAL);
        }

        gamerules.setPausedAll(false);
        challenges.setPausedAll(false);
        goals.setPausedAll(false);

        timer.start();
    }

    public void loadConfig(FileConfiguration src){
        allPaused = src.getBoolean("modifications.allpaused", true);

        challenges.loadConfig(src);
        gamerules.loadConfig(src);
        goals.loadConfig(src);
    }

    public void saveConfig(FileConfiguration dest){
        dest.set("modifications.allpaused", allPaused);

        challenges.saveConfig(dest);
        gamerules.saveConfig(dest);
        goals.saveConfig(dest);
    }

    public void clearWorldSpecificConfig(FileConfiguration dest){
        challenges.clearWorldSpecificConfig(dest);
        gamerules.clearWorldSpecificConfig(dest);
        goals.clearWorldSpecificConfig(dest);
    }

    public ChallengesService getChallengeService(){
        return challenges;
    }

    public GamerulesService getGamerulesService(){
        return gamerules;
    }

    public GoalsService getGoalsService(){
        return goals;
    }
}
