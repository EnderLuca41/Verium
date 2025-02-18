package me.enderluca.verium.listener.modifiers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.function.BooleanSupplier;

public class UuhcListener implements Listener {

    private final BooleanSupplier isActive;

    public UuhcListener(BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityHeal(EntityRegainHealthEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(!(event.getEntity() instanceof Player))
            return;

        event.setCancelled(true);
    }
}
