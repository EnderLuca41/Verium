package me.enderluca.verium.listener;

import me.enderluca.verium.services.GameRulesService;
import me.enderluca.verium.util.GuiUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class GameRulesInventoryListener implements Listener {
    private final GameRulesService gameRulesService;
    public GameRulesInventoryListener(GameRulesService gameRulesService){
        this.gameRulesService = gameRulesService;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equals(GuiUtil.GAMERULES_GUI_NAME))
            return;

        event.setCancelled(true);

        if(!event.isLeftClick() || event.isShiftClick())
            return;


    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        if(!event.getView().getTitle().equals(GuiUtil.GAMERULES_GUI_NAME))
            return;

        event.setCancelled(true);
    }
}
