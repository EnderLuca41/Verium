package me.enderluca.verium.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.function.BooleanSupplier;

public class NoVillagerListener implements Listener {

    private final BooleanSupplier isActive;

    public NoVillagerListener(BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(!(event.getInventory().getType() == InventoryType.MERCHANT))
            return;

        event.setCancelled(true);
    }
}
