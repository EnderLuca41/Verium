package me.enderluca.verium.commands;

import me.enderluca.verium.gui.GameModifiersGui;
import me.enderluca.verium.services.GameModifierService;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class GameModifiersCommand implements CommandExecutor {
    private final GameModifiersGui gameModifiersGui;

    public GameModifiersCommand(Plugin owner, ProtocolManager manager, GameModifierService service){
        gameModifiersGui = new GameModifiersGui(owner, manager, service);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(args.length != 0)
            return false;

        if(!(sender instanceof Player player))
            return false;

        gameModifiersGui.show(player);

        return true;
    }
}
