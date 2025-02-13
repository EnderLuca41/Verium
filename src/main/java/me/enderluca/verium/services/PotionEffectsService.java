package me.enderluca.verium.services;

import me.enderluca.verium.AccessList;
import me.enderluca.verium.ListingType;
import me.enderluca.verium.PotionEffect;
import me.enderluca.verium.listener.PotionEffectsListener;

import me.enderluca.verium.util.PotionUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;

/**
 * This service is responsible for managing permanent potion effects given to players
 */
public class PotionEffectsService {

    private boolean paused;

    /**
     * Stores the potion effects and to which player they apply
     */
    @Nonnull
    private final List<PotionEffect> potionEffects;

    public PotionEffectsService(@Nonnull Plugin owner, @Nonnull FileConfiguration fileConfig) {
        potionEffects = new ArrayList<>();

        loadConfig(fileConfig);

        PotionEffectsListener listener = new PotionEffectsListener(this::onPlayerLeave, this::applyEffects, this::getEffect);
        owner.getServer().getPluginManager().registerEvents(listener, owner);
    }

    private void onPlayerLeave(@Nonnull Player player){
        for(PotionEffect effect : potionEffects)
            if(effect.isAffected(player.getUniqueId()))
                player.removePotionEffect(effect.getEffectType());
    }

    @Nullable
    private PotionEffect getEffect(@Nonnull PotionEffectType potionEffectType) {
        for(PotionEffect effect : potionEffects)
            if(effect.getEffectType().equals(potionEffectType))
                return effect;
        return null;
    }

    /**
     * Returns an immutable list of all potion effects
     */
    @Nonnull
    public List<PotionEffect> getEffects(){
        return Collections.unmodifiableList(potionEffects);
    }

    public void addEffect(@Nonnull PotionEffectType potionEffectType, int amplifier, boolean hideParticles) {
        PotionEffect effect = new PotionEffect(potionEffectType, amplifier, hideParticles, e -> applyEffects());
        potionEffects.add(effect);
        applyEffects();
    }

    public void removeEffect(@Nonnull PotionEffect effect){
        potionEffects.remove(effect);
        applyEffects();
    }

    /**
     * Applies potion effects to all players
     */
    public void applyEffects() {
        for(Player p : Bukkit.getOnlinePlayers())
            for(org.bukkit.potion.PotionEffect effect : p.getActivePotionEffects())
                if(effect.isInfinite())
                    p.removePotionEffect(effect.getType());

        if(paused)
            return;

        for(PotionEffect effect : potionEffects)
            for(Player p : Bukkit.getOnlinePlayers())
                if(effect.isAffected(p.getUniqueId()))
                    effect.apply(p);
    }

    public void applyEffects(@Nonnull Player player) {
        for(org.bukkit.potion.PotionEffect effect : player.getActivePotionEffects())
            if(effect.isInfinite())
                player.removePotionEffect(effect.getType());

        if(paused)
            return;

        for(PotionEffect effect : potionEffects)
            if(effect.isAffected(player.getUniqueId()))
                effect.apply(player);
    }


    public void loadConfig(@Nonnull FileConfiguration src) {
        paused = src.getBoolean("potioneffects.paused", false);

        potionEffects.clear();
        if(src.getConfigurationSection("potioneffects") == null)
            return;

        for(int i = 0; src.isSet("potioneffects." + i); i++){
            int amplifier = src.getInt("potioneffects." + i + ".amplifier");
            boolean hideParticles = src.getBoolean("potioneffects." + i + ".hideparticles");
            int listingTypeOrdinal = src.getInt("potioneffects." + i + ".listingtype");
            ListingType listingType = ListingType.values()[listingTypeOrdinal];
            int effectTypeId = src.getInt("potioneffects." + i + ".effecttype");

            if(effectTypeId < 0 || effectTypeId >= PotionUtil.EFFECTS.length)
                continue;

            PotionEffectType effectType = PotionUtil.EFFECTS[effectTypeId];

            if(effectType == null)
                continue;

            AccessList<UUID> accessList = new AccessList<>(listingType);

            Pattern uuidCheck = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
            for(int j = 0; src.isSet("potioneffects." + i + ".players." + j); j++){
                String uuidString = src.getString("potioneffects." + i + ".players." + j);
                if(uuidCheck.matcher(uuidString).matches())
                    accessList.add(UUID.fromString(uuidString));
            }

            PotionEffect effect = new PotionEffect(effectType, amplifier, hideParticles, a -> applyEffects(), accessList);

            potionEffects.add(effect);
        }
    }

    public void saveConfig(@Nonnull FileConfiguration dest) {
        dest.set("potioneffects", null);

        dest.set("potioneffects.paused", paused);

        int i = 0;
        for(PotionEffect effect : potionEffects){
            dest.set("potioneffects." + i + ".effecttype", PotionUtil.EFFECT_IDS.get(effect.getEffectType()));
            dest.set("potioneffects." + i + ".amplifier", effect.getAmplifier());
            dest.set("potioneffects." + i + ".hideparticles", effect.getHideParticles());
            dest.set("potioneffects." + i + ".listingtype", effect.getListType().ordinal());

            for(int j = 0; j < effect.getPlayers().size(); j++)
                dest.set("potioneffects." + i + ".players." + j, effect.getPlayers().get(j).toString());
            i++;
        }
    }

    public void setPaused(boolean paused) {
        if(this.paused == paused)
            return;

        this.paused = paused;
        applyEffects();
    }
}
