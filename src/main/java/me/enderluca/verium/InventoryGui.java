package me.enderluca.verium;

import me.enderluca.verium.function.Getter;
import me.enderluca.verium.function.Setter;
import me.enderluca.verium.util.GuiUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class InventoryGui implements Listener {
    protected String invTitle;
    protected Inventory invGui;
    protected Map<Integer, Setter> switchSetterList;
    protected Map<Integer, Getter> switchGetterList;


    public InventoryGui(Plugin owner, int size, @Nonnull String title){
        invGui = Bukkit.createInventory(null, size, title);
        invTitle = title;

        switchSetterList = new HashMap<>();
        switchGetterList = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, owner);
    }

    /**
     * Creates an icon at the specified location
     * @param icon Item to be shown in the inventory gui
     * @param index Position in inventory gui
     * @return Indicates if creating was successful
     */
    public boolean createIcon(ItemStack icon, int index) {
        if(invGui.getSize() <= index)
            return false;

        if(invGui.getItem(index) != null)
            return false;

        invGui.setItem(index, icon);
        return true;
    }

    /**
     * Create as switch that can be toggled on and off
     * @param setter Method that gets called on a state switch
     * @param getter Function to get the current state of the button
     * @param index Position in inventory gui
     * @return Indicates if creation was successful
     */
    public boolean createSwitch(Setter setter, Getter getter, int index){
        if(invGui.getSize() <= index)
            return false;

        if(invGui.getItem(index) != null)
            return false;

        switchSetterList.put(index, setter);
        switchGetterList.put(index, getter);

        if(getter.get())
            invGui.setItem(index, GuiUtil.getEnabledItem());
        else
            invGui.setItem(index, GuiUtil.getDisabledItem());

        return true;
    }

    /**
     * Sets the state of the switch at the specified index in the inventory gui
     */
    protected void switchSetState(int index, boolean state){
        if(state)
            invGui.setItem(index, GuiUtil.getEnabledItem());
        else
            invGui.setItem(index, GuiUtil.getDisabledItem());
    }

    protected void renderSwitches(){
        for(Map.Entry<Integer, Getter> e : switchGetterList.entrySet()){
            switchSetState(e.getKey(), e.getValue().get());
        }
    }

    public void show(Player player) {
        player.openInventory(invGui);
    }


    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equals(invTitle))
            return;

        event.setCancelled(true);

        if(!(event.getWhoClicked() instanceof Player player))
            return;

        if(event.isShiftClick() || !event.isLeftClick())
            return; //We only count left clicks

        //Check if switch is registered at the clicked index
        Setter setter = switchSetterList.get(event.getRawSlot());
        Getter getter = switchGetterList.get(event.getRawSlot());

        if(setter == null || getter == null)
            return; //In theory, it is impossible for both to be null

        ItemStack item = invGui.getItem(event.getRawSlot());

        if(item == null)
            return; //Should (in theory) be impossible when the class works properly

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);

        boolean currentState = getter.get();
        setter.set(!currentState);
        switchSetState(event.getRawSlot(), !currentState);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        if(!event.getView().getTitle().equals(invTitle))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event){
        if(!event.getView().getTitle().equals(invTitle))
            return;
        renderSwitches();
    }

    public int getSize(){
        return invGui.getSize();
    }

    public String getTitle(){
        return invTitle;
    }
}
