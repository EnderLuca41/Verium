package me.enderluca.verium.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

/**
 * Provides utility methods to build messages with the component builder
 */
public final class MessageUtil {
    private MessageUtil(){}

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
}
