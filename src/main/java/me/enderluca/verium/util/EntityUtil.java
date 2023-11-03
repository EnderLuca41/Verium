package me.enderluca.verium.util;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public final class EntityUtil {
    private EntityUtil(){}

    public static Wolf createTamedWolf(String name, Location location, Player owner){
        Wolf wolf = (Wolf) owner.getWorld().spawnEntity(location, EntityType.WOLF);
        wolf.setTamed(true);
        wolf.setAdult();
        wolf.setOwner(owner);

        wolf.setCustomName(name);
        wolf.setCustomNameVisible(true);
        return wolf;
    }
}
