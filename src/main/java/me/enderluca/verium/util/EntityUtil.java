package me.enderluca.verium.util;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.meta.FireworkMeta;

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

    private static Firework createVictoryFirework(World world, Location location, Color color, FireworkEffect.Type type){
        Firework firework = world.spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.trail(true).flicker(true);
        builder.withColor(color);
        builder.with(type);

        meta.addEffect(builder.build());
        meta.setPower(1);
        firework.setFireworkMeta(meta);

        return firework;
    }

    public static Firework createVictoryFireworkStar(World world, Location location){
        return createVictoryFirework(world, location, Color.SILVER, FireworkEffect.Type.STAR);
    }

    public static Firework createVictoryFireworkBurst(World world, Location location){
        return createVictoryFirework(world, location, Color.RED, FireworkEffect.Type.BURST);
    }

    public static Firework createVictoryFireworkCreeper(World world, Location location){
        return createVictoryFirework(world, location, Color.GREEN, FireworkEffect.Type.CREEPER);
    }

    public static Firework createVictoryFireworkBall(World world, Location location){
       return createVictoryFirework(world, location, Color.BLUE, FireworkEffect.Type.BALL);
    }
}
