package me.enderluca.verium.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.function.BooleanSupplier;

public class ModificationsListener implements Listener {

    private final BooleanSupplier isActive;

    public ModificationsListener(BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(!isActive.getAsBoolean())
            return;

        event.getPlayer().setGameMode(GameMode.SPECTATOR);
    }
}
