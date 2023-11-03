package me.enderluca.verium.services;

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

    private final TimerService timer;

    public ModificationsService(Plugin owner, FileConfiguration fileConfig, TimerService timer){
        this.gamerules = new GamerulesService(owner, fileConfig);
        this.challenges = new ChallengesService(owner, fileConfig, this::onChallengeFail);
        this.timer = timer;
    }

    private void onChallengeFail(@Nullable BaseComponent[] message){
        pause();

        if(message != null)
            Bukkit.spigot().broadcast(message);
    }

    /**
     * Pauses the current active challenges and gamerules, puts all players into spectator <br>
     * and pauses the timer
     */
    public void pause(){
        timer.pause();

        challenges.setPausedAll(true);
        gamerules.setPausedAll(true);

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setGameMode(GameMode.SPECTATOR);
        }
    }

    /**
     * Resumes all challenges and gamerule, reset the failed challenges, puts all players into survival again <br>
     * and starts the timer
     */
    public void resume(){
        challenges.resetFailed();

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setGameMode(GameMode.SURVIVAL);
        }

        gamerules.setPausedAll(false);
        challenges.setPausedAll(false);

        timer.start();
    }

    public ChallengesService getChallengeService(){
        return challenges;
    }

    public GamerulesService getGamerulesService(){
        return gamerules;
    }
}
