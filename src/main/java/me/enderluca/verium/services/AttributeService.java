package me.enderluca.verium.services;

import me.enderluca.verium.AttributeChange;
import me.enderluca.verium.ListingType;
import me.enderluca.verium.listener.AttributeListener;
import me.enderluca.verium.util.AttributeUtil;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.regex.Pattern;

/**
 * A service that manages attributes of all players and allows for custom attribute changes
 */
public class AttributeService {

    private boolean paused;

    /**
     * Stores the attribute changes and to which player they apply
     */
    @Nonnull
    private final List<AttributeChange> attributeChanges;

    public AttributeService(Plugin owner, FileConfiguration fileConfig){
        attributeChanges = new ArrayList<>();

        loadConfig(fileConfig);
        applyAttributes();

        Bukkit.getPluginManager().registerEvents(new AttributeListener(() -> !paused, this::applyAttributes), owner);
    }

    public void createAttributeChange(Attribute attribute, double value){
        attributeChanges.add(new AttributeChange(attribute, change -> applyAttributes(), value));
        applyAttributes();
    }


    /**
     * Applies attributes to all online players
     */
    public void applyAttributes(){
        for(Player player : Bukkit.getOnlinePlayers())
            applyAttributes(player);
    }

    /**
     * Applies attributes only to a single, online player
     */
    public void applyAttributes(Player player){
        //Reset all attributes to default
        for(Attribute attribute : AttributeUtil.PLAYER_ATTRIBUTES){
            player.getAttribute(attribute).setBaseValue(AttributeUtil.getDefaultBaseValue(attribute));
        }

        if(paused)
            return;

        for(AttributeChange change : attributeChanges){
            if(change.isAffected(player.getUniqueId()) && change.isEnabled())
                player.getAttribute(change.getAttribute()).setBaseValue(change.getValue());
        }
    }

    public void removeAttributeChange(AttributeChange change){
        attributeChanges.remove(change);
        applyAttributes();
    }

    public void setPaused(boolean val){
        if(val == paused)
            return;

        paused = val;
        applyAttributes();
    }

    @Nonnull
    public List<AttributeChange> getAttributeChanges(){
        return attributeChanges;
    }

    public void loadConfig(FileConfiguration src){
        for(int i = 0; src.isSet("attributechanges." + i); i++){
            double value = src.getDouble("attributechanges." + i + ".value");
            int attributeOrdinal = src.getInt("attributechanges." + i + ".attribute");
            int listingTypeOrdinal = src.getInt("attributechanges." + i + ".listingtype");
            createAttributeChange(Attribute.values()[attributeOrdinal], value);

            AttributeChange change = attributeChanges.get(attributeChanges.size() - 1);
            change.setListType(ListingType.values()[listingTypeOrdinal]);

            Pattern uuidCheck = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
            for(int j = 0; src.isSet("attributechanges." + i + ".players." + j); j++) {
                //src.getString cannot return null because we checked if it is set
                if(!uuidCheck.matcher(src.getString("attributechanges." + i + ".players." + j)).matches())
                    continue;

                change.addPlayer(UUID.fromString(src.getString("attributechanges." + i + ".players." + j)));
            }
        }
    }

    public void saveConfig(FileConfiguration dest){
        int i = 0;

        dest.set("attributechanges", null); //Clear the old values
        for(AttributeChange change : attributeChanges){
            dest.set("attributechanges." + i + ".value", change.getValue());
            dest.set("attributechanges." + i + ".attribute", change.getAttribute().ordinal());
            dest.set("attributechanges." + i + ".listingtype", change.getListType().ordinal());
            for(int j = 0; j < change.getPlayers().size(); j++){
                dest.set("attributechanges." + i + ".players." + j, change.getPlayers().get(j).toString());
            }
            i++;
        }
    }
}
