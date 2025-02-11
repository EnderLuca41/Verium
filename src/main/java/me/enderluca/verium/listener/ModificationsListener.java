package me.enderluca.verium.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.function.BooleanSupplier;

public class ModificationsListener implements Listener {

    private final BooleanSupplier isActive;
    public ModificationsListener(BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event){
        if(isActive.getAsBoolean()) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
            return;
        }

        if(event.getPlayer().getGameMode() == GameMode.SPECTATOR)
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntitySpawn(EntitySpawnEvent event){
        if(!isActive.getAsBoolean())
            return;

        event.setCancelled(true);
    }
}
