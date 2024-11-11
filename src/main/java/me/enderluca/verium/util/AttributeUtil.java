package me.enderluca.verium.util;

import org.bukkit.attribute.Attribute;

import java.util.HashMap;
import java.util.Map;

public final class AttributeUtil {
    private AttributeUtil(){}

    /**
     * Array containing all attributes that apply to a player
     */
    public static final Attribute[] PLAYER_ATTRIBUTES = {
            Attribute.ARMOR, Attribute.ARMOR_TOUGHNESS,
            Attribute.ATTACK_DAMAGE, Attribute.ATTACK_SPEED,
            Attribute.KNOCKBACK_RESISTANCE, Attribute.LUCK,
            Attribute.MAX_ABSORPTION, Attribute.MAX_HEALTH,
            Attribute.MOVEMENT_SPEED, Attribute.SCALE,
            Attribute.STEP_HEIGHT, Attribute.JUMP_STRENGTH,
            Attribute.BLOCK_INTERACTION_RANGE, Attribute.ENTITY_INTERACTION_RANGE,
            Attribute.BLOCK_BREAK_SPEED, Attribute.GRAVITY,
            Attribute.SAFE_FALL_DISTANCE, Attribute.FALL_DAMAGE_MULTIPLIER,
            Attribute.BURNING_TIME, Attribute.EXPLOSION_KNOCKBACK_RESISTANCE,
            Attribute.MINING_EFFICIENCY, Attribute.MOVEMENT_EFFICIENCY,
            Attribute.OXYGEN_BONUS, Attribute.SNEAKING_SPEED,
            Attribute.SUBMERGED_MINING_SPEED, Attribute.SWEEPING_DAMAGE_RATIO,
            Attribute.WATER_MOVEMENT_EFFICIENCY
    };

    private static final Map<Attribute, Double> defaultBaseValues = new HashMap<>();

    static {
        defaultBaseValues.put(Attribute.ARMOR, 0.0);
        defaultBaseValues.put(Attribute.ARMOR_TOUGHNESS, 0.0);
        defaultBaseValues.put(Attribute.ATTACK_DAMAGE, 1.0);
        defaultBaseValues.put(Attribute.ATTACK_SPEED, 4.0);
        defaultBaseValues.put(Attribute.KNOCKBACK_RESISTANCE, 0.0);
        defaultBaseValues.put(Attribute.LUCK, 0.0);
        defaultBaseValues.put(Attribute.MAX_HEALTH, 20.0);
        defaultBaseValues.put(Attribute.MOVEMENT_SPEED, 0.1);
    }

    /**
     * Returns the default attribute base value for a player
     */
    public static double getDefaultBaseValue(Attribute attribute){
        return defaultBaseValues.getOrDefault(attribute, 0.0);
    }

    private static final Map<Attribute, Double> minValues = new HashMap<>();

    static {
        minValues.put(Attribute.ARMOR, 0.0);
        minValues.put(Attribute.ARMOR_TOUGHNESS, 0.0);
        minValues.put(Attribute.ATTACK_DAMAGE, 0.0);
        minValues.put(Attribute.ATTACK_SPEED, 0.0);
        minValues.put(Attribute.FLYING_SPEED, 0.0);
        minValues.put(Attribute.FOLLOW_RANGE, 0.0);
        minValues.put(Attribute.KNOCKBACK_RESISTANCE, 0.0);
        minValues.put(Attribute.LUCK, -1024.0);
        minValues.put(Attribute.MAX_HEALTH, 1.0);
        minValues.put(Attribute.MOVEMENT_SPEED, 0.0);
        minValues.put(Attribute.JUMP_STRENGTH, 0.0);
        minValues.put(Attribute.SPAWN_REINFORCEMENTS, 0.0);
    }

    /**
     * Returns the minimum value of a given attribute
     */
    public static double getMinValue(Attribute attribute){
        return minValues.get(attribute);
    }

    private static final Map<Attribute, Double> maxValues = new HashMap<>();

    static {
        maxValues.put(Attribute.ARMOR, 30.0);
        maxValues.put(Attribute.ARMOR_TOUGHNESS, 20.0);
        maxValues.put(Attribute.ATTACK_DAMAGE, 2048.0);
        maxValues.put(Attribute.ATTACK_KNOCKBACK, 5.0);
        maxValues.put(Attribute.ATTACK_SPEED, 1024.0);
        maxValues.put(Attribute.FLYING_SPEED, 1024.0);
        maxValues.put(Attribute.FOLLOW_RANGE, 2048.0);
        maxValues.put(Attribute.KNOCKBACK_RESISTANCE, 1.0);
        maxValues.put(Attribute.LUCK, 1024.0);
        maxValues.put(Attribute.MAX_HEALTH, 1024.0);
        maxValues.put(Attribute.MOVEMENT_SPEED, 1024.0);
        maxValues.put(Attribute.JUMP_STRENGTH, 32.0);
        maxValues.put(Attribute.SPAWN_REINFORCEMENTS, 1.0);

    }
    /**
     * Returns the maximum value of a given attribute
     */
    public static double getMaxValue(Attribute attribute){
        return maxValues.get(attribute);
    }
}
