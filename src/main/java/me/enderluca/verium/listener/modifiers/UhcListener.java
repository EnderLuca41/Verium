package me.enderluca.verium.listener.modifiers;

import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.function.BooleanSupplier;

public class UhcListener implements Listener {
    private final BooleanSupplier isActive;

    public UhcListener(BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldLoad(WorldLoadEvent event){
        event.getWorld().setGameRule(GameRule.NATURAL_REGENERATION, !isActive.getAsBoolean());
    }
}
