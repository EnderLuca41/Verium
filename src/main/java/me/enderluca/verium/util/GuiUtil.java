package me.enderluca.verium.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Define utility functions to handle chest gui's
 */
public final class GuiUtil {
    private GuiUtil(){}

    public static final String CHALLENGE_GUI_NAME = "Challenge selection";
    public static final String GAMERULES_GUI_NAME = "Gamerules";


    private static final ItemStack enabledItemPrototype = new ItemStack(Material.GREEN_STAINED_GLASS, 1);
    private static final ItemStack disabledItemPrototype = new ItemStack(Material.RED_STAINED_GLASS, 1);

    private static boolean prototypesInitialized = false;

    private static void initPrototypes(){
        if(prototypesInitialized)
            return;

        //Item cannot be air, that is why the NullPointerException is impossible
        ItemMeta enabledMeta = enabledItemPrototype.getItemMeta();
        enabledMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.GREEN + "Enabled");
        enabledItemPrototype.setItemMeta(enabledMeta);

        ItemMeta disabledMeta = disabledItemPrototype.getItemMeta();
        disabledMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "Disabled");
        disabledItemPrototype.setItemMeta(disabledMeta);


        prototypesInitialized = true;
    }

    /**
     * Gets the item that symbols the enabled state
     */
    public static ItemStack getEnabledItem(){
        initPrototypes();

        return enabledItemPrototype.clone();
    }

    /**
     * Gets the item that symbols the disabled state
     */
    public static ItemStack getDisabledItem(){
        initPrototypes();

        return disabledItemPrototype.clone();
    }

    //
    //For the next type of gui elements, we will not use prototype, since we don't have to compare them
    //

    /**
     * Gets the item that represents the no crafting challenge in the gui
     */
    public static ItemStack getNoCraftingIcon(){
        ItemStack noCrafting = new ItemStack(Material.CRAFTING_TABLE, 1);
        ItemMeta meta = noCrafting.getItemMeta();
        meta.setDisplayName("No Crafting"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of("Disables the use of crafting tables."));
        noCrafting.setItemMeta(meta);
        return noCrafting;
    }

    /**
     * Gets the item that represents the no hunger game rule in the gui
     */
    public static ItemStack getNoHungerIcon(){
        ItemStack noHunger = new ItemStack(Material.APPLE, 1);
        ItemMeta meta = noHunger.getItemMeta();
        meta.setDisplayName("No Hunger"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of("Fills hunger bar and disables the ability to lose hunger."));
        noHunger.setItemMeta(meta);
        return noHunger;
    }

    /**
     * Gets the item that represents the pvp game rule in the gui
     */
    public static ItemStack getPvpItem(){
        ItemStack pvp = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = pvp.getItemMeta();
        meta.setDisplayName("PvP"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of("Enables the possibility to damage other players."));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        pvp.setItemMeta(meta);
        return pvp;
    }

    /**
     * Check if the passed item indicates the "enabled" state
     */
    public static boolean isEnabledItem(ItemStack item){
        initPrototypes();

        return item.equals(enabledItemPrototype);
    }

    /**
     * Check if the passed item indicates the "disabled" state
     */
    public static boolean isDisabledItem(ItemStack item){
        initPrototypes();

        return item.equals(disabledItemPrototype);
    }

    /**
     * Switches the state between disable/enable in the gui
     */
    public static void switchState(int index, Inventory inv){
        ItemStack item = inv.getItem(index);
        if(item == null)
            return;

        if(GuiUtil.isEnabledItem(item)){
            inv.setItem(index, GuiUtil.getDisabledItem());
            return;
        }

        if(GuiUtil.isDisabledItem(item)){
            inv.setItem(index, GuiUtil.getEnabledItem());
        }
    }
}
