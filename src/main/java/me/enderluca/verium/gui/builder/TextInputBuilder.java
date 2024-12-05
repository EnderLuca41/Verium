package me.enderluca.verium.gui.builder;

import me.enderluca.verium.gui.SoundEffect;
import me.enderluca.verium.gui.widgets.TextInput;
import me.enderluca.verium.gui.event.TextInputEvent;
import me.enderluca.verium.interfaces.Gui;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TextInputBuilder extends WidgetBuilder {
    @Nullable
    protected SoundEffect clickSound;
    @Nullable
    protected SoundEffect successSound;
    @Nullable
    protected SoundEffect failSound;
    @Nullable
    protected Predicate<String> validator;
    @Nullable
    protected Consumer<TextInputEvent> onTextEntered;
    @Nullable
    protected Supplier<String> preText;

    @Nullable
    protected Gui returnGui;

    public TextInputBuilder(Plugin owner, ProtocolManager manager){
        super(owner, manager);
    }

    /**
     * Sets the sound to play when the player clicks on the text input, if not set a default sound will be used
     */
    public TextInputBuilder addClickSound(SoundEffect sound){
        clickSound = sound;
        return this;
    }

    /**
     * Sets the sound to play when the player submits the text, if not set no sound will be played
     */
    public TextInputBuilder addSuccessSound(SoundEffect sound){
        successSound = sound;
        return this;
    }
    /**
     * Sets the sound to play when the player submits the text, if not set no sound will be played
     */
    public TextInputBuilder addFailSound(SoundEffect sound){
        failSound = sound;
        return this;
    }

    /**
     * Sets the validator to be used to validate the text input, depending on the result the text will be submitted or not and the success/fail sound will be played. <br>
     * The function needs to be thread safe, so avoid side effects when implementing the predicate.
     */
    public TextInputBuilder addValidator(Predicate<String> validator){
        this.validator = validator;
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
     * Sets the text to be pre-entered into the text input
     */
    public TextInputBuilder addPreEnteredText(Supplier<String> preText){
        this.preText = preText;
        return this;

    }

    /**
     * Sets the gui to return to after the text has been entered
     */
    public TextInputBuilder addReturnGui(Gui gui){
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
        return new TextInput(owner, manager, icon, clickSound, successSound, failSound, validator, onTextEntered, returnGui, preText);
    }
}
