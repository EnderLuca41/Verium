package me.enderluca.verium.commands;

import me.enderluca.verium.GameRulesGui;
import me.enderluca.verium.services.GameRulesService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class GameRulesCommand implements CommandExecutor {
    private final GameRulesGui gameRulesGui;

    public GameRulesCommand(Plugin owner, GameRulesService service){
        gameRulesGui = new GameRulesGui(owner, service);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(args.length != 0)
            return false;

        if(!(sender instanceof Player player))
            return false;

        gameRulesGui.show(player);

        return true;
    }
}
