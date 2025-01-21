package me.enderluca.verium.gui;

import com.comphenix.protocol.ProtocolManager;
import me.enderluca.verium.gui.builder.ButtonBuilder;
import me.enderluca.verium.gui.widgets.Button;
import me.enderluca.verium.interfaces.Gui;
import me.enderluca.verium.util.GuiUtil;
import me.enderluca.verium.util.PotionUtil;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class PotionEffectsListGui implements Gui {

    @Nonnull
    private final InventoryGui gui;

    @Nonnull
    private final Consumer<PotionEffectType> onEffectSelect;

    public PotionEffectsListGui(@Nonnull Plugin owner, @Nonnull ProtocolManager protocolManager, @Nonnull Gui parent, @Nonnull Consumer<PotionEffectType> onEffectSelect){
        this.onEffectSelect = onEffectSelect;
        gui = new InventoryGui(owner, 36, "Select a potion effect", parent);

        createWidgets(owner, protocolManager);
    }

    private void createWidgets(Plugin owner, ProtocolManager manager){
        ButtonBuilder builder = new ButtonBuilder(owner, manager);

        int i = 0;
        for(PotionEffectType effect : PotionUtil.EFFECTS){
            if(effect.isInstant())
                continue;

            Button button = builder.addIcon(GuiUtil.getPotionEffectIcon(effect))
                    .addClickEvent((e) -> {
                        e.getWhoClicked().closeInventory();
                        onEffectSelect.accept(effect);
                    })
                    .build();
            gui.addWidget(button, i);
            i++;
        }
    }

    @Override
    public void show(Player player){
        gui.show(player);
    }

    @Override
    public void renderWidgets(){
        gui.renderWidgets();
    }
}
