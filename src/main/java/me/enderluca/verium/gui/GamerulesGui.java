package me.enderluca.verium.gui;

import me.enderluca.verium.GameruleType;
import me.enderluca.verium.gui.builder.SwitchBuilder;
import me.enderluca.verium.gui.widgets.Icon;
import me.enderluca.verium.gui.widgets.Switch;
import me.enderluca.verium.interfaces.Gamerule;
import me.enderluca.verium.interfaces.IInventoryGui;
import me.enderluca.verium.services.GamerulesService;
import me.enderluca.verium.util.GuiUtil;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * A gui that allows the player to toggle gamerules
 */
public class GamerulesGui implements IInventoryGui {

    protected final InventoryGui gui;

    public GamerulesGui(Plugin owner, ProtocolManager manager, GamerulesService service){
        gui = new InventoryGui(owner, 54, "Gamerules", null);

        SwitchBuilder builder = new SwitchBuilder(owner, manager);

        int i = 0;
        for(GameruleType type : GameruleType.values()){
            Gamerule gamerule = service.getGamerule(type);
            if(gamerule == null)
                continue; //Skip if the gamerule is not registered
            Icon icon = new Icon(GuiUtil.getGameruleIcon(type));
            Switch sw = builder.addGetter(gamerule::isEnabled)
                               .addSetter(gamerule::setEnabled)
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
