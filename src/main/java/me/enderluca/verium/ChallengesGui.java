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


        Challenge noDeath = service.getChallenge(ChallengeType.NoDeath);
        if(noDeath != null){
            invGui.createIcon(GuiUtil.getNoDeathIcon(), 0);
            invGui.createSwitch(noDeath::setEnabled, noDeath::isEnabled, 9);
        }

        Challenge noCrafting = service.getChallenge(ChallengeType.NoCrafting);
        if(noCrafting != null) {
            invGui.createIcon(GuiUtil.getNoCraftingIcon(), 1);
            invGui.createSwitch(noCrafting::setEnabled, noCrafting::isEnabled, 10);
        }

        Challenge wolfSurvive = service.getChallenge(ChallengeType.WolfSurvive);
        if(wolfSurvive != null) {
            invGui.createIcon(GuiUtil.getWolfSurviveIcon(), 2);
            invGui.createSwitch(wolfSurvive::setEnabled, wolfSurvive::isEnabled, 11);
        }

        Challenge noFallDamage = service.getChallenge(ChallengeType.NoFallDamage);
        if(noFallDamage != null){
            invGui.createIcon(GuiUtil.getNoFallDamageIcon(), 3);
            invGui.createSwitch(noFallDamage::setEnabled, noFallDamage::isEnabled, 12);
        }
    }

    public void show(Player player){
        invGui.show(player);
    }
}
