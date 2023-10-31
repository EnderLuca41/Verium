package me.enderluca.verium.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.function.BooleanSupplier;

public class PvpListener implements Listener {

    private final BooleanSupplier isActive;

    public PvpListener(BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event){
        event.getWorld().setPVP(isActive.getAsBoolean());
    }
}
