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
    }

    public void show(Player player){
        invGui.show(player);
    }
}
