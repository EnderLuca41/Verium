package me.enderluca.verium.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Define utility functions to handle chest gui's
 */
public final class GuiUtil {
    private GuiUtil(){}

    /**
     * Gets the item that symbols the enabled state
     */
    public static ItemStack getEnabledIcon(){
        ItemStack enabled = new ItemStack(Material.GREEN_STAINED_GLASS, 1);
        ItemMeta meta = enabled.getItemMeta();
        //Item cannot be air, that is why the NullPointerException is impossible
        meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.GREEN + "Enabled");
        enabled.setItemMeta(meta);
        return enabled;
    }

    /**
     * Gets the item that symbols the disabled state
     */
    public static ItemStack getDisabledIcon(){
        ItemStack disabled = new ItemStack(Material.RED_STAINED_GLASS, 1);
        ItemMeta meta = disabled.getItemMeta();
        //Item cannot be air, that is why the NullPointerException is impossible
        meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "Disabled");
        disabled.setItemMeta(meta);
        return disabled;
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
    public static ItemStack getPvpIcon(){
        ItemStack pvp = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = pvp.getItemMeta();
        meta.setDisplayName("PvP"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of("Enables the possibility to damage other players."));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        pvp.setItemMeta(meta);
        return pvp;
    }
}
