package me.enderluca.verium;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class PotionEffect {
    private boolean enabled = true;
    private final PotionEffectType potionEffectType;
    private int amplifier;
    private boolean hideParticles = false;

    @Nonnull
    private final AccessList<UUID> accessList;

    @Nonnull
    private final Consumer<PotionEffect> onChange;

    public PotionEffect(PotionEffectType potionEffectType, int amplifier, @Nonnull Consumer<PotionEffect> onChange){
        this.potionEffectType = potionEffectType;
        this.amplifier = amplifier;
        this.accessList = new AccessList<>(ListingType.Blacklist);
        this.onChange = onChange;
    }

    public PotionEffect(PotionEffectType potionEffectType, int amplifier, boolean hideParticles, @Nonnull Consumer<PotionEffect> onChange){
        this.potionEffectType = potionEffectType;
        this.amplifier = amplifier;
        this.hideParticles = hideParticles;
        this.onChange = onChange;
        this.accessList = new AccessList<>(ListingType.Blacklist);
    }

    public PotionEffect(PotionEffectType potionEffectType, int amplifier, boolean hideParticles, @Nonnull Consumer<PotionEffect> onChange, @Nonnull AccessList<UUID> accessList){
        this.potionEffectType = potionEffectType;
        this.amplifier = amplifier;
        this.hideParticles = hideParticles;
        this.onChange = onChange;
        this.accessList = accessList;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean val){
        enabled = val;
        onChange.accept(this);
    }

    /**
     * Add a player to the access list
     */
    public void addPlayer(UUID player) {
        accessList.add(player);
        onChange.accept(this);
    }

    /**
     * Remove a player from the access list
     */
    public void removePlayer(UUID player){
        accessList.remove(player);
        onChange.accept(this);
    }

    /**
     * Returns an unmodifiable list of all players in the access list whether it is a whitelist or blacklist
     */
    public List<UUID> getPlayers(){
        return Collections.unmodifiableList(accessList.getList());
    }

    /**
     * Returns true if the player is in the access list
     */
    public boolean containsPlayer(UUID player){
        return accessList.getList().contains(player);
    }

    /**
     * Returns true if the player is affected by this attribute change
     */
    public boolean isAffected(UUID player){
        return accessList.hasAccess(player);
    }

    public void setListType(ListingType type){
        accessList.setType(type);
        onChange.accept(this);
    }

    public ListingType getListType(){
        return accessList.getType();
    }

    public PotionEffectType getEffectType() {
        return potionEffectType;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
        onChange.accept(this);
    }

    public boolean getHideParticles() {
        return hideParticles;
    }

    public void setHideParticles(boolean hideParticles) {
        this.hideParticles = hideParticles;
        onChange.accept(this);
    }

    public void apply(Player player) {
        if(!enabled)
            return;
        player.addPotionEffect(new org.bukkit.potion.PotionEffect(potionEffectType, org.bukkit.potion.PotionEffect.INFINITE_DURATION, amplifier, hideParticles));
    }
}
