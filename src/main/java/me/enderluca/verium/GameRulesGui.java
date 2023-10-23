package me.enderluca.verium;

import me.enderluca.verium.services.GameRulesService;
import me.enderluca.verium.util.GuiUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GameRulesGui {
    protected InventoryGui invGui;
    protected GameRulesService service;

    public GameRulesGui(Plugin owner, GameRulesService service){
        invGui = new InventoryGui(owner, 36, "Gamerules");
        this.service = service;

        invGui.createIcon(GuiUtil.getNoHungerIcon(), 0);
        invGui.createSwitch(service::setNoHunger, service::getNoHunger, 9);

        invGui.createIcon(GuiUtil.getPvpIcon(), 1);
        invGui.createSwitch(service::setPvp, service::getPvp, 10);
    }

    public void show(Player player){
        invGui.show(player);
    }
}
