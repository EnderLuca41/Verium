package me.enderluca.verium.gui.widgets;

import me.enderluca.verium.interfaces.IOnClick;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A button widget that when clicked by the player executes a given action
 */
public class Button extends Widget implements IOnClick {

    @Nonnull
    protected Sound clickSound;

    @Nullable
    protected Consumer<InventoryClickEvent> onClick;
    protected boolean doubleClick;


    public Button(@Nullable ItemStack icon, @Nullable Sound clickSound,
                  @Nullable Consumer<InventoryClickEvent> onClick, boolean doubleClick){
        if(Objects.isNull(icon)){
            ItemStack defaultIcon = new ItemStack(Material.REDSTONE_BLOCK, 1);
            ItemMeta meta = defaultIcon.getItemMeta();
            meta.setDisplayName("Button"); //Guaranteed to not be null
            defaultIcon.setItemMeta(meta);
            this.icon = defaultIcon;
        }
        else
            this.icon = icon;

        this.clickSound = Objects.isNull(clickSound) ? Sound.ENTITY_EXPERIENCE_ORB_PICKUP : clickSound;
        this.onClick = onClick;
        this.doubleClick = doubleClick;
    }


    @Override
    public void render(Inventory inv, int index) {
        inv.setItem(index, icon);
        this.index = index;
    }

    /**
     * Listener for the button click event
     */
    @Override
    public void handleOnClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if(!(event.getWhoClicked() instanceof Player player))
            return;

        if(event.isShiftClick() || !event.isLeftClick())
            return; //We only count non-shift, left clicks

        if((event.getClick() == ClickType.DOUBLE_CLICK) != doubleClick)
            return;

        player.playSound(player.getLocation(), clickSound, 1f, 1f);
        if(Objects.nonNull(onClick))
            onClick.accept(event);
    }
}
