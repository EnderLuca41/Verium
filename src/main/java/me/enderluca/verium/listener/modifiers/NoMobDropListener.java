package me.enderluca.verium.listener.modifiers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class NoMobDropListener implements Listener {

    @Nonnull
    private final BooleanSupplier isActive;

    public NoMobDropListener(@Nonnull BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityDeath(@Nonnull EntityDeathEvent event){
        if(!isActive.getAsBoolean())
            return;
        if(event.getEntity() instanceof Player)
            return;
        event.getDrops().clear();

        //NOTE: EntityDropItemEvent is not usable because it also called when items are dropped for example when trading with piglins
    }
}
