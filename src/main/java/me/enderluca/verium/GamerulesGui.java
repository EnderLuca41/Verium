package me.enderluca.verium;

import me.enderluca.verium.interfaces.Gamerule;
import me.enderluca.verium.services.GamerulesService;
import me.enderluca.verium.util.GuiUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GamerulesGui {
    protected InventoryGui invGui;
    protected GamerulesService service;

    public GamerulesGui(Plugin owner, GamerulesService service){
        invGui = new InventoryGui(owner, 36, "Gamerules");
        this.service = service;

        Gamerule noHunger = service.getGamerule(GameruleType.NoHunger);
        if(noHunger != null) {
            invGui.createIcon(GuiUtil.getNoHungerIcon(), 0);
            invGui.createSwitch(noHunger::setEnabled, noHunger::isEnabled, 9);
        }

        Gamerule pvp = service.getGamerule(GameruleType.Pvp);
        if(pvp != null) {
            invGui.createIcon(GuiUtil.getPvpIcon(), 1);
            invGui.createSwitch(pvp::setEnabled, pvp::isEnabled, 10);
        }

        Gamerule uhc = service.getGamerule(GameruleType.Uhc);
        if(uhc != null){
            invGui.createIcon(GuiUtil.getUhcIcon(), 2);
            invGui.createSwitch(uhc::setEnabled, uhc::isEnabled, 11);
        }
    }

    public void show(Player player){
        invGui.show(player);
    }
}
