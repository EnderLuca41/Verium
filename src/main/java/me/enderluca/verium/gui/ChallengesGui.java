package me.enderluca.verium.gui;

import me.enderluca.verium.ChallengeType;
import me.enderluca.verium.gui.builder.SwitchBuilder;
import me.enderluca.verium.gui.widgets.Icon;
import me.enderluca.verium.gui.widgets.Switch;
import me.enderluca.verium.interfaces.Challenge;
import me.enderluca.verium.interfaces.IInventoryGui;
import me.enderluca.verium.services.ChallengesService;
import me.enderluca.verium.util.GuiUtil;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

/**
 * Gui that is used to toggle challenges
 */
public class ChallengesGui implements IInventoryGui {

    @Nonnull
    protected final InventoryGui gui;

    public ChallengesGui(Plugin owner, ProtocolManager manager, ChallengesService service){
        gui = new InventoryGui(owner, 54, "Challenges", null);

        SwitchBuilder builder = new SwitchBuilder(owner, manager);

        int i = 0;
        for(ChallengeType type : ChallengeType.values()){
            Challenge challenge = service.getChallenge(type);
            if(challenge == null)
                continue; //Skip if the challenge is not registered
            Icon icon = new Icon(GuiUtil.getChallengeIcon(type));
            Switch sw = builder.addGetter(challenge::isEnabled)
                               .addSetter(challenge::setEnabled)
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
