package me.enderluca.verium.util;

import net.md_5.bungee.api.ChatColor;

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

    public static ItemStack getWolfSurviveIcon(){
        ItemStack wolfSurvive = new ItemStack(Material.BONE, 1);
        ItemMeta meta = wolfSurvive.getItemMeta();
        meta.setDisplayName("Wolf survive");
        meta.setLore(List.of("Every player receives a pet wolf, if the wolf dies, the challenge is over", "Players cannot be more than 50 blocks apart from their wolf"));
        wolfSurvive.setItemMeta(meta);
        return wolfSurvive;
    }

    public static ItemStack getNoFallDamageItem(){
        ItemStack noFallDamage = new ItemStack(Material.IRON_BOOTS, 1);
        ItemMeta meta = noFallDamage.getItemMeta();
        meta.setDisplayName("No fall damage");
        meta.setLore(List.of("If a player receives fall damage, the challenge will fail."));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        noFallDamage.setItemMeta(meta);
        return noFallDamage;
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

    public static ItemStack getUhcIcon(){
        ItemStack uhc = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemMeta meta = uhc.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.ITALIC + "UHC"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of("Disables natural regeneration of health."));
        uhc.setItemMeta(meta);
        return uhc;
    }
}
