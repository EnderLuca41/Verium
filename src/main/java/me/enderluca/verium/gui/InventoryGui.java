package me.enderluca.verium.gui;

import me.enderluca.verium.gui.widgets.Widget;
import me.enderluca.verium.interfaces.IInventoryGui;
import me.enderluca.verium.interfaces.IOnClick;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * An inventory gui that can be used to create custom guis with widgets
 */
public class InventoryGui implements IInventoryGui, Listener {

    @Nonnull
    protected Plugin owner;

    protected int inventorySize;
    @Nonnull
    protected String inventoryTitle;

    @Nonnull
    protected Widget[] widgets;

    @Nonnull
    protected Inventory inventory;

    @Nullable
    protected final IInventoryGui parent;

    /**
     * Create a new InventoryGui instance
     * @param owner The owner Plugin
     * @param size Size of the InventoryGui in slots
     * @param parent Parent gui, if this child is closed, the parent will be shown
     */
    public InventoryGui(Plugin owner, int size, @Nonnull String title, @Nullable IInventoryGui parent){
        this.owner = owner;

        inventorySize = size;
        inventoryTitle = title;

        this.parent = parent;

        widgets = new Widget[size];
        inventory = owner.getServer().createInventory(null, size, title);
        Bukkit.getPluginManager().registerEvents(this, owner);
    }

    /**
     * Removes all widgets from the gui
     */
    public void clearWidgets(){
        Arrays.fill(widgets, null);
        inventory.clear();
    }

    /**
     * Renders all widgets in the inventory
     */
    public void renderWidgets(){
        inventory.clear();

        for(int i = 0; i < widgets.length; i++){
            if(widgets[i] != null)
                widgets[i].render(inventory, i);
        }
    }

    /**
     * Adds a widget to the gui, if the widget is a listener, it will be automatically registered
     */
    public void addWidget(Widget widget, int index){
        widgets[index] = widget;
        if(widget instanceof Listener)
            Bukkit.getPluginManager().registerEvents((Listener) widget, owner);
        widget.render(inventory, index);
    }

    @Nonnull
    public List<Widget> getWidgets(){
        return Arrays.stream(widgets).toList();
    }

    public void show(Player player){
        player.openInventory(inventory);
        renderWidgets();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equals(inventoryTitle))
            return;

        //In case of a double click, to take out all the item of one type, the slot will be -999
        if(event.getRawSlot() == -999){
            event.setCancelled(true);
            return;
        }

        Widget clickedWidget = widgets[event.getSlot()];
        if(Objects.nonNull(clickedWidget) && clickedWidget instanceof IOnClick clickableWidget){
            //Widgets exists at the index and can handle the InventoryClickEvent
            clickableWidget.handleOnClick(event);
        }

        event.setCancelled(true); //Ensure the inventory is not changed
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        if(event.getView().getTitle().equals(inventoryTitle))
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(!event.getView().getTitle().equals(inventoryTitle))
            return;
        if(Objects.nonNull(parent))
            Bukkit.getScheduler().runTask(owner, () -> parent.show((Player) event.getPlayer()));
    }
}
