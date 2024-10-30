package me.enderluca.verium.gui.widgets;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;

/*
 * Base class for all widgets that can be part of a chest gui
 */
public abstract class Widget {

    protected Plugin owner;

    protected ItemStack icon;

    protected int index;

    public int getIndex(){
        return index;
    }
    public void setIndex(int index){
        this.index = index;
    }


    public ItemStack getIcon(){
        return icon;
    }

    public abstract void render(Inventory inv, int index);
}
