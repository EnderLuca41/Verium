package me.enderluca.verium.util;

import org.bukkit.attribute.Attribute;

public final class AttributeUtil {
    private AttributeUtil(){}

    /**
     * Returns the default attribute base value for a player
     */
    public double getDefaultBaseValue(Attribute attribute){
        return switch (attribute){
            case GENERIC_ARMOR -> 0.0;
            case GENERIC_ARMOR_TOUGHNESS -> 0.0;
            case GENERIC_ATTACK_DAMAGE -> 2.0;
            case GENERIC_ATTACK_SPEED -> 4.0;
            case GENERIC_KNOCKBACK_RESISTANCE -> 0.0;
            case GENERIC_LUCK -> 0.0;
            case GENERIC_MAX_HEALTH -> 20.0;
            case GENERIC_MOVEMENT_SPEED -> 0.1;
            default -> 0.0; //If attribute is not applicable to player, return 0.0
            //NOTE: attribute max absorption was added in 1.20.2
            //NOTE 2: 1.20.5 Also adds 4 new attributes
        };
    }
}