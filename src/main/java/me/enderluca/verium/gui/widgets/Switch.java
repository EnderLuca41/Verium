package me.enderluca.verium.gui.widgets;

import me.enderluca.verium.gui.SoundEffect;
import me.enderluca.verium.interfaces.OnClick;
import me.enderluca.verium.util.GuiUtil;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A switch widget that can be clicked by the player to toggle a boolean state
 */
public class Switch extends Widget implements OnClick {

    @Nonnull
    protected ItemStack trueIcon;
    @Nonnull
    protected ItemStack falseIcon;
    @Nonnull
    protected SoundEffect trueSound;
    @Nonnull
    protected SoundEffect falseSound;

    @Nullable
    protected Supplier<Boolean> getter;
    @Nullable
    protected Consumer<Boolean> setter;

    /**
     * Used when getter is not set
     */
    protected boolean state;

    /**
     * Create a new Switch instance
     * @param trueIcon Icon that is displayed when the switch is in the true state
     * @param falseIcon Icon that is displayed when the switch is in the false state
     * @param getter Function that returns the current state of the switch, if not set, the state will be stored internally
     * @param setter Function that sets the state of the switch
     * @param trueSound Sound that is played when the switch switches to the true state
     * @param falseSound Sound that is played when the switch switches to the false state
     */
    public Switch(@Nullable ItemStack trueIcon, @Nullable ItemStack falseIcon,
                  @Nullable Supplier<Boolean> getter, @Nullable Consumer<Boolean> setter, @Nullable SoundEffect trueSound, @Nullable SoundEffect falseSound){

        this.trueIcon = Objects.requireNonNullElseGet(trueIcon, GuiUtil::getEnabledIcon);
        this.falseIcon = Objects.requireNonNullElseGet(falseIcon, GuiUtil::getDisabledIcon);
        this.trueSound = Objects.requireNonNullElse(trueSound, new SoundEffect(Sound.ENTITY_EXPERIENCE_ORB_PICKUP));
        this.falseSound = Objects.requireNonNullElse(falseSound, new SoundEffect(Sound.ENTITY_EXPERIENCE_ORB_PICKUP));

        this.getter = getter;
        this.setter = setter;
    }

    public boolean getState(){
        if(Objects.isNull(getter))
            return state;
        return getter.get();
    }

    public void setState(boolean val){
        if(setter != null)
            setter.accept(val);
        state = val;
    }


    @Override
    public void render(Inventory inv, int index) {
        boolean switchState = getState();

        if(switchState)
            inv.setItem(index, trueIcon);
        else
            inv.setItem(index, falseIcon);

        this.index = index;
    }

    /**
     * Listener for when the switch is clicked
     */
    @Override
    public void handleOnClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if(!(event.getWhoClicked() instanceof Player player))
            return;

        if(event.isShiftClick() || !event.isLeftClick())
            return; //We only count non-shift, left clicks

        /*
         * if the user double clicks, the event will actually get fired three times,
         * this ensures that a double click will only fire the event two times
         */
        if(event.getClick() == ClickType.DOUBLE_CLICK)
            return;

        setState(!(getState()));
        if(getState())
            trueSound.play(player);
        else
            falseSound.play(player);

        render(event.getInventory(), index);
    }

    @Override
    public ItemStack getIcon() {
        if(getState())
            return trueIcon;
        else return falseIcon;
    }
}
