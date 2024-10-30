package me.enderluca.verium.gui.widgets;

import me.enderluca.verium.interfaces.IOnClick;
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
public class Switch extends Widget implements IOnClick {

    @Nonnull
    protected ItemStack trueIcon;
    @Nonnull
    protected ItemStack falseIcon;
    @Nonnull
    protected Sound clickSound;

    @Nullable
    protected Supplier<Boolean> getter;
    @Nullable
    protected Consumer<Boolean> setter;

    /**
     * Used when getter is not set
     */
    protected boolean state;

    public Switch(@Nullable ItemStack trueIcon, @Nullable ItemStack falseIcon,
                  @Nullable Supplier<Boolean> getter, @Nullable Consumer<Boolean> setter, @Nullable Sound clickSound){

        this.trueIcon = Objects.requireNonNullElseGet(trueIcon, GuiUtil::getEnabledIcon);
        this.falseIcon = Objects.requireNonNullElseGet(falseIcon, GuiUtil::getDisabledIcon);
        this.clickSound = Objects.isNull(clickSound) ? Sound.ENTITY_EXPERIENCE_ORB_PICKUP : clickSound;

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
        player.playSound(player.getLocation(), clickSound, 1f, 1f);

        render(event.getInventory(), index);
    }

    @Override
    public ItemStack getIcon() {
        if(getState())
            return trueIcon;
        else return falseIcon;
    }
}
