package me.enderluca.verium.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public final class TimerUtil {

    private TimerUtil(){}

    /**
     * Parses the args of the set option of the /timer command
     * @param args Arguments passed to the command handler including "set" in args[0]
     * @return The total seconds the timer has to be set
     */
    public static long parseSetArgs(String[] args) throws NumberFormatException{
        long seconds = 0;

        if(args.length >= 2){
            //Seconds
            seconds += Long.parseLong(args[1]);
        }

        if(args.length >= 3){
            //Minutes
            seconds += Long.parseLong(args[2]) * 60;
        }

        if(args.length >= 4){
            //Hours
            seconds += Long.parseLong(args[3]) * 60 * 60;
        }

        if(args.length >= 5){
            //Days
            seconds += Long.parseLong(args[4]) * 60 * 60 * 24;
        }

        return seconds;
    }
}
