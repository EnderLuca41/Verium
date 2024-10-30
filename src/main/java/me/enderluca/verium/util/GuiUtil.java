package me.enderluca.verium.util;

import me.enderluca.verium.ChallengeType;
import me.enderluca.verium.GameruleType;
import me.enderluca.verium.GoalType;

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
     * Gets the item that represents the specified challenge in the gui
     */
    public static ItemStack getChallengeIcon(ChallengeType type){
        switch (type){
            case NoCrafting -> {
                ItemStack noCrafting = new ItemStack(Material.CRAFTING_TABLE, 1);
                ItemMeta meta = noCrafting.getItemMeta();
                meta.setDisplayName("No Crafting"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Disables the use of crafting tables."));
                noCrafting.setItemMeta(meta);
                return noCrafting;
            }
            case WolfSurvive -> {
                ItemStack wolfSurvive = new ItemStack(Material.BONE, 1);
                ItemMeta meta = wolfSurvive.getItemMeta();
                meta.setDisplayName("Wolf survive"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Every player receives a pet wolf, if the wolf dies, the challenge is over", "Players cannot be more than 50 blocks apart from their wolf"));
                wolfSurvive.setItemMeta(meta);
                return wolfSurvive;
            }

            case NoFallDamage -> {
                ItemStack noFallDamage = new ItemStack(Material.IRON_BOOTS, 1);
                ItemMeta meta = noFallDamage.getItemMeta();
                meta.setDisplayName("No fall damage"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("If a player receives fall damage, the challenge will fail."));
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                noFallDamage.setItemMeta(meta);
                return noFallDamage;
            }

            case NoDeath -> {
                ItemStack noDeath = new ItemStack(Material.PLAYER_HEAD, 1);
                ItemMeta meta = noDeath.getItemMeta();
                meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.ITALIC + "No Death"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("If a player dies, the challenge will fail."));
                noDeath.setItemMeta(meta);
                return noDeath;
            }

            default -> {return new ItemStack(Material.AIR);} //Should never happen
        }
    }

    /**
     * Gets the item that represents the specified gamerule in the gui
     */
    public static ItemStack getGameruleIcon(GameruleType type){
        switch (type){
            case NoHunger -> {
                ItemStack noHunger = new ItemStack(Material.APPLE, 1);
                ItemMeta meta = noHunger.getItemMeta();
                meta.setDisplayName("No Hunger"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Fills hunger bar and disables the ability to lose hunger."));
                noHunger.setItemMeta(meta);
                return noHunger;
            }

            case Pvp -> {
                ItemStack pvp = new ItemStack(Material.IRON_SWORD, 1);
                ItemMeta meta = pvp.getItemMeta();
                meta.setDisplayName("PvP"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Enables the possibility to damage other players."));
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                pvp.setItemMeta(meta);
                return pvp;
            }

            case Uhc -> {
                ItemStack uhc = new ItemStack(Material.GOLDEN_APPLE, 1);
                ItemMeta meta = uhc.getItemMeta();
                meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.ITALIC + "UHC"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Disables natural regeneration of health."));
                uhc.setItemMeta(meta);
                return uhc;
            }

            case Uuhc -> {
                ItemStack uuhc = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
                ItemMeta meta = uuhc.getItemMeta();
                meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.ITALIC + "UUHC"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Disables every kind of regeneration."));
                uuhc.setItemMeta(meta);
                return uuhc;
            }

            case NoVillager -> {
                ItemStack noVillager = new ItemStack(Material.EMERALD, 1);
                ItemMeta meta = noVillager.getItemMeta();
                meta.setDisplayName("No Villager"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Disables trading with villagers and wandering traders."));
                noVillager.setItemMeta(meta);
                return noVillager;
            }

            case NoArmor -> {
                ItemStack noArmor = new ItemStack(Material.IRON_CHESTPLATE, 1);
                ItemMeta meta = noArmor.getItemMeta();
                meta.setDisplayName("No Armor"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Disables the use of armor for players."));
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                noArmor.setItemMeta(meta);
                return noArmor;
            }

            default -> {return new ItemStack(Material.AIR);} //Should never happen
        }
    }


    /**
     * Gets the item that represents the specified goal in the gui
     */
    public static ItemStack getGoalIcon(GoalType type){
        switch(type){
            case KillEnderdragon ->{
                ItemStack killEnderdragon = new ItemStack(Material.ENDERMAN_SPAWN_EGG, 1);
                ItemMeta meta = killEnderdragon.getItemMeta();
                meta.setDisplayName("Kill Enderdragon"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("It does not matter how the enderdragon dies and if he was natural or a respawn."));
                killEnderdragon.setItemMeta(meta);
                return killEnderdragon;
            }

            case KillWither -> {
                ItemStack killWither = new ItemStack(Material.WITHER_SKELETON_SPAWN_EGG, 1);
                ItemMeta meta = killWither.getItemMeta();
                meta.setDisplayName("Kill Wither"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Summon and defeat the wither to complete this goal."));
                killWither.setItemMeta(meta);
                return killWither;
            }

            case KillElderguardian -> {
                ItemStack killElderguardian = new ItemStack(Material.ELDER_GUARDIAN_SPAWN_EGG, 1);
                ItemMeta meta = killElderguardian.getItemMeta();
                meta.setDisplayName("Kill elder guardian"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Defeat the mighty guardian of the ocean monument."));
                killElderguardian.setItemMeta(meta);
                return killElderguardian;
            }

            case KillWarden -> {
                ItemStack killWarden = new ItemStack(Material.WARDEN_SPAWN_EGG, 1);
                ItemMeta meta = killWarden.getItemMeta();
                meta.setDisplayName("Kill Warden"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Defeat the great warrior of the deep dark."));
                killWarden.setItemMeta(meta);
                return killWarden;
            }

            default -> {return new ItemStack(Material.AIR);} //Should never happen
        }
    }
}
