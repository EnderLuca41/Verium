package me.enderluca.verium.gui;

import com.comphenix.protocol.ProtocolManager;
import me.enderluca.verium.gui.builder.ButtonBuilder;
import me.enderluca.verium.gui.widgets.Button;
import me.enderluca.verium.interfaces.Gui;
import me.enderluca.verium.util.AttributeUtil;
import me.enderluca.verium.util.GuiUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * List of attributes in a gui for the player to select from when creating a new attribute change
 */
public class AttributeListGui implements Gui {

    @Nonnull
    private final InventoryGui gui;

    @Nonnull
    private final Plugin owner;
    @Nonnull
    private final ProtocolManager protocolManager;
    @Nonnull
    private final Consumer<Attribute> onAttributeSelected;


    /**
     * Create a new AttributeListGui instance
     * @param owner The owner plugin
     * @param onAttributeSelected Callback when an attribute is selected in the gui
     * @param parent Parent gui, if this child is closed, the parent will be shown
     */
    public AttributeListGui(@Nonnull Plugin owner, @Nonnull ProtocolManager manager, @Nonnull Consumer<Attribute> onAttributeSelected, @Nonnull Gui parent){
        this.owner = owner;
        this.protocolManager = manager;
        this.onAttributeSelected = onAttributeSelected;

        gui = new InventoryGui(owner, 27, "Attributes", parent);
        createAttributeButtons();
    }

    /**
     * Creates all button the gui used for selecting an attribute
     */
    private void createAttributeButtons(){
        ButtonBuilder builder = new ButtonBuilder(owner, protocolManager);

        int i = 0;
        for(Attribute attribute : AttributeUtil.PLAYER_ATTRIBUTES){
            Button button = builder.addIcon(GuiUtil.getAttributeIcon(attribute))
                    .addClickEvent(event -> onButtonClicked((Player) event.getWhoClicked(), attribute))
                    .build();
            gui.addWidget(button, i);
            i++;
        }
    }

    private void onButtonClicked(Player player, Attribute attribute){
        player.closeInventory();
        onAttributeSelected.accept(attribute);
    }

    public void show(Player player){
        gui.show(player);
    }

    public void renderWidgets(){
        gui.renderWidgets();
    }
}
