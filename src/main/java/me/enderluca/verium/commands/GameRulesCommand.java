package me.enderluca.verium.commands;

import me.enderluca.verium.listener.GameRulesInventoryListener;
import me.enderluca.verium.services.GameRulesService;
import me.enderluca.verium.util.GuiUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class GameRulesCommand implements CommandExecutor {

    private final GameRulesService gameRulesService;

    public GameRulesCommand(Plugin owner, GameRulesService service){
        gameRulesService = service;

        Bukkit.getPluginManager().registerEvents(new GameRulesInventoryListener(gameRulesService), owner);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(args.length != 0)
            return false;

        if(!(sender instanceof Player player))
            return false;

        Inventory gui = GuiUtil.generateGameRulesGui(gameRulesService);

        player.openInventory(gui);

        return true;
    }
}
