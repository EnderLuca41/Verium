package me.enderluca.verium.services;

import me.enderluca.verium.GameruleType;
import me.enderluca.verium.interfaces.Gamerule;
import me.enderluca.verium.services.gamerules.NoHungerGamerule;
import me.enderluca.verium.services.gamerules.PvpGamerule;
import me.enderluca.verium.services.gamerules.UhcGamerule;
import me.enderluca.verium.services.gamerules.UuhcGamerule;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GamerulesService {

    private final List<Gamerule> gamerules;

    public GamerulesService(Plugin owner, FileConfiguration fileConfig){
        gamerules = new ArrayList<>();

        gamerules.add(new NoHungerGamerule(owner, fileConfig));
        gamerules.add(new PvpGamerule(owner, fileConfig));
        gamerules.add(new UhcGamerule(owner, fileConfig));
        gamerules.add(new UuhcGamerule(owner, fileConfig));
    }

    public void loadConfig(FileConfiguration src){
        for(Gamerule gamerule : gamerules){
            gamerule.loadConfig(src);
        }
    }

    public void saveConfig(FileConfiguration dest){
        for(Gamerule gamerule : gamerules){
            gamerule.saveConfig(dest);
        }
    }
    public void cleanWorldSpecificConfig(FileConfiguration dest){
        for(Gamerule gamerule : gamerules){
            gamerule.cleanWoldSpecificConfig(dest);
        }
    }

    /**
     * Pauses or unpauses all the gamerules
     */
    public void setPaused(boolean val){
        for(Gamerule gamerule : gamerules){
            gamerule.setPaused(val);
        }
    }


    @Nullable
    public Gamerule getGamerule(GameruleType type){
        return gamerules.stream()
                .filter(gamerule -> gamerule.getType() == type)
                .findFirst()
                .orElse(null);
    }
}
