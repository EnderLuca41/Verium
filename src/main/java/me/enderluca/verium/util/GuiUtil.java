package me.enderluca.verium.util;

import me.enderluca.verium.ChallengeType;
import me.enderluca.verium.GameruleType;
import me.enderluca.verium.GoalType;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Define utility functions to handle chest gui's
 */
public final class GuiUtil {
    private GuiUtil(){}

    /**
     * Gets the item that symbols the enabled state
     */
    @Nonnull
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
    @Nonnull
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
    @Nonnull
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
    @Nonnull
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
    @Nonnull
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

    /**
     * Returns the item used to represent a certain attribute in the attribute manager GUI
     */
    @Nonnull
    public static ItemStack getAttributeIcon(Attribute attribute){
        switch (attribute){
            case GENERIC_ARMOR -> {
                ItemStack icon = new ItemStack(Material.IRON_CHESTPLATE, 1);
                ItemMeta meta = icon.getItemMeta();
                meta.setLore(List.of("Armor defense points. "));
                meta.setDisplayName(ChatColor.WHITE + "Armor");
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                icon.setItemMeta(meta);
                return icon;
            }

            case GENERIC_ARMOR_TOUGHNESS -> {
                ItemStack icon = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                ItemMeta meta = icon.getItemMeta();
                meta.setDisplayName(ChatColor.WHITE + "Armor toughness");
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                icon.setItemMeta(meta);
                return icon;
            }

            case GENERIC_ATTACK_DAMAGE -> {
                ItemStack icon = new ItemStack(Material.IRON_SWORD, 1);
                ItemMeta meta = icon.getItemMeta();
                meta.setLore(List.of("Damage dealt by attacks, in half-hearts. "));
                meta.setDisplayName(ChatColor.WHITE + "Attack damage");
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                icon.setItemMeta(meta);
                return icon;
            }

            case GENERIC_ATTACK_SPEED -> {
                ItemStack icon = new ItemStack(Material.FEATHER, 1);
                ItemMeta meta = icon.getItemMeta();
                meta.setLore(List.of("Determines recharging rate of attack strength.",
                        "Value is the number of full-strength attacks per second. "));
                meta.setDisplayName(ChatColor.WHITE + "Attack speed");
                icon.setItemMeta(meta);
                return icon;
            }

            case GENERIC_KNOCKBACK_RESISTANCE -> {
                ItemStack icon = new ItemStack(Material.SHIELD, 1);
                ItemMeta meta = icon.getItemMeta();
                meta.setLore(List.of("The scale of horizontal knockback resisted from attacks and projectiles.",
                        "Vertical knockback is not affected. Does not affect explosions.",
                        "The resistance functions as a percentage from 0.0 (0% resistance) to 1.0 (100% resistance)",
                        "(e.g. 0.4 is 40% resistance, meaning the attributed mob takes 60% of usual knockback)"));
                meta.setDisplayName(ChatColor.WHITE + "Knockback resistance");
                icon.setItemMeta(meta);
                return icon;
            }

            case GENERIC_LUCK -> {
                ItemStack icon = new ItemStack(Material.RED_MUSHROOM, 1);
                ItemMeta meta = icon.getItemMeta();
                meta.setLore(List.of("Affects the results of loot tables using the quality or bonus_rolls tag ",
                        "(e.g. when opening chests or chest minecarts, fishing, and killing mobs)."));
                meta.setDisplayName(ChatColor.WHITE + "Luck");
                icon.setItemMeta(meta);
                return icon;
            }

            case GENERIC_MAX_HEALTH -> {
                ItemStack icon = new ItemStack(Material.APPLE, 1);
                ItemMeta meta = icon.getItemMeta();
                meta.setLore(List.of("Maximum health points in half-hearts."));
                meta.setDisplayName(ChatColor.WHITE + "Max health");
                icon.setItemMeta(meta);
                return icon;
            }

            case GENERIC_MOVEMENT_SPEED -> {
                ItemStack icon = new ItemStack(Material.POTION, 1);
                PotionMeta meta = (PotionMeta) icon.getItemMeta();
                meta.setColor(Color.AQUA);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                meta.setDisplayName(ChatColor.WHITE + "Movement speed");
                icon.setItemMeta(meta);
                return icon;
            }

            default -> {return new ItemStack(Material.AIR);}
        }
    }

