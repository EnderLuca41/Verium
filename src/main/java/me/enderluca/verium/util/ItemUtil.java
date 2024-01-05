package me.enderluca.verium.util;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

/**
 * Utility class dealing with items
 */
public final class ItemUtil {
    private ItemUtil(){}

    public static boolean isArmor(ItemStack item){
        Material type = item.getType();

        if(type == Material.ELYTRA)
            return true;

        String typeName = type.name();

        if(typeName.endsWith("_HELMET"))
            return true;
        if(typeName.endsWith("_CHESTPLATE"))
            return true;
        if(typeName.endsWith("_LEGGINGS"))
            return true;
        if(typeName.endsWith("_BOOTS"))
            return true;

        return false;
    }

    public static boolean isArmor(Item item){
        return isArmor(item.getItemStack());
    }
}
