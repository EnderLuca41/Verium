package me.enderluca.verium.listener.challenges;


import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class MlgListener implements Listener {

    @Nonnull
    private final Plugin owner;
    private final int maxHeight;
    @Nonnull
    private final BooleanSupplier isActive;
    @Nonnull
    private final Consumer<Player> onDeath;

    public MlgListener(@Nonnull Plugin owner, int maxHeight, @Nonnull BooleanSupplier isActive, @Nonnull Consumer<Player> onDeath){
        this.owner = owner;
        this.maxHeight = maxHeight;
        this.isActive = isActive;
        this.onDeath = onDeath;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event){
        if(!isActive.getAsBoolean())
            return;
        onDeath.accept(event.getEntity());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onServerLoad(ServerLoadEvent event){
        WorldCreator creator = new WorldCreator("mlg");
        creator.generateStructures(false);
        creator.type(WorldType.FLAT);
        World world = Bukkit.getServer().createWorld(creator);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setAutoSave(false);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlackPlace(BlockPlaceEvent event){
        if(!event.getPlayer().getWorld().getName().equals("mlg"))
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(owner, () -> {
            event.getBlockPlaced().setType(Material.AIR);
        }, 60);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEmptyBucket(PlayerBucketEmptyEvent event){
        if(!event.getPlayer().getWorld().getName().equals("mlg"))
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(owner, () -> {
            event.getBlock().setType(Material.AIR);
        },  60);
    }
}
