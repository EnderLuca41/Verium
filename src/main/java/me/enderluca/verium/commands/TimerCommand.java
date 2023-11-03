package me.enderluca.verium.commands;

import me.enderluca.verium.services.TimerService;

import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class TimerCommand implements TabExecutor {

    private final TimerService timer;

    public TimerCommand(TimerService _timer){
        timer = _timer;
    }
    @Override
    public boolean onCommand(@Nonnull CommandSender  sender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        if(args.length == 0)
            return false;

        if(Objects.equals(args[0], "enable")){
            if(timer.isEnabled()){
                sender.spigot().sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Timer is already enabled"));
                return true;
            }

            timer.enable();
            return true;
       }

        if(Objects.equals(args[0], "disable")){
            if(!timer.isEnabled()){
                sender.spigot().sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Timer is already disabled"));
                return true;
            }

            timer.disable();
            return true;
        }

        if(!timer.isEnabled()){
            sender.spigot().sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Timer is disabled, enable it with /timer enable"));
            return true;
        }


        if(Objects.equals(args[0], "start")){
            if(!timer.isPaused()){
                sender.spigot().sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Timer is not paused"));
                return true;
            }

            timer.start();
            return true;
        }

        if(Objects.equals(args[0], "pause")) {
            if(timer.isPaused()){
                sender.spigot().sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Timer is already paused"));
            }

            timer.pause();
            return true;
        }

        if(Objects.equals(args[0], "reset")){
            timer.reset();
            return true;
        }

        if(Objects.equals(args[0], "sync")){
            timer.sync();
            return true;
        }

        if(Objects.equals(args[0], "set")){
            if(args.length > 5)
                return false; //To many arguments passed

            try{
                long seconds = parseSetArgs(args);
                timer.setSeconds(seconds);
            }
            catch (NumberFormatException ex){
                return  false;
            }

            return true;
        }


        return false;
    }


    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(!(sender instanceof Player))
            return null;

        List<String> options = List.of("start", "pause", "reset", "enable", "disable", "sync", "set");

        if(args.length == 0)
            return null;

        if(args.length == 1)
            return options.stream().filter(x -> x.startsWith(args[0])).toList();

        if(args[0].equals("set")){
            switch (args.length){
                case 2:
                    return List.of("seconds");
                case 3:
                    return List.of("minutes");
                case 4:
                    return List.of("hours");
                case 5:
                    return List.of("days");
            }
        }

        return null;
    }

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
