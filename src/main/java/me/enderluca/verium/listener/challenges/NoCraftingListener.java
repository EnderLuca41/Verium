package me.enderluca.verium.listener.challenges;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.function.BooleanSupplier;

public class NoCraftingListener implements Listener {

    private final BooleanSupplier isActive;

    public NoCraftingListener(BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true) //High priority to ensure the player cannot open the workbench but also allow other plugins to cancel the event
    public void onOpenInventory(InventoryOpenEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(event.getInventory().getType() == InventoryType.WORKBENCH)
            event.setCancelled(true);
    }
}
