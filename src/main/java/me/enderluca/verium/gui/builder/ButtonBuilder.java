package me.enderluca.verium.gui.builder;

import me.enderluca.verium.gui.widgets.Button;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ButtonBuilder extends WidgetBuilder {
    @Nullable
    protected Sound clickSound = null;
    @Nullable
    protected Consumer<InventoryClickEvent> onClick = null;
    protected boolean doubleClick = false;

    public ButtonBuilder(@Nonnull Plugin owner, @Nonnull ProtocolManager manager){
        super(owner, manager);
    }

    /**
     * Sets the sound to play when the player clicks on the button, if not set a default sound will be used
     */
    public ButtonBuilder addClickSound(Sound sound){
        clickSound = sound;
        return this;
    }

    /**
     * Sets the consumer to be called when the player clicks on the button
     */
    public ButtonBuilder addClickEvent(Consumer<InventoryClickEvent> onClick){
        this.onClick = onClick;
        return this;
    }

    /**
     * Sets if only double clicks should trigger the button
     */
    public ButtonBuilder doubleClick(){
        doubleClick = true;
        return this;
    }

    @Override
    public ButtonBuilder addIcon(@Nullable ItemStack icon){
        this.icon = icon;
        return this;
    }

    @Override
    public Button build(){
        return new Button(icon, clickSound, onClick, doubleClick);
    }
}
