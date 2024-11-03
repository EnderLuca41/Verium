package me.enderluca.verium.commands;

import me.enderluca.verium.gui.GamerulesGui;
import me.enderluca.verium.services.GamerulesService;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class GameRulesCommand implements CommandExecutor {
    private final GamerulesGui gamerulesGui;

    public GameRulesCommand(Plugin owner, ProtocolManager manager, GamerulesService service){
        gamerulesGui = new GamerulesGui(owner, manager, service);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(args.length != 0)
            return false;

        if(!(sender instanceof Player player))
            return false;

        gamerulesGui.show(player);

        return true;
    }
}
