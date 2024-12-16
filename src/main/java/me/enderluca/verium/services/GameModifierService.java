package me.enderluca.verium.services;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.services.modifiers.*;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GameModifierService {

    private final List<GameModifier> gameModifiers;

    public GameModifierService(Plugin owner, FileConfiguration fileConfig){
        gameModifiers = new ArrayList<>();

        gameModifiers.add(new NoCraftingModifier(owner, fileConfig));
        gameModifiers.add(new NoHungerModifier(owner, fileConfig));
        gameModifiers.add(new PvpModifier(owner, fileConfig));
        gameModifiers.add(new UhcModifier(owner, fileConfig));
        gameModifiers.add(new UuhcModifier(owner, fileConfig));
        gameModifiers.add(new NoVillagerModifier(owner, fileConfig));
        gameModifiers.add(new NoArmorModifier(owner, fileConfig));
    }

    public void loadConfig(FileConfiguration src){
        for(GameModifier modifier : gameModifiers){
            modifier.loadConfig(src);
        }
    }

    public void saveConfig(FileConfiguration dest){
        for(GameModifier modifier : gameModifiers){
            modifier.saveConfig(dest);
        }
    }
    public void clearWorldSpecificConfig(FileConfiguration dest){
        for(GameModifier modifier : gameModifiers){
            modifier.clearWorldSpecificConfig(dest);
        }
    }

    /**
     * Pauses or unpauses all the modifiers
     */
    public void setPausedAll(boolean val){
        for(GameModifier modifier : gameModifiers){
            modifier.setPaused(val);
        }
    }


    @Nullable
    public GameModifier getModifier(GameModifierType type){
        return gameModifiers.stream()
                .filter(modifier -> modifier.getType() == type)
                .findFirst()
                .orElse(null);
    }
}
