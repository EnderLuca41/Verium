package me.enderluca.verium.services;

import me.enderluca.verium.util.EntityUtil;
import me.enderluca.verium.util.MessageUtil;
import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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

    public ModificationsService(Plugin owner, FileConfiguration fileConfig, TimerService timer){
        this.gamerules = new GamerulesService(owner, fileConfig);
        this.challenges = new ChallengesService(owner, fileConfig, this::onChallengeFail);
        this.goals = new GoalsService(owner, fileConfig, this::onAllGoalsComplete);
        this.timer = timer;
        this.owner = owner;
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

        //Relative positions from the player where fireworks should appear
        Vector pos1 = new Vector(0, 0, 0);
        Vector pos2 = new Vector(1, 0, 0);
        Vector pos3 = new Vector(0, 0 , 1);
        Vector pos4 = new Vector(1, 0, 1);

        List<Firework> fireworks = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            fireworks.add(EntityUtil.createVictoryFireworkStar(p.getWorld(), p.getLocation().add(pos1)));
            fireworks.add(EntityUtil.createVictoryFireworkBurst(p.getWorld(), p.getLocation().add(pos2)));
            fireworks.add(EntityUtil.createVictoryFireworkCreeper(p.getWorld(), p.getLocation().add(pos3)));
            fireworks.add(EntityUtil.createVictoryFireworkBall(p.getWorld(), p.getLocation().add(pos4)));
        }

        BukkitRunnable detonateFireworks = new BukkitRunnable() {
            @Override
            public void run() {
                fireworks.forEach(Firework::detonate);
            }
        };

        detonateFireworks.runTaskLater(owner, 40);
    }

    /**
     * Pauses the current active challenges and gamerules, puts all players into spectator <br>
     * and pauses the timer
     */
    public void pause(){
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
        challenges.resetFailed();

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setGameMode(GameMode.SURVIVAL);
        }

        gamerules.setPausedAll(false);
        challenges.setPausedAll(false);
        goals.setPausedAll(false);

        timer.start();
    }

    public ChallengesService getChallengeService(){
        return challenges;
    }

    public GamerulesService getGamerulesService(){
        return gamerules;
    }
}
