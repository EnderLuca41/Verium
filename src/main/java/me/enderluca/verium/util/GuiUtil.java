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
     * Gets the item that represents the no crafting challenge in the gui
     */
    public static ItemStack getNoCraftingItem(){
        ItemStack noCrafting = new ItemStack(Material.CRAFTING_TABLE, 1);
        ItemMeta meta = noCrafting.getItemMeta();
        meta.setDisplayName("No Crafting"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of("Disables the use of crafting tables."));
        noCrafting.setItemMeta(meta);
        return noCrafting;
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
     * Generates a chest gui that contains the challenges to enable/disable
     */
    public static Inventory generateChallengeGui(ChallengesService challenges){
        Inventory inv = Bukkit.createInventory(null, 36, CHALLENGE_GUI_NAME);

        inv.setItem(0, getNoCraftingItem());
        if(challenges.getNoCrafting())
            inv.setItem(9, getEnabledItem());
        else
            inv.setItem(9, getDisabledItem());


        return inv;
    }

    public static Inventory generateGameRulesGui(GameRulesService gameRules){
        Inventory inv = Bukkit.createInventory(null, 36, GAMERULES_GUI_NAME);

        return inv;
    }
}
