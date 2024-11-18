package me.enderluca.verium.gui.builder;

import me.enderluca.verium.gui.SoundEffect;
import me.enderluca.verium.gui.widgets.Switch;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Builder class for the {@link Switch} widget
 */
public class SwitchBuilder extends WidgetBuilder {

    @Nullable
    protected ItemStack trueIcon = null;
    @Nullable
    protected ItemStack falseIcon = null;
    @Nullable
    protected SoundEffect trueSound = null;
    @Nullable
    protected SoundEffect falseSound = null;
    @Nullable
    protected Supplier<Boolean> getter = null;
    @Nullable
    protected Consumer<Boolean> setter = null;

    public SwitchBuilder(Plugin owner, ProtocolManager manager){
        super(owner, manager);
    }

    /**
     * Set the icon displayed when the switch is set to true. <br>
     * The default icon is a green glass block with the green text 'Enabled'.
     */
    public SwitchBuilder addTrueIcon(ItemStack icon){
        trueIcon = icon;
        return this;
    }

    /**
     * Set the icon displayed when the switch is set to false. <br>
     * The default icon is a red glass block with the red text 'Disabled'.
     */
    public SwitchBuilder addFalseIcon(ItemStack icon){
        falseIcon = icon;
        return this;
    }

    /**
     * Add a sound that is played when the switch switches to the true state. <br>
     * Default is Sound.ENTITY_EXPERIENCE_ORB_PICKUP
     */
    public SwitchBuilder addTrueSound(SoundEffect sound){
        trueSound = sound;
        return this;
    }

    /**
     * Add a sound that is played when the switch switches to the false state. <br>
     * Default is Sound.ENTITY_EXPERIENCE_ORB_PICKUP
     */
    public SwitchBuilder addFalseSound(SoundEffect sound) {
        falseSound = sound;
        return this;
    }

    /**
     * Add a function for the switch to retrieve its current value. <br>
     * If not getter is set, the switch will save its state internally.
     */
    public SwitchBuilder addGetter(Supplier<Boolean> getter){
        this.getter = getter;
        return this;
    }

    /**
     * Add a function for the switch to save its value. <br>
     * If no setter is set, the switch not be able to save state changes anywhere.
     */
    public SwitchBuilder addSetter(Consumer<Boolean> setter){
        this.setter = setter;
        return this;
    }

    /**
     * This method is not used for the Switch widget, please use addTrueIcon and addFalseIcon instead.
     *
     */
    @Override
    public SwitchBuilder addIcon(ItemStack icon){
        return this;
    }

    /**
     * Build the switch from the configuration mode with the builder.
     */
    public Switch build(){
        return new Switch(trueIcon, falseIcon, getter, setter, trueSound, falseSound);
    }
}
