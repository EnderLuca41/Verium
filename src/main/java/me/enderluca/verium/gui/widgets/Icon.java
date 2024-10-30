package me.enderluca.verium.gui.widgets;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * A simple widget that displays an itemstack that is not clickable
 */
public class Icon extends Widget {
    public Icon(ItemStack icon){
        this.icon = icon;
    }

    @Override
    public void render(Inventory inv, int index){
        inv.setItem(index, icon);
        this.index = index;
    }
}
