package me.enderluca.verium.util;

import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for dealing with potion effects
 */
public class PotionUtil {

    /**
     * Provides a standard set of identifiers since ordinal value can change
     */
    public final static Map<PotionEffectType, Integer> EFFECT_IDS;

    public final static PotionEffectType[] EFFECTS = {
            PotionEffectType.ABSORPTION,
            PotionEffectType.BAD_OMEN,
            PotionEffectType.BLINDNESS,
            PotionEffectType.CONDUIT_POWER,
            PotionEffectType.DARKNESS,
            PotionEffectType.DOLPHINS_GRACE,
            PotionEffectType.FIRE_RESISTANCE,
            PotionEffectType.GLOWING,
            PotionEffectType.HASTE,
            PotionEffectType.HEALTH_BOOST,
            PotionEffectType.HERO_OF_THE_VILLAGE,
            PotionEffectType.HUNGER,
            PotionEffectType.INFESTED,
            PotionEffectType.INSTANT_DAMAGE,
            PotionEffectType.INSTANT_HEALTH,
            PotionEffectType.INVISIBILITY,
            PotionEffectType.JUMP_BOOST,
            PotionEffectType.LEVITATION,
            PotionEffectType.LUCK,
            PotionEffectType.MINING_FATIGUE,
            PotionEffectType.NAUSEA,
            PotionEffectType.NIGHT_VISION,
            PotionEffectType.OOZING,
            PotionEffectType.POISON,
            PotionEffectType.RAID_OMEN,
            PotionEffectType.REGENERATION,
            PotionEffectType.RESISTANCE,
            PotionEffectType.SATURATION,
            PotionEffectType.SLOW_FALLING,
            PotionEffectType.SLOWNESS,
            PotionEffectType.SPEED,
            PotionEffectType.STRENGTH,
            PotionEffectType.UNLUCK,
            PotionEffectType.WATER_BREATHING,
            PotionEffectType.WEAKNESS,
            PotionEffectType.WEAVING,
            PotionEffectType.WIND_CHARGED,
            PotionEffectType.WITHER
    };

    static {
        EFFECT_IDS = new HashMap<>();
        for (int i = 0; i < EFFECTS.length; i++) {
            EFFECT_IDS.put(EFFECTS[i], i);
        }
    }


}
