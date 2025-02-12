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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//TODO: Find better name for this class

/**
 * Manages challenges, modifiers, goals, attributes, time and potion effects
 */
public class ModificationsService {

    @Nonnull
    private final TimeService time;
    @Nonnull
    private final ChallengesService challenges;
    @Nonnull
    private final GameModifierService modifiers;
    @Nonnull
    private final GoalsService goals;
    @Nonnull
    private final AttributeService attributes;
    @Nonnull
    private final PotionEffectsService potionEffects;

    @Nonnull
    private final TimerService timer;

    @Nonnull
    private final Plugin owner;

    private boolean allPaused;


    public ModificationsService(@Nonnull Plugin owner, @Nonnull FileConfiguration fileConfig, @Nonnull TimerService timer){
        this.time = new TimeService(owner, fileConfig);
        this.modifiers = new GameModifierService(owner, fileConfig);
        this.challenges = new ChallengesService(owner, fileConfig, this::onChallengeFail);
        this.goals = new GoalsService(owner, fileConfig, this::onAllGoalsComplete);
        this.attributes = new AttributeService(owner, fileConfig);
        this.potionEffects = new PotionEffectsService(owner, fileConfig);
        this.timer = timer;
        this.owner = owner;

        allPaused = fileConfig.getBoolean("modifications.allpaused", false);
        time.setPaused(allPaused);

        Bukkit.getPluginManager().registerEvents(new ModificationsListener(() -> allPaused), owner);

        //Ensure all services are actually paused
        if(allPaused){
            challenges.setPausedAll(true);
            modifiers.setPausedAll(true);
            goals.setPausedAll(true);
            attributes.setPaused(true);
            potionEffects.setPaused(true);
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
     * Pauses all services and puts all players into spectator and pauses the timer
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
        potionEffects.setPaused(true);

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setGameMode(GameMode.SPECTATOR);
            p.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }


    }

    /**
     * Resumes all services, puts all players into survival again and starts the timer
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
        potionEffects.setPaused(false);

        timer.start();
    }

    public void loadConfig(@Nonnull FileConfiguration src){
        allPaused = src.getBoolean("modifications.allpaused", true);

        challenges.loadConfig(src);
        modifiers.loadConfig(src);
        goals.loadConfig(src);
        attributes.loadConfig(src);
        time.loadConfig(src);
        potionEffects.loadConfig(src);
    }

    public void saveConfig(@Nonnull FileConfiguration dest){
        dest.set("modifications.allpaused", allPaused);

        challenges.saveConfig(dest);
        modifiers.saveConfig(dest);
        goals.saveConfig(dest);
        attributes.saveConfig(dest);
        time.saveConfig(dest);
        potionEffects.saveConfig(dest);
    }

    /**
     * Clears all entries of the config that is linked to the current world, <br>
     * mainly used when the current world is deleted
     */
    public void clearWorldSpecificConfig(@Nonnull FileConfiguration dest){
        challenges.clearWorldSpecificConfig(dest);
        modifiers.clearWorldSpecificConfig(dest);
        goals.clearWorldSpecificConfig(dest);
    }

    @Nonnull
    public ChallengesService getChallengeService(){ return challenges; }

    @Nonnull
    public GameModifierService getGameModifierService(){ return modifiers; }

    @Nonnull
    public GoalsService getGoalsService(){ return goals; }

    @Nonnull
    public AttributeService getAttributeService(){ return attributes; }

    @Nonnull
    public TimeService getTimeService(){ return time; }

    @Nonnull
    public PotionEffectsService getPotionEffectsService(){ return potionEffects; }
}
