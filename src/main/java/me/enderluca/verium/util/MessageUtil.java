package me.enderluca.verium.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * Provides utility methods to build messages with the component builder
 */
public final class MessageUtil {
    private MessageUtil(){}

    /**
     * Builds the message to be sent over the message bar, that shows the timer
     * @param seconds Total seconds of the timer
     * @param isPaused If the timer is paused
     */
    public static BaseComponent[] buildTimerMessage(long seconds, boolean isPaused){
        ComponentBuilder builder = new ComponentBuilder();
        builder.bold(true);

        if(isPaused)
            builder.append("PAUSED ").color(ChatColor.RED);

        if(seconds / (60 * 60 * 24) != 0){
            builder.append(Long.toString(seconds / 60 / 60 / 24)).color(ColorUtil.ELECTRIC_VIOLET).append("d ").color(ColorUtil.ELECTRIC_VIOLET);
        }

        if(seconds / (60 * 60) != 0){
            builder.append(Long.toString((seconds / 60 / 60) % 24)).color(ChatColor.DARK_PURPLE).append("h ").color(ChatColor.DARK_PURPLE);
        }

        if(seconds / 60 != 0){
            builder.append(Long.toString((seconds / 60) % 60)).color(ColorUtil.MAGENTA).append("m ").color(ColorUtil.MAGENTA);
        }

        builder.append(Long.toString(seconds % 60)).color(ChatColor.LIGHT_PURPLE).append("s ").color(ChatColor.LIGHT_PURPLE);
        return builder.create();
    }


    /**
     * Creates the broadcast message that get sent when the player and his wolf are more than 50 blocks apart
     */
    public static BaseComponent[] buildPlayerWolfToFarApart(String playerName){
        ComponentBuilder builder = new ComponentBuilder();
        builder.append("Player ").color(ChatColor.RED);
        builder.append(playerName).color(ChatColor.GOLD);
        builder.append(" was more than 50 blocks from their wolf apart").color(ChatColor.RED);
        return builder.create();
    }

    /**
     * Creates the broadcast message that get sent when the player and his wolf are seperated in nether and end
     */
    public static BaseComponent[] builderPlayerWolfEndNether(String playerName){
        ComponentBuilder builder = new ComponentBuilder();
        builder.append("Player ").color(ChatColor.RED);
        builder.append(playerName).color(ChatColor.GOLD);
        builder.append(" and his wolf were seperated into the end and nether dimension").color(ChatColor.RED);
        return builder.create();
    }

    /**
     * Creates the broadcast message that get sent when either player or his are in the nether, and there are to far apart
     */
    public static BaseComponent[] buildPlayerWolfNetherApart(String playerName){
        ComponentBuilder builder = new ComponentBuilder();
        builder.append("Player ").color(ChatColor.RED);
        builder.append(playerName).color(ChatColor.GOLD);
        builder.append(" was to far apart from their wolf in a different dimension").color(ChatColor.RED);
        return builder.create();
    }

    /**
     * Creates the broadcast message that get sent when they are seperated in overworld and end for more than 20 seconds
     */
    public static BaseComponent[] buildPlayerWolfEndMessage(String playerName){
        ComponentBuilder builder = new ComponentBuilder();
        builder.append("Player ").color(ChatColor.RED);
        builder.append(playerName).color(ChatColor.GOLD);
        builder.append(" was to long in a different dimension than their dog\n").color(ChatColor.RED);
        builder.append("Be aware that you cannot be seperated in the overworld and end for more than 20 seconds");
        return builder.create();
    }

    /**
     * Creates the broadcast message that get sent when the wolf of a player dies
     */
    public static BaseComponent[] buildWolfDead(String playerName){
        ComponentBuilder builder = new ComponentBuilder();
        builder.append("The wolf of player ").color(ChatColor.RED);
        builder.append(playerName).color(ChatColor.GOLD);
        builder.append(" died").color(ChatColor.RED);
        builder.append("\nRIP Wolfi").color(ChatColor.RED);
        return builder.create();
    }

    /**
     * Creates the broadcast message that get sent when a player receives fall damage
     */
    public static BaseComponent[] buildFallDamage(String playerName, long damage){
        ComponentBuilder builder = new ComponentBuilder();
        builder.append("Player ").color(ChatColor.RED);
        builder.append(playerName).color(ChatColor.GOLD);
        builder.append(" received ").color(ChatColor.RED);
        builder.append(Long.toString(damage)).color(ChatColor.GOLD);
        builder.append("HP").color(ChatColor.GOLD);
        builder.append(" of fall damage").color(ChatColor.RED);
        return builder.create();
    }

