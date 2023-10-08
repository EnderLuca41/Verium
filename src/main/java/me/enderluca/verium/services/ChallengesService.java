package me.enderluca.verium.services;

import me.enderluca.verium.ChallengesConfig;
import org.bukkit.plugin.Plugin;

public class ChallengesService {

    private final Plugin owner;
    private ChallengesConfig config;
    public ChallengesService(Plugin owner, ChallengesConfig config){
        this.owner = owner;
    }
}
