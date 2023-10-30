package me.enderluca.verium.services;

import me.enderluca.verium.*;
import me.enderluca.verium.interfaces.Challenge;
import me.enderluca.verium.services.challenges.NoCraftingChallenge;
import me.enderluca.verium.services.challenges.NoFallDamageChallenge;
import me.enderluca.verium.services.challenges.WolfSurviveChallenge;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ChallengesService {
    private final List<Challenge> challenges;

    private final TimerService timer;

    public ChallengesService(Plugin owner, FileConfiguration fileConfig, TimerService timer){
        this.timer = timer;

        challenges = new ArrayList<>();

        challenges.add(new WolfSurviveChallenge(owner, fileConfig, this::failChallenge));
        challenges.add(new NoCraftingChallenge(owner));
        challenges.add(new NoFallDamageChallenge(owner, fileConfig, this::failChallenge));
    }

    public void saveConfig(FileConfiguration dest){
        for(Challenge ch : challenges){
            ch.saveConfig(dest);
        }
    }

    public void loadConfig(FileConfiguration src){
        for(Challenge ch : challenges){
            ch.loadConfig(src);
        }
    }

    /**
     * Clean config from world specific data
     */
    public void cleanWorldSpecificConfig(FileConfiguration dest){
        for(Challenge ch : challenges){
            ch.cleanWoldSpecificConfig(dest);
        }
    }

    /**
     * Will pause all challenges, broadcast the {@code message}, put all players in vanish and pause the timer (if enabled)
     */
    private void failChallenge(@Nullable BaseComponent[] message){
        for(Challenge ch : challenges){
            ch.setPaused(true);
        }

        if(message != null)
            Bukkit.spigot().broadcast(message);

        for(Player p : Bukkit.getOnlinePlayers())
            p.setGameMode(GameMode.SPECTATOR);

        timer.pause();
    }


    @Nullable
    public Challenge getChallenge(ChallengeType type){
        return challenges.stream()
                .filter(ch -> ch.getType() == type)
                .findFirst()
                .orElse(null);
    }
}