    /**
     * Returns the item used to represent the add attribute button in the attribute manager gui
     */
    @Nonnull
    public static ItemStack getAddAttributeIcon(){
        ItemStack addAttribute = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta meta = addAttribute.getItemMeta();
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true); //Item cannot be air, so NullPointerException is impossible
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.WHITE + "Add attribute");
        addAttribute.setItemMeta(meta);
        return addAttribute;
    }

    /**
     * Return the item used to represent the switch to switch between blacklist and whitelist in the gui, set on whitelist
     * @param playerList List of all players contained in the access list, used to list them in the lore
     */
    @Nonnull
    public static ItemStack getWhitelistIcon(String playerList){
        ItemStack whitelist = new ItemStack(Material.WHITE_STAINED_GLASS, 1);
        ItemMeta meta = whitelist.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Whitelist"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of(playerList));
        whitelist.setItemMeta(meta);
        return whitelist;
    }

    /**
     * Return the item used to represent the switch to switch between blacklist and whitelist in the gui, set on blacklist
     * @param playerList List of all players contained in the access list, used to list them in the lore
     */
    @Nonnull
    public static ItemStack getBlacklistIcon(String playerList){
        ItemStack blacklist = new ItemStack(Material.BLACK_STAINED_GLASS, 1);
        ItemMeta meta = blacklist.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Blacklist"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of(playerList));
        blacklist.setItemMeta(meta);
        return blacklist;
    }

    /**
     * Returns the item used to represent the add player input in the attribute manager gui, to add a new player to the access list
     */
    @Nonnull
    public static ItemStack getAddPlayerInputIcon(){
        ItemStack addPlayerInput = new ItemStack(Material.BIRCH_SIGN, 1);
        ItemMeta meta = addPlayerInput.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Add player"); //Item cannot be air, so NullPointerException is impossible
        addPlayerInput.setItemMeta(meta);
        return addPlayerInput;
    }

    /**
     * Returns the item used to represent the remove player input in the attribute manager gui, to remove a player from the access list
     */
    @Nonnull
    public static ItemStack getRemovePlayerInputIcon(){
        ItemStack removePlayerInput = new ItemStack(Material.SPRUCE_SIGN, 1);
        ItemMeta meta = removePlayerInput.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Remove player"); //Item cannot be air, so NullPointerException is impossible
        removePlayerInput.setItemMeta(meta);
        return removePlayerInput;
    }

    /**
     * Returns the item used to represent the remove attribute button in the attribute manager gui, which removes an attribute change from the attribute manager
     */
    @Nonnull
    public static ItemStack getRemoveAttributeIcon(){
        ItemStack removeAttribute = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = removeAttribute.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Remove attribute"); //Item cannot be air, so NullPointerException is impossible
        removeAttribute.setItemMeta(meta);
        return removeAttribute;
    }

    /**
     * Returns the item used to represent the change value of an attribute in the attribute manager gui
     * @param attribute The attribute to change the value of, used to add the value range and default value to the item lore
     * @param value Current value the attribute is set to
     */
    @Nonnull
    public static ItemStack getChangeAttributeValueIcon(Attribute attribute, double value){
        ItemStack changeAttributeValue = new ItemStack(Material.COMPARATOR, 1);
        ItemMeta meta = changeAttributeValue.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Change value"); //Item cannot be air, so NullPointerException is impossible
        String line1 = "Current: " + value;
        String line2 = "Range: " + AttributeUtil.getMaxValue(attribute) + " - " + AttributeUtil.getMinValue(attribute);
        String line3 = "Default: " + AttributeUtil.getDefaultBaseValue(attribute);
        meta.setLore(List.of(line1, line2, line3));
        changeAttributeValue.setItemMeta(meta);
        return changeAttributeValue;
    }
}
