package me.enderluca.verium.services;

import me.enderluca.verium.*;
import me.enderluca.verium.interfaces.Challenge;
import me.enderluca.verium.services.challenges.NoCraftingChallenge;
import me.enderluca.verium.services.challenges.NoDeathChallenge;
import me.enderluca.verium.services.challenges.NoFallDamageChallenge;
import me.enderluca.verium.services.challenges.WolfSurviveChallenge;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChallengesService {
    private final List<Challenge> challenges;

    private final Consumer<BaseComponent[]> onFail;

    public ChallengesService(Plugin owner, FileConfiguration fileConfig, Consumer<BaseComponent[]> onChallengeFail){
        onFail = onChallengeFail;
        challenges = new ArrayList<>();

        challenges.add(new WolfSurviveChallenge(owner, fileConfig, this::onFailChallenge));
        challenges.add(new NoCraftingChallenge(owner, fileConfig));
        challenges.add(new NoFallDamageChallenge(owner, fileConfig, this::onFailChallenge));
        challenges.add(new NoDeathChallenge(owner, fileConfig, this::onFailChallenge));
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
    public void clearWorldSpecificConfig(FileConfiguration dest){
        for(Challenge ch : challenges){
            ch.clearWoldSpecificConfig(dest);
        }
    }

    private void onFailChallenge(@Nullable BaseComponent[] message){
        this.onFail.accept(message);
    }

    /**
     * Pauses/unpauses all the registered challenges
     */
    public void setPausedAll(boolean val){
        for(Challenge ch : challenges){
            ch.setPaused(val);
        }
    }

    /**
     * Resets all challenges that failed
     */
    public void resetFailed(){
        for(Challenge ch : challenges){
            if(ch.isFailed())
                ch.reset();
        }
    }

    @Nullable
    public Challenge getChallenge(ChallengeType type){
        return challenges.stream()
                .filter(ch -> ch.getType() == type)
                .findFirst()
                .orElse(null);
    }
}