    /**
     * Creates the death message when the no death challenge is active
     */
    public static BaseComponent[] buildDeathMessage(String playerName, @Nullable String originalMessage){
        ComponentBuilder builder = new ComponentBuilder();

        if(originalMessage == null){
            return buildDefaultDeathMessage(playerName, null ,builder);
        }

        int index = originalMessage.indexOf(playerName);

        if(index == -1){
            return buildDefaultDeathMessage(playerName, originalMessage, builder);
        }

        //Color the existing message:
        //In theory, all vanilla death message start with the players name
        //But a custom death message may don't have this convention
        builder.append(originalMessage.substring(0, index)).color(ChatColor.RED);
        builder.append(playerName).color(ChatColor.GOLD);
        builder.append(originalMessage.substring(index + 1 + playerName.length() - 1)).color(ChatColor.RED);
        return builder.create();
    }

    private static BaseComponent[] buildDefaultDeathMessage(String playerName, @Nullable String originalMessage, ComponentBuilder builder) {
        builder.append("Player ").color(ChatColor.RED);
        builder.append(playerName).color(ChatColor.GOLD);
        builder.append(" has died.\n").color(ChatColor.RED);
        if(originalMessage != null)
            builder.append(originalMessage).color(ChatColor.RED);
        return builder.create();
    }

    /**
     * Creates the broadcast message that get sent when the kill enderdragon gaol is completed
     */
    public static BaseComponent[] buildKillEnderdragonComplete(@Nullable Player killer){
        ComponentBuilder builder = new ComponentBuilder();

        if(killer != null){
            builder.append("The enderdragon got killed by ").color(ChatColor.GREEN);
            builder.append(killer.getDisplayName()).color(ChatColor.DARK_GREEN);
            builder.append("\n");
        }
        else{
            builder.append("The enderdragon got killed by... uh... by nobody? What are you guys doing?\n").color(ChatColor.GREEN);
        }

        builder.append("Goal complete").color(ChatColor.GREEN);
        return builder.create();
    }

    /**
     * Creates the broadcast message that get sent when the kill wither goal is completed
     */
    public static BaseComponent[] buildKillWitherComplete(@Nullable Player killer){
        ComponentBuilder builder = new ComponentBuilder();

        if(killer != null){
            builder.append("The wither got killed by ").color(ChatColor.GREEN);
            builder.append(killer.getDisplayName()).color(ChatColor.DARK_GREEN);
            builder.append("\n");
        }
        else{
            builder.append("The wither got killed by... uh... by nobody? What are you guys doing?\n").color(ChatColor.GREEN);
        }

        builder.append("Goal complete").color(ChatColor.GREEN);
        return builder.create();
    }

    /**
     * Creates the broadcast message that get sent when the kill elder guardian goal is completed
     */
    public static BaseComponent[] buildKillElderguardianComplete(@Nullable Player killer){
        ComponentBuilder builder = new ComponentBuilder();

        if(killer != null){
            builder.append("The elder guardian got killey by ").color(ChatColor.GREEN);
            builder.append(killer.getDisplayName()).color(ChatColor.DARK_GREEN);
            builder.append("\n");
        }
        else{
            builder.append("The elder guardian got killed by... uh... by nobody? What are you guys doing?\n").color(ChatColor.GREEN);
        }

        builder.append("Goal complete").color(ChatColor.GREEN);
        return builder.create();
    }

    /**
     * Creates the broadcast message that get sent when the kill warden goal is completed
     */
    public static BaseComponent[] buildKillWardenComplete(@Nullable Player killer){
        ComponentBuilder builder = new ComponentBuilder();

        if(killer != null){
            builder.append("The warden got killed by ").color(ChatColor.GREEN);
            builder.append(killer.getDisplayName()).color(ChatColor.DARK_GREEN);
            builder.append("\n");
        }
        else{
            builder.append("The warden got killed by... uh... by nobody? What are you guys doing?\n").color(ChatColor.GREEN);
        }

        builder.append("Goal complete").color(ChatColor.GREEN);
        return builder.create();
    }

    /**
     * Creates the broadcast message that get sent when all goals are completed
     */
    public static BaseComponent[] buildAllGoalsComplete(@Nullable Long timerSeconds){
        ComponentBuilder builder = new ComponentBuilder();
        builder.append("######################################\n").color(ChatColor.GREEN).bold(false);
        builder.append("All goals are completed!\n").color(ChatColor.GREEN);
        if(timerSeconds != null) {
            builder.append("Time: ").color(ChatColor.RED);
            builder.append(buildTimerMessage(timerSeconds, false));
            builder.append("\n");
        }
        builder.append("######################################\n").color(ChatColor.GREEN).bold(false);

        return builder.create();
    }
}
