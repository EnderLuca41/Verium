package me.enderluca.verium.listener;

import me.enderluca.verium.services.ChallengesService;
import me.enderluca.verium.util.GuiUtil;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Listens for event for the challenge gui
 */
public class ChallengesInventoryListener implements Listener {
    private final ChallengesService challengesService;

    public ChallengesInventoryListener(ChallengesService challengesService){
        this.challengesService = challengesService;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equals(GuiUtil.CHALLENGE_GUI_NAME))
            return;

        event.setCancelled(true);

        if(!event.isLeftClick() || event.isShiftClick())
            return;

        ItemStack clickedItem = event.getCurrentItem();

        if(clickedItem == null || clickedItem.getType().isAir())
            return;

        if(!(event.getWhoClicked() instanceof Player player))
            return;

        switch (event.getRawSlot()){
            case 9:
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                GuiUtil.switchState(event.getRawSlot(), event.getInventory());
                challengesService.setNoCrafting(!challengesService.getNoCrafting());
                break;
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        if(!event.getView().getTitle().equals(GuiUtil.CHALLENGE_GUI_NAME))
            return;

        event.setCancelled(true);
    }
}
