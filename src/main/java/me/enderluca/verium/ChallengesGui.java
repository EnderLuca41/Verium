package me.enderluca.verium;

import me.enderluca.verium.services.ChallengesService;
import me.enderluca.verium.util.GuiUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ChallengesGui {
    protected InventoryGui invGui;
    protected ChallengesService service;

    public ChallengesGui(Plugin owner, ChallengesService service){
        invGui = new InventoryGui(owner, 36, "Challenge selection");
        this.service = service;

        invGui.createIcon(GuiUtil.getNoCraftingIcon(), 0);
        invGui.createSwitch(service::setNoCrafting, service::getNoCrafting, 9);
    }

    public void show(Player player){
        invGui.show(player);
    }
}
