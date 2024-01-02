package me.enderluca.verium.listener;

import me.enderluca.verium.util.ItemUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.function.BooleanSupplier;

public class NoArmorListener implements Listener {

    private final BooleanSupplier isActive;

    public NoArmorListener(BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemPickup(EntityPickupItemEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(!(event.getEntity() instanceof Player))
            return;

        if(!ItemUtil.isArmor(event.getItem()))
            return;

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(Objects.isNull(event.getCurrentItem()))
            return;

        if(!ItemUtil.isArmor(event.getCurrentItem()))
            return;

        event.setCancelled(true);
    }


    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(Objects.isNull(event.getCursor()))
            return;

        if(!ItemUtil.isArmor(event.getCursor()))
            return;

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onManipulateArmorStand(PlayerArmorStandManipulateEvent event){
        if(!isActive.getAsBoolean())
            return;

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJon(PlayerJoinEvent event){
        if(!isActive.getAsBoolean())
            return;

        Player p = event.getPlayer();

        for(ItemStack item : p.getInventory().getContents()){
            if(Objects.isNull(item))
                continue;

            if(!ItemUtil.isArmor(item))
                continue;

            p.getInventory().remove(item);
            p.getWorld().dropItem(p.getLocation(), item);
        }

        ItemStack[] emptyArmor = {null, null, null, null};
        p.getInventory().setArmorContents(emptyArmor);
    }
}
