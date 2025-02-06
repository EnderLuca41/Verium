package me.enderluca.verium.util;

import me.enderluca.verium.ChallengeType;
import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.GoalType;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
     * Gets the item that represents the specified game modifier in the gui
     */
    @Nonnull
    public static ItemStack getGameModifierIcon(GameModifierType type){
        switch (type){
            case NoCrafting -> {
                ItemStack noCrafting = new ItemStack(Material.CRAFTING_TABLE, 1);
                ItemMeta meta = noCrafting.getItemMeta();
                meta.setDisplayName("No Crafting"); //Item cannot be air, so NullPointerException is impossible
                meta.setLore(List.of("Disables the use of crafting tables."));
                noCrafting.setItemMeta(meta);
                return noCrafting;
            }

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
        if(attribute == Attribute.ARMOR) {
            ItemStack icon = new ItemStack(Material.IRON_CHESTPLATE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Armor defense points. "));
            meta.setDisplayName(ChatColor.WHITE + "Armor");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.ARMOR_TOUGHNESS) {
            ItemStack icon = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName(ChatColor.WHITE + "Armor toughness");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.ATTACK_DAMAGE) {
            ItemStack icon = new ItemStack(Material.IRON_SWORD, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Damage dealt by attacks, in half-hearts. "));
            meta.setDisplayName(ChatColor.WHITE + "Attack damage");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.ATTACK_KNOCKBACK){
            ItemStack icon = new ItemStack(Material.STICK, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The scale of horizontal knockback applied to entities hit by attacks.",
                    "Vertical knockback is not affected. Does not affect explosions."));
            meta.setDisplayName(ChatColor.WHITE + "Attack knockback");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.ATTACK_SPEED) {
            ItemStack icon = new ItemStack(Material.FEATHER, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Determines recharging rate of attack strength.",
                    "Value is the number of full-strength attacks per second. "));
            meta.setDisplayName(ChatColor.WHITE + "Attack speed");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.KNOCKBACK_RESISTANCE) {
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
        else if(attribute == Attribute.LUCK) {
            ItemStack icon = new ItemStack(Material.RED_MUSHROOM, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Affects the results of loot tables using the quality or bonus_rolls tag ",
                    "(e.g. when opening chests or chest minecarts, fishing, and killing mobs)."));
            meta.setDisplayName(ChatColor.WHITE + "Luck");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.MAX_ABSORPTION) {
            ItemStack icon = new ItemStack(Material.GOLDEN_APPLE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Maximum absorption health points in half-hearts."));
            meta.setDisplayName(ChatColor.WHITE + "Max absorption");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.MAX_HEALTH) {
            ItemStack icon = new ItemStack(Material.APPLE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Maximum health points in half-hearts."));
            meta.setDisplayName(ChatColor.WHITE + "Max health");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.MOVEMENT_SPEED) {
            ItemStack icon = new ItemStack(Material.POTION, 1);
            PotionMeta meta = (PotionMeta) icon.getItemMeta();
            meta.setColor(Color.AQUA);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            meta.setDisplayName(ChatColor.WHITE + "Movement speed");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.SCALE){
            ItemStack icon = new ItemStack(Material.SLIME_BALL, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The scale of the players, the higher the value, the bigger the player."));
            meta.setDisplayName(ChatColor.WHITE + "Scale");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.STEP_HEIGHT){
            ItemStack icon = new ItemStack(Material.SMOOTH_STONE_SLAB, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The maximum height the player can step up without jumping in blocks.",
                    "Sneaking prevents drops from heights that are higher than the step height."));
            meta.setDisplayName(ChatColor.WHITE + "Step height");
            icon.setItemMeta(meta);
            return icon;

        }
        else if(attribute == Attribute.JUMP_STRENGTH){
            ItemStack icon = new ItemStack(Material.RABBIT_FOOT, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The initial velocity of an player when they jump, in blocks per second.",
                    "This directly influcences the jump height."));
            meta.setDisplayName(ChatColor.WHITE + "Jump strength");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.BLOCK_INTERACTION_RANGE){
            ItemStack icon = new ItemStack(Material.FISHING_ROD, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The maximum distance from which a player can interact with blocks.",
                    "This includes breaking blocks, placing blocks, and using blocks (e.g. opening a chest)."));
            meta.setDisplayName(ChatColor.WHITE + "Block interaction range");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.ENTITY_INTERACTION_RANGE){
            ItemStack icon = new ItemStack(Material.NAME_TAG, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The maximum distance from which a player can interact with entities.",
                    "This includes attacking entities and interacting with them (e.g. milking a cow)."));
            meta.setDisplayName(ChatColor.WHITE + "Entity interaction range");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.BLOCK_BREAK_SPEED){
            ItemStack icon = new ItemStack(Material.DIAMOND_PICKAXE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The speed at which a player can break blocks.",
                    "The value is the multiplier of the base speed."));
            meta.setDisplayName(ChatColor.WHITE + "Block break speed");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.GRAVITY){
            ItemStack icon = new ItemStack(Material.ANVIL, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The gravity of the player, the higher the value, the faster the player falls."));
            meta.setDisplayName(ChatColor.WHITE + "Gravity");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.SAFE_FALL_DISTANCE){
            ItemStack icon = new ItemStack(Material.HONEY_BLOCK, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The distance a player can fall before they start taking fall damage.",
                    "The value is in blocks."));
            meta.setDisplayName(ChatColor.WHITE + "Safe fall distance");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.FALL_DAMAGE_MULTIPLIER){
            ItemStack icon = new ItemStack(Material.SLIME_BLOCK, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The multiplier of the fall damage a player takes.",
                    "The value is the multiplier of the base fall damage."));
            meta.setDisplayName(ChatColor.WHITE + "Fall damage multiplier");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.BURNING_TIME){
            ItemStack icon = new ItemStack(Material.LAVA_BUCKET, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Amount of time how long an player remains on fire after being as an multiplier."));
            meta.setDisplayName(ChatColor.WHITE + "Burning time");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.EXPLOSION_KNOCKBACK_RESISTANCE){
            ItemStack icon = new ItemStack(Material.TNT, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The scale of horizontal knockback resisted from explosions.",
                    "Vertical knockback is not affected. Does not affect attacks."));
            meta.setDisplayName(ChatColor.WHITE + "Explosion knockback resistance");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.MINING_EFFICIENCY){
            ItemStack icon = new ItemStack(Material.WOODEN_PICKAXE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("A factor to speed up the mining of blocks when using a tool."));
            meta.setDisplayName(ChatColor.WHITE + "Mining efficiency");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.MOVEMENT_EFFICIENCY){
            ItemStack icon = new ItemStack(Material.SOUL_SAND, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("A factor to improve waling on terrain that slows down movement.",
                    "A value of 1 removed the slowdown effect."));
            meta.setDisplayName(ChatColor.WHITE + "Movement efficiency");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.OXYGEN_BONUS){
            ItemStack icon = new ItemStack(Material.PUFFERFISH, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Decreases the chance of decreasing the air while a player is submerged.",
                    "The chance is given by 1/(1 + oxygen_bonus)."));
            meta.setDisplayName(ChatColor.WHITE + "Oxygen bonus");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.SNEAKING_SPEED){
            ItemStack icon = new ItemStack(Material.LEATHER_BOOTS, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The movement speed factor when sneaking or crawling.",
                    "A factor of 1 means sneaking or crawling is as fast as walking."));
            meta.setDisplayName(ChatColor.WHITE + "Sneaking speed");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.SUBMERGED_MINING_SPEED){
            ItemStack icon = new ItemStack(Material.STONE_PICKAXE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The mining speed factor when underwater.",
                    "A factor of 1 means mining as fast as on land."));
            meta.setDisplayName(ChatColor.WHITE + "Submerged mining speed");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.SWEEPING_DAMAGE_RATIO){
            ItemStack icon = new ItemStack(Material.DIAMOND_SWORD, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Determines how much of the base attack damage gets transferred",
                    "to secondary targets in a sweep attack"));
            meta.setDisplayName(ChatColor.WHITE + "Sweeping damage ratio");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            icon.setItemMeta(meta);
            return icon;
        }
        else if(attribute == Attribute.WATER_MOVEMENT_EFFICIENCY){
            ItemStack icon = new ItemStack(Material.WATER_BUCKET, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("The movement speed factor when submerged."));
            meta.setDisplayName(ChatColor.WHITE + "Water movement efficiency");
            icon.setItemMeta(meta);
            return icon;
        }
        else
            return new ItemStack(Material.AIR); //Should never happen
    }

    /**
     * Returns the item used to represent the add attribute button in the attribute manager gui
     */
    @Nonnull
    public static ItemStack getAddAttributeIcon(){
        ItemStack addAttribute = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta meta = addAttribute.getItemMeta();
        meta.addEnchant(Enchantment.SHARPNESS, 1, true); //Item cannot be air, so NullPointerException is impossible
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.WHITE + "Add attribute");
        addAttribute.setItemMeta(meta);
        return addAttribute;
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

    /**
     * Returns the item used to display the time in minecraft time in the time manager gui
     */
    @Nonnull
    public static ItemStack getTimeIcon(String time){
        ItemStack timeIcon = new ItemStack(Material.CLOCK, 1);
        ItemMeta meta = timeIcon.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "Time: " + ChatColor.GOLD + time);
        timeIcon.setItemMeta(meta);
        return timeIcon;
    }

    /**
     * Returns the item used to display the time in ticks in the time manager gui
     */
    @Nonnull
    public static ItemStack getTimeTicksIcon(long ticks){
        ItemStack timeTicksIcon = new ItemStack(Material.REPEATER, 1);
        ItemMeta meta = timeTicksIcon.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "Time (ticks): " + ChatColor.GOLD + ticks);
        timeTicksIcon.setItemMeta(meta);
        return timeTicksIcon;
    }

    @Nonnull
    public static ItemStack getTimeRealIcon(String realTime){
        ItemStack timeRealIcon = new ItemStack(Material.SUNFLOWER, 1);
        ItemMeta meta = timeRealIcon.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "Time (real): " + ChatColor.GOLD + realTime);
        timeRealIcon.setItemMeta(meta);
        return timeRealIcon;
    }

    /**
     * Returns the item used to represent the freeze time switch in the time manager gui in the true state
     */
    @Nonnull
    public static ItemStack getFreezeTrueIcon(){
        ItemStack freezeTrue = new ItemStack(Material.BLUE_ICE, 1);
        ItemMeta meta = freezeTrue.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Freeze time (on)");
        freezeTrue.setItemMeta(meta);
        return freezeTrue;
    }

    /**
     * Returns the item used to represent the freeze time switch in the time manager gui in the false state
     */
    @Nonnull
    public static ItemStack getFreezeFalseIcon(){
        ItemStack freezeFalse = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS, 1);
        ItemMeta meta = freezeFalse.getItemMeta();
        meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.GRAY + "Freeze time (off)");
        freezeFalse.setItemMeta(meta);
        return freezeFalse;
    }

    /**
     * Returns the item that represents the change ticks button in the time gui, which sets the current time in ticks
     */
    @Nonnull
    public static ItemStack getChangeTicksIcon(){
        ItemStack changeTicks = new ItemStack(Material.OAK_SIGN, 1);
        ItemMeta meta = changeTicks.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Change time (in ticks)"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of("One day has 24000 ticks"));
        changeTicks.setItemMeta(meta);
        return changeTicks;
    }

    /**
     * Returns the item that represents the change time button in the time gui, which sets the current time in minecraft time
     */
    @Nonnull
    public static ItemStack getChangeTimeIcon(){
        ItemStack changeTime = new ItemStack(Material.SPRUCE_SIGN, 1);
        ItemMeta meta = changeTime.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Change time (ingame time)"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of("The format is HH:MM:SS"));
        changeTime.setItemMeta(meta);
        return changeTime;
    }

    /**
     * Returns the item that represents the change time button in the time gui, which sets the current time in real time
     */
    @Nonnull
    public static ItemStack getChangeTimeRealIcon(){
        ItemStack changeTimeReal = new ItemStack(Material.DARK_OAK_SIGN, 1);
        ItemMeta meta = changeTimeReal.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Change time (real time)"); //Item cannot be air, so NullPointerException is impossible
        meta.setLore(List.of("The format is MM:SS"));
        changeTimeReal.setItemMeta(meta);
        return changeTimeReal;
    }

    /**
     * Returns the item that represents the day button in the time gui, which sets the current time to the day preset
     */
    @Nonnull
    public static ItemStack getDayIcon(){
        ItemStack day = new ItemStack(Material.RED_BANNER, 1);
        BannerMeta meta = (BannerMeta) day.getItemMeta();
        meta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.HALF_HORIZONTAL)); //Item cannot be air, so NullPointerException is impossible
        meta.addPattern(new Pattern(DyeColor.RED, PatternType.FLOWER));
        meta.addPattern(new Pattern(DyeColor.RED, PatternType.CIRCLE));
        meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL_BOTTOM));
        meta.addPattern(new Pattern(DyeColor.ORANGE, PatternType.GRADIENT));
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + "Day");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setLore(List.of("Sets the time to 7:00 or 1000 ticks"));
        day.setItemMeta(meta);
        return day;
    }

    /**
     * Returns the item that represents the noon button in the time gui, which sets the current time to the noon preset
     */
    @Nonnull
    public static ItemStack getNoonIcon(){
        ItemStack noon = new ItemStack(Material.WHITE_BANNER, 1);
        BannerMeta meta = (BannerMeta) noon.getItemMeta();
        meta.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.GRADIENT_UP)); //Item cannot be air, so NullPointerException is impossible
        meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.GRADIENT));
        meta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.FLOWER));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE));
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + "Noon");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setLore(List.of("Sets the time to 12:00 or 6000 ticks"));
        noon.setItemMeta(meta);
        return noon;
    }

    /**
     * Returns the item that represents the night button in the time gui, which sets the current time to the night preset
     */
    @Nonnull
    public static ItemStack getNightIcon(){
        ItemStack night = new ItemStack(Material.GRAY_BANNER, 1);
        BannerMeta meta = (BannerMeta) night.getItemMeta();
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.GRADIENT)); //Item cannot be air, so NullPointerException is impossible
        meta.addPattern(new Pattern(DyeColor.MAGENTA, PatternType.GRADIENT));
        meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.GRADIENT_UP));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE));
        meta.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.HALF_HORIZONTAL_BOTTOM));
        meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.GRADIENT_UP));
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + "Night");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setLore(List.of("Sets the time to 19:00 or 13000 ticks"));
        night.setItemMeta(meta);
        return night;
    }

    /**
     * Returns the item that represents the midnight button in the time gui, which sets the current time to the midnight preset
     */
    @Nonnull
    public static ItemStack getMidnightIcon(){
        ItemStack midnight = new ItemStack(Material.BLACK_BANNER, 1);
        BannerMeta meta = (BannerMeta) midnight.getItemMeta();
        meta.addPattern(new Pattern(DyeColor.LIGHT_GRAY, PatternType.BRICKS)); //Item cannot be air, so NullPointerException is impossible
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.BRICKS));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE));
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + "Midnight");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setLore(List.of("Sets the time to 0:00 or 18000 ticks"));
        midnight.setItemMeta(meta);
        return midnight;
    }


    @Nonnull
    public static ItemStack getPotionEffectIcon(PotionEffectType type){
        if(type == PotionEffectType.ABSORPTION){
            ItemStack icon = new ItemStack(Material.GOLDEN_APPLE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Gives the player 4 hp per amplifier level that connot regenerate."));
            meta.setDisplayName(ChatColor.WHITE + "Absorption");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.BAD_OMEN){
            ItemStack icon = new ItemStack(Material.OMINOUS_BOTTLE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            meta.setLore(List.of("Triggers an ominous event when the player enters a village or trial chambers."));
            meta.setDisplayName(ChatColor.WHITE + "Bad omen");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.BLINDNESS){
            ItemStack icon = new ItemStack(Material.BLACK_DYE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Blinds the player."));
            meta.setDisplayName(ChatColor.WHITE + "Blindness");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.CONDUIT_POWER){
            ItemStack icon = new ItemStack(Material.CONDUIT, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Effects granted by a nearby conduit."));
            meta.setDisplayName(ChatColor.WHITE + "Conduit power");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.DARKNESS){
            ItemStack icon = new ItemStack(Material.SCULK, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Causes the player's vision to dim occasionally."));
            meta.setDisplayName(ChatColor.WHITE + "Darkness");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.DOLPHINS_GRACE){
            ItemStack icon = new ItemStack(Material.TUBE_CORAL, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Increases underwater movement speed."));
            meta.setDisplayName(ChatColor.WHITE + "Dolphins grace");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.FIRE_RESISTANCE){
            ItemStack icon = new ItemStack(Material.FIRE_CHARGE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Stops fire damage."));
            meta.setDisplayName(ChatColor.WHITE + "Fire resistance");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.GLOWING){
            ItemStack icon = new ItemStack(Material.GLOWSTONE_DUST, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Outlines the player so that it can be seed from afar."));
            meta.setDisplayName(ChatColor.WHITE + "Glowing");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.HASTE){
            ItemStack icon = new ItemStack(Material.GOLDEN_PICKAXE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setLore(List.of("Increases dig speed."));
            meta.setDisplayName(ChatColor.WHITE + "Haste");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.HEALTH_BOOST){
            ItemStack icon = new ItemStack(Material.APPLE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Increases maximum health of a player."));
            meta.setDisplayName(ChatColor.WHITE + "Health boost");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.HERO_OF_THE_VILLAGE){
            ItemStack icon = new ItemStack(Material.TOTEM_OF_UNDYING, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Reduces the cost of villgaer trades."));
            meta.setDisplayName(ChatColor.WHITE + "Hero of the village");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.HUNGER){
            ItemStack icon = new ItemStack(Material.ROTTEN_FLESH, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Increases hunger."));
            meta.setDisplayName(ChatColor.WHITE + "Hunger");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.INFESTED){
            ItemStack icon = new ItemStack(Material.SILVERFISH_SPAWN_EGG, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Chance of spawning silverfish when hurt."));
            meta.setDisplayName(ChatColor.WHITE + "Infested");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.INSTANT_DAMAGE){
            ItemStack icon = new ItemStack(Material.SPLASH_POTION, 1);
            PotionMeta meta = (PotionMeta) icon.getItemMeta();
            meta.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 1), true);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            meta.setLore(List.of("Hurts an player."));
            meta.setDisplayName(ChatColor.WHITE + "Instant damage");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.INSTANT_HEALTH){
            ItemStack icon = new ItemStack(Material.SPLASH_POTION, 1);
            PotionMeta meta = (PotionMeta) icon.getItemMeta();
            meta.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 1), true);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            meta.setLore(List.of("Heals an player."));
            meta.setDisplayName(ChatColor.WHITE + "Instant health");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.INVISIBILITY){
            ItemStack icon = new ItemStack(Material.WHITE_STAINED_GLASS, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Grants invisibility."));
            meta.setDisplayName(ChatColor.WHITE + "Invisibility");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.JUMP_BOOST){
            ItemStack icon = new ItemStack(Material.RABBIT_FOOT, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Increases jump height."));
            meta.setDisplayName(ChatColor.WHITE + "Jump boost");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.LEVITATION){
            ItemStack icon = new ItemStack(Material.FEATHER, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Causes the player to float in the air."));
            meta.setDisplayName(ChatColor.WHITE + "Levitation");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.LUCK){
            ItemStack icon = new ItemStack(Material.GOLD_NUGGET, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Increases loot table luck."));
            meta.setDisplayName(ChatColor.WHITE + "Luck");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.MINING_FATIGUE){
            ItemStack icon = new ItemStack(Material.BEDROCK, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Decreases dig speed."));
            meta.setDisplayName(ChatColor.WHITE + "Mining fatigue");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.NAUSEA){
            ItemStack icon = new ItemStack(Material.FERMENTED_SPIDER_EYE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Warps vision of the player."));
            meta.setDisplayName(ChatColor.WHITE + "Nausea");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.NIGHT_VISION){
            ItemStack icon = new ItemStack(Material.LANTERN, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Allows the player to see in the dark."));
            meta.setDisplayName(ChatColor.WHITE + "Night vision");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.OOZING){
            ItemStack icon = new ItemStack(Material.SLIME_BALL, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Causes slimes to spawn upon death."));
            meta.setDisplayName(ChatColor.WHITE + "Oozing");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.POISON){
            ItemStack icon = new ItemStack(Material.POISONOUS_POTATO, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Deals damage to the player over time."));
            meta.setDisplayName(ChatColor.WHITE + "Poison");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.RAID_OMEN){
            ItemStack icon = new ItemStack(Material.WHITE_BANNER, 1);
            BannerMeta meta = (BannerMeta) icon.getItemMeta();
            meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.RHOMBUS));
            meta.addPattern(new Pattern(DyeColor.LIGHT_GRAY, PatternType.STRIPE_BOTTOM));
            meta.addPattern(new Pattern(DyeColor.GRAY, PatternType.STRIPE_CENTER));
            meta.addPattern(new Pattern(DyeColor.LIGHT_GRAY, PatternType.BORDER));
            meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_MIDDLE));
            meta.addPattern(new Pattern(DyeColor.LIGHT_GRAY, PatternType.HALF_HORIZONTAL));
            meta.addPattern(new Pattern(DyeColor.LIGHT_GRAY, PatternType.CIRCLE));
            meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.BORDER));
            meta.setLore(List.of("Triggers a raid when a player enters a village."));
            meta.setDisplayName(ChatColor.WHITE + "Raid omen");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.REGENERATION){
            ItemStack icon = new ItemStack(Material.APPLE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Regenerates health."));
            meta.setDisplayName(ChatColor.WHITE + "Regeneration");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.RESISTANCE){
            ItemStack icon = new ItemStack(Material.IRON_CHESTPLATE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Decreases damage dealt to the player."));
            meta.setDisplayName(ChatColor.WHITE + "Resistance");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.SATURATION){
            ItemStack icon = new ItemStack(Material.COOKED_BEEF, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Increases the food level of the player each tick."));
            meta.setDisplayName(ChatColor.WHITE + "Saturation");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.SLOW_FALLING){
            ItemStack icon = new ItemStack(Material.FEATHER, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Slows the fall rate."));
            meta.setDisplayName(ChatColor.WHITE + "Slow falling");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.SLOWNESS){
            ItemStack icon = new ItemStack(Material.SOUL_SAND, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Decreases movement speed."));
            meta.setDisplayName(ChatColor.WHITE + "Slowness");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.SPEED){
            ItemStack icon = new ItemStack(Material.SUGAR, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Increases movement speed."));
            meta.setDisplayName(ChatColor.WHITE + "Speed");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.STRENGTH){
            ItemStack icon = new ItemStack(Material.IRON_SWORD, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Increases damage dealt."));
            meta.setDisplayName(ChatColor.WHITE + "Strength");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.TRIAL_OMEN){
            ItemStack icon = new ItemStack(Material.TRIAL_SPAWNER, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Causes trial spawners to become ominous."));
            meta.setDisplayName(ChatColor.WHITE + "Trial omen");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.UNLUCK){
            ItemStack icon = new ItemStack(Material.COAL, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Loot table unluck."));
            meta.setDisplayName(ChatColor.WHITE + "Unluck");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.WATER_BREATHING){
            ItemStack icon = new ItemStack(Material.WATER_BUCKET, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Allows breathing underwater."));
            meta.setDisplayName(ChatColor.WHITE + "Water breathing");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.WEAKNESS){
            ItemStack icon = new ItemStack(Material.WOODEN_SWORD, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Decreases damage dealt."));
            meta.setDisplayName(ChatColor.WHITE + "Weakness");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.WEAVING){
            ItemStack icon = new ItemStack(Material.COBWEB, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Creates cobwebs upon death."));
            meta.setDisplayName(ChatColor.WHITE + "Weaving");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.WIND_CHARGED){
            ItemStack icon = new ItemStack(Material.BREEZE_ROD, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Emits a wind burst upon death."));
            meta.setDisplayName(ChatColor.WHITE + "Wind charged");
            icon.setItemMeta(meta);
            return icon;
        }
        else if(type == PotionEffectType.WITHER){
            ItemStack icon = new ItemStack(Material.WITHER_ROSE, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(List.of("Deals damage to the player over time."));
            meta.setDisplayName(ChatColor.WHITE + "Wither");
            icon.setItemMeta(meta);
            return icon;
        }
        else
            return new ItemStack(Material.AIR); //Should never happen
    }

    /**
     * Returns the item that represents the change amplifier input in the potion effect gui
     */
    public static ItemStack getChangeAmplifierIcon(int currentAmplifier){
        ItemStack icon = new ItemStack(Material.COMPARATOR);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Change amplifier");
        meta.setLore(List.of("Current: " + currentAmplifier, "Range: 0 - 127"));
        icon.setItemMeta(meta);
        return icon;
    }

    /**
     * Returns the item that represents the particles switch in the potion effect gui, set to true
     */
    public static ItemStack getParticlesSwitchIconTrue(){
        ItemStack icon = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Particles");
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        icon.setItemMeta(meta);
        return icon;
    }

    /**
     * Returns the item that represents the particles switch in the potion effect gui, set to false
     */
    public static ItemStack getParticlesSwitchIconFalse(){
        ItemStack icon = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Particles");
        icon.setItemMeta(meta);
        return icon;
    }

    /**
     * Returns the item that represents the remove potion effect button in the potion effect gui
     */
    public static ItemStack getRemovePotionEffectIcon(){
        ItemStack icon = new ItemStack(Material.BARRIER);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Remove effect");
        icon.setItemMeta(meta);
        return icon;
    }

    /**
     * Returns the item that represents the add potion effect button in the potion effect gui
     */
    public static ItemStack getAddPotionEffectIcon(){
        ItemStack addAttribute = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta meta = addAttribute.getItemMeta();
        meta.addEnchant(Enchantment.SHARPNESS, 1, true); //Item cannot be air, so NullPointerException is impossible
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.WHITE + "Add potion effect");
        addAttribute.setItemMeta(meta);
        return addAttribute;
    }

    /**
     * Return the item used to represent the switch to switch between blacklist and whitelist in a gui, set on whitelist
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
     * Return the item used to represent the switch to switch between blacklist and whitelist in a gui, set on blacklist
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
     * Returns the item used to represent the add player input in a gui, to add a new player to the access list
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
     * Returns the item used to represent the remove player input in a gui, to remove a player from the access list
     */
    @Nonnull
    public static ItemStack getRemovePlayerInputIcon(){
        ItemStack removePlayerInput = new ItemStack(Material.SPRUCE_SIGN, 1);
        ItemMeta meta = removePlayerInput.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Remove player"); //Item cannot be air, so NullPointerException is impossible
        removePlayerInput.setItemMeta(meta);
        return removePlayerInput;
    }

}
