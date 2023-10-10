package me.enderluca.verium.listener;

import me.enderluca.verium.ChallengesConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;

public class NoCraftingListener implements Listener {

    private final ChallengesConfig config;

    public NoCraftingListener(ChallengesConfig config){
        this.config = config;
    }

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent event){
        if(!config.getNoCrafting())
            return;

        if(event.getInventory().getType() == InventoryType.WORKBENCH)
            event.setCancelled(true);
    }
}
