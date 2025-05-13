package me.enderluca.verium.listener.modifiers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;

public class SoupListener implements Listener {

    private final BooleanSupplier isActive;
    private final BiConsumer<Player, Integer> onSoupConsumed;

    public SoupListener(BooleanSupplier isActive, BiConsumer<Player, Integer> onSoupConsumed) {
        this.isActive = isActive;
        this.onSoupConsumed = onSoupConsumed;
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
            return;

        if(event.getPlayer().getInventory().getItemInMainHand().getType() != Material.MUSHROOM_STEW)
            return;

        event.setCancelled(true);
        onSoupConsumed.accept(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot());
    }
}
