package me.enderluca.verium.gui;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.gui.builder.SwitchBuilder;
import me.enderluca.verium.gui.widgets.Icon;
import me.enderluca.verium.gui.widgets.Switch;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.interfaces.Gui;
import me.enderluca.verium.services.GameModifierService;
import me.enderluca.verium.util.GuiUtil;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * A gui that allows the player to toggle modifiers
 */
public class GameModifiersGui implements Gui {

    protected final InventoryGui gui;

    public GameModifiersGui(Plugin owner, ProtocolManager manager, GameModifierService service){
        gui = new InventoryGui(owner, 54, "Gamerules", null);

        SwitchBuilder builder = new SwitchBuilder(owner, manager);

        int i = 0;
        for(GameModifierType type : GameModifierType.values()){
            GameModifier gameModifier = service.getModifier(type);
            if(gameModifier == null)
                continue; //Skip if the modifier is not registered
            Icon icon = new Icon(GuiUtil.getGameModifierIcon(type));
            Switch sw = builder.addGetter(gameModifier::isEnabled)
                               .addSetter(gameModifier::setEnabled)
                               .addTrueSound(new SoundEffect(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1.1f))
                               .addFalseSound(new SoundEffect(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.7f))
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
