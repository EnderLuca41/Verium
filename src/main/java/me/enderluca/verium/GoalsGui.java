package me.enderluca.verium;

import me.enderluca.verium.interfaces.Goal;
import me.enderluca.verium.services.GoalsService;
import me.enderluca.verium.util.GuiUtil;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GoalsGui {

    protected InventoryGui invGui;
    protected GoalsService service;

    public GoalsGui(Plugin owner, GoalsService service){
        invGui = new InventoryGui(owner, 36, "Goals");
        this.service = service;

        Goal killEnderdragon = service.getGoal(GoalType.KillEnderdragon);
        if(killEnderdragon != null){
            invGui.createIcon(GuiUtil.getKillEnderDragonIcon(), 0);
            invGui.createSwitch(killEnderdragon::setEnabled, killEnderdragon::isEnabled, 9);
        }

        Goal killWither = service.getGoal(GoalType.KillWither);
        if(killWither != null){
            invGui.createIcon(GuiUtil.getKillWitherIcon(), 1);
            invGui.createSwitch(killWither::setEnabled, killWither::isEnabled, 10);
        }

        Goal killElderguardian = service.getGoal(GoalType.KillElderguardian);
        if(killElderguardian != null){
            invGui.createIcon(GuiUtil.getKillElderguardianIcon(), 2);
            invGui.createSwitch(killElderguardian::setEnabled, killElderguardian::isEnabled, 11);
        }

        Goal killWarden = service.getGoal(GoalType.KillWarden);
        if(killWarden != null){
            invGui.createIcon(GuiUtil.getKillWardenIcon(), 3);
            invGui.createSwitch(killWarden::setEnabled, killWarden::isEnabled, 12);
        }
    }

    public void show(Player player){
        invGui.show(player);
    }
}
