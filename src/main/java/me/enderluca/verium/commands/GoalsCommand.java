package me.enderluca.verium.commands;

import me.enderluca.verium.gui.GoalsGui;
import me.enderluca.verium.services.GoalsService;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class GoalsCommand implements CommandExecutor {

    private final GoalsGui gui;

    public GoalsCommand(Plugin owner, ProtocolManager manager, GoalsService service){
        gui = new GoalsGui(owner, null, service);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(args.length != 0)
            return false;

        if(!(sender instanceof Player player))
            return true;

        gui.show(player);

        return true;
    }
}
