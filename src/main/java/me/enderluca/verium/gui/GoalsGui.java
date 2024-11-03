package me.enderluca.verium.gui;

import me.enderluca.verium.GoalType;
import me.enderluca.verium.gui.widgets.Icon;
import me.enderluca.verium.gui.widgets.Switch;
import me.enderluca.verium.gui.builder.SwitchBuilder;
import me.enderluca.verium.interfaces.Goal;
import me.enderluca.verium.interfaces.IInventoryGui;
import me.enderluca.verium.services.GoalsService;
import me.enderluca.verium.util.GuiUtil;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

/**
 * A gui that allows the player to toggle goals
 */
public class GoalsGui implements IInventoryGui {

    @Nonnull
    protected final InventoryGui gui;

    public GoalsGui(Plugin owner, ProtocolManager manager, GoalsService service){
        gui = new InventoryGui(owner, 54, "Goals", null);

        SwitchBuilder builder = new SwitchBuilder(owner, manager);

        int i = 0;
        for(GoalType type : GoalType.values()){
            Goal goal = service.getGoal(type);
            if(goal == null)
                continue; //Skip if the goal is not registered
            Icon icon = new Icon(GuiUtil.getGoalIcon(type));
            Switch sw = builder.addGetter(goal::isEnabled)
                               .addSetter(goal::setEnabled)
                               .build();

            gui.addWidget(icon, i + ((i / 9) * 18));
            gui.addWidget(sw, i + 9 + ((i / 9) * 18));
            i++;
        }
    }

    @Override
    public void show(Player player) {
        gui.show(player);
    }

    @Override
    public void renderWidgets() {
        gui.renderWidgets();
    }
}
