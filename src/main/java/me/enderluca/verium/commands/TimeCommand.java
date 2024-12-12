package me.enderluca.verium.commands;

import com.comphenix.protocol.ProtocolManager;
import me.enderluca.verium.gui.TimeGui;
import me.enderluca.verium.services.TimeService;

import org.bukkit.Bukkit;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class TimeCommand implements TabExecutor {

    @Nonnull
    private final TimeGui gui;

    public TimeCommand(Plugin owner, ProtocolManager manager, TimeService service) {
        gui = new TimeGui(owner, manager, service);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(args.length == 0 && (sender instanceof Player player)){
            gui.show(player);
            return true;
        }

        Bukkit.dispatchCommand(sender, "time " + String.join(" ", args));
        return true;
    }

    private boolean isNum(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    //Used to provide tab completion similar to the vanilla /time command
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        List<String> first = List.of("set", "add", "query");
        if(args.length == 1){
            return first.stream().filter(e -> e.startsWith(args[0])).toList();
        }
        if(args[0].equals("add")){
            if(args[1].isEmpty())
                return List.of("<time>");

            if(isNum(args[1]))
                return List.of("t", "s", "d");

            return Collections.emptyList();
        }

        if(args[0].equals("set")){
            if(args[1].isEmpty())
                return List.of("day", "night", "noon", "midnight");

            if(isNum(args[1]))
                return List.of("t", "s", "d");

            return Collections.emptyList();
        }

        if(args[0].equals("query")){
            if(args[1].isEmpty())
                return List.of("daytime", "gametime", "day");

            return Collections.emptyList();
        }

        return Collections.emptyList();
    }
}
