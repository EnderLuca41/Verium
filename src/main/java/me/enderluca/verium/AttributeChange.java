package me.enderluca.verium;

import me.enderluca.verium.util.AttributeUtil;

import org.bukkit.attribute.Attribute;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Represents a single change to an attribute
 */

public class AttributeChange {

    private boolean enabled = true;
    private final Attribute attribute;
    private double value;
    @Nonnull
    private final AccessList<UUID> accessList;

    @Nonnull
    private final Consumer<AttributeChange> onChange;

    public AttributeChange(Attribute attribute, @Nonnull Consumer<AttributeChange> onChange){
        this.attribute = attribute;
        this.onChange = onChange;
        value = AttributeUtil.getDefaultBaseValue(attribute);
        accessList = new AccessList<>(ListingType.Blacklist);
    }

    public AttributeChange(Attribute attribute, @Nonnull Consumer<AttributeChange> onChange, double value){
        this.attribute = attribute;
        this.onChange = onChange;
        this.value = value;
        accessList = new AccessList<>(ListingType.Blacklist);
    }

    public AttributeChange(Attribute attribute, @Nonnull Consumer<AttributeChange> onChange, double value, @Nonnull AccessList<UUID> accessList){
        this.attribute = attribute;
        this.onChange = onChange;
        this.value = value;
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
    public void addPlayer(UUID player){
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

    public Attribute getAttribute(){
        return attribute;
    }

    public double getValue(){
        return value;
    }

    public void setValue(double val){
        value = val;
        onChange.accept(this);
    }
}
