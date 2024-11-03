package me.enderluca.verium.util;

import org.bukkit.attribute.Attribute;

public final class AttributeUtil {
    private AttributeUtil(){}

    /**
     * Array containing all attributes that apply to a player
     */
    public static final Attribute[] PLAYER_ATTRIBUTES = {
            Attribute.GENERIC_ARMOR, Attribute.GENERIC_ARMOR_TOUGHNESS,
            Attribute.GENERIC_ATTACK_DAMAGE, Attribute.GENERIC_ATTACK_SPEED,
            Attribute.GENERIC_KNOCKBACK_RESISTANCE, Attribute.GENERIC_LUCK,
            Attribute.GENERIC_MAX_HEALTH, Attribute.GENERIC_MOVEMENT_SPEED
    };

    /**
     * Returns the default attribute base value for a player
     */
    public static double getDefaultBaseValue(Attribute attribute){
        return switch (attribute){
            case GENERIC_ARMOR -> 0.0;
            case GENERIC_ARMOR_TOUGHNESS -> 0.0;
            case GENERIC_ATTACK_DAMAGE -> 1.0;
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

    /**
     * Returns the minimum value of a given attribute
     */
    public static double getMinValue(Attribute attribute){
        return switch(attribute){
            case GENERIC_ARMOR -> 0.0;
            case GENERIC_ARMOR_TOUGHNESS -> 0.0;
            case GENERIC_ATTACK_DAMAGE -> 0.0;
            case GENERIC_ATTACK_KNOCKBACK -> 0.0;
            case GENERIC_ATTACK_SPEED -> 0.0;
            case GENERIC_FLYING_SPEED -> 0.0;
            case GENERIC_FOLLOW_RANGE -> 0.0;
            case GENERIC_KNOCKBACK_RESISTANCE -> 0.0;
            case GENERIC_LUCK -> -1024.0;
            case GENERIC_MAX_HEALTH -> 1.0;
            case GENERIC_MOVEMENT_SPEED -> 0.0;
            case HORSE_JUMP_STRENGTH -> 0.0;
            case ZOMBIE_SPAWN_REINFORCEMENTS -> 0.0;
        };
    }

    /**
     * Returns the maximum value of a given attribute
     */
    public static double getMaxValue(Attribute attribute){
        return switch(attribute){
            case GENERIC_ARMOR -> 30.0;
            case GENERIC_ARMOR_TOUGHNESS -> 20.0;
            case GENERIC_ATTACK_DAMAGE -> 2048.0;
            case GENERIC_ATTACK_KNOCKBACK -> 5.0;
            case GENERIC_ATTACK_SPEED -> 1024.0;
            case GENERIC_FLYING_SPEED -> 1024.0;
            case GENERIC_FOLLOW_RANGE -> 2048.0;
            case GENERIC_KNOCKBACK_RESISTANCE -> 1.0;
            case GENERIC_LUCK -> 1024.0;
            case GENERIC_MAX_HEALTH -> 1024.0;
            case GENERIC_MOVEMENT_SPEED -> 1024.0;
            case HORSE_JUMP_STRENGTH -> 32.0;
            case ZOMBIE_SPAWN_REINFORCEMENTS -> 1.0;
        };
    }
}
