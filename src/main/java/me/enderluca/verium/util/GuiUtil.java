package me.enderluca.verium.util;

import me.enderluca.verium.services.ChallengesService;
import me.enderluca.verium.services.GameRulesService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
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
     * Gets the item that represents the no hunger game rule in the gui
     */
    public static ItemStack getNoHungerItem(){
        ItemStack noHunger = new ItemStack(Material.APPLE, 1);
        ItemMeta meta = noHunger.getItemMeta();
        meta.setDisplayName("No Hunger"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of("Fills hunger bar and disables the ability to lose hunger."));
        noHunger.setItemMeta(meta);
        return noHunger;
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
