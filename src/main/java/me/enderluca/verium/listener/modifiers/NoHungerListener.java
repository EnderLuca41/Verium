package me.enderluca.verium.listener.modifiers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.function.BooleanSupplier;

public class NoHungerListener implements Listener {

    private final BooleanSupplier isActive;

    public NoHungerListener(BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        if(!isActive.getAsBoolean())
            return;

        event.setCancelled(true);
        event.getEntity().setFoodLevel(20); //Food might be not 20, because the player joined after the rule was activated
    }
}
