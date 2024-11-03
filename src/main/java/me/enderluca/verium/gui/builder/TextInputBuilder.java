package me.enderluca.verium.gui.builder;

import me.enderluca.verium.gui.widgets.TextInput;
import me.enderluca.verium.gui.event.TextInputEvent;
import me.enderluca.verium.interfaces.IInventoryGui;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class TextInputBuilder extends WidgetBuilder {
    @Nullable
    protected Sound clickSound;
    @Nullable
    protected Sound doneSound;
    @Nullable
    protected Consumer<TextInputEvent> onTextEntered;

    @Nullable
    protected IInventoryGui returnGui;

    public TextInputBuilder(Plugin owner, ProtocolManager manager){
        super(owner, manager);
    }

    /**
     * Sets the sound to play when the player clicks on the text input, if not set a default sound will be used
     */
    public TextInputBuilder addClickSound(Sound sound){
        clickSound = sound;
        return this;
    }

    /**
     * Sets the sound to play when the player submits the text, if not set a default sound will be used
     */
    public TextInputBuilder addDoneSound(Sound sound){
        doneSound = sound;
        return this;
    }

    /**
     * Sets the consumer to be called when the player submits the text
     */
    public TextInputBuilder listenOnTextEntered(Consumer<TextInputEvent> onTextEntered){
        this.onTextEntered = onTextEntered;
        return this;
    }

    /**
     * Sets the gui to return to after the text has been entered
     */
    public TextInputBuilder addReturnGui(IInventoryGui gui){
        returnGui = gui;
        return this;
    }

    @Override
    public TextInputBuilder addIcon(@Nullable ItemStack icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public TextInput build() {
        return new TextInput(owner, manager, icon, clickSound, doneSound, onTextEntered, returnGui);
    }
}
