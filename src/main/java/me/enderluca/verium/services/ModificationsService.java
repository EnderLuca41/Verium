package me.enderluca.verium.services;

import me.enderluca.verium.listener.ModificationsListener;
import me.enderluca.verium.util.FireworkUtil;
import me.enderluca.verium.util.MessageUtil;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;

//TODO: Find better name for this class

/**
 * Manages challenges, modifiers, goals and attributes
 */
public class ModificationsService {

    private final TimeService time;

    private final ChallengesService challenges;
    private final GameModifierService modifiers;
    private final GoalsService goals;
    private final AttributeService attributes;

    private final TimerService timer;

    private final Plugin owner;

    private boolean allPaused;


    public ModificationsService(Plugin owner, FileConfiguration fileConfig, TimerService timer){
        this.time = new TimeService(owner, fileConfig);
        this.modifiers = new GameModifierService(owner, fileConfig);
        this.challenges = new ChallengesService(owner, fileConfig, this::onChallengeFail);
        this.goals = new GoalsService(owner, fileConfig, this::onAllGoalsComplete);
        this.attributes = new AttributeService(owner, fileConfig);
        this.timer = timer;
        this.owner = owner;

        allPaused = fileConfig.getBoolean("modifications.allpaused", false);
        time.setPaused(allPaused);

        Bukkit.getPluginManager().registerEvents(new ModificationsListener(() -> allPaused), owner);

        if(allPaused){
            challenges.setPausedAll(true);
            modifiers.setPausedAll(true);
            goals.setPausedAll(true);
            attributes.setPaused(true);
        }
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
     * Pauses the current active challenges, modifiers and goals and puts all players into spectator <br>
     * and pauses the timer
     */
    public void pause(){
        if(allPaused)
            return;

        allPaused = true;

        timer.pause();

        time.setPaused(true);
        challenges.setPausedAll(true);
        modifiers.setPausedAll(true);
        goals.setPausedAll(true);
        attributes.setPaused(true);

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setGameMode(GameMode.SPECTATOR);
            p.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }


    }

    /**
     * Resumes all challenges and modifiers, reset the failed challenges, puts all players into survival again <br>
     * and starts the timer
     */
    public void resume(){
        if(!allPaused)
            return;

        allPaused = false;

        challenges.resetFailed();

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setGameMode(GameMode.SURVIVAL);
            p.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        }

        time.setPaused(false);
        modifiers.setPausedAll(false);
        challenges.setPausedAll(false);
        goals.setPausedAll(false);
        attributes.setPaused(false);

        timer.start();
    }

    public void loadConfig(FileConfiguration src){
        allPaused = src.getBoolean("modifications.allpaused", true);

        challenges.loadConfig(src);
        modifiers.loadConfig(src);
        goals.loadConfig(src);
        attributes.loadConfig(src);
        time.loadConfig(src);
    }

    public void saveConfig(FileConfiguration dest){
        dest.set("modifications.allpaused", allPaused);

        challenges.saveConfig(dest);
        modifiers.saveConfig(dest);
        goals.saveConfig(dest);
        attributes.saveConfig(dest);
        time.saveConfig(dest);
    }

    public void clearWorldSpecificConfig(FileConfiguration dest){
        challenges.clearWorldSpecificConfig(dest);
        modifiers.clearWorldSpecificConfig(dest);
        goals.clearWorldSpecificConfig(dest);
    }

    public ChallengesService getChallengeService(){
        return challenges;
    }

    public GameModifierService getGameModifierService(){
        return modifiers;
    }

    public GoalsService getGoalsService(){
        return goals;
    }

    public AttributeService getAttributeService(){ return attributes; }

    public TimeService getTimeService(){
        return time;
    }
}
