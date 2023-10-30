package me.enderluca.verium;

import me.enderluca.verium.interfaces.Challenge;
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

        Challenge noCrafting = service.getChallenge(ChallengeType.NoCrafting);
        if(noCrafting != null) {
            invGui.createIcon(GuiUtil.getNoCraftingIcon(), 0);
            invGui.createSwitch(noCrafting::setEnabled, noCrafting::isEnabled, 9);
        }

        Challenge wolfSurvive = service.getChallenge(ChallengeType.WolfSurvive);
        if(wolfSurvive != null) {
            invGui.createIcon(GuiUtil.getWolfSurviveIcon(), 1);
            invGui.createSwitch(wolfSurvive::setEnabled, wolfSurvive::isEnabled, 10);
        }

        Challenge noFallDamage = service.getChallenge(ChallengeType.NoFallDamage);
        if(noFallDamage != null){
            invGui.createIcon(GuiUtil.getNoFallDamageItem(), 2);
            invGui.createSwitch(noFallDamage::setEnabled, noFallDamage::isEnabled, 11);
        }
    }

    public void show(Player player){
        invGui.show(player);
    }
}
