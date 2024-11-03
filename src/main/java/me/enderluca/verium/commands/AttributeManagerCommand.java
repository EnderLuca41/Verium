package me.enderluca.verium.commands;

import me.enderluca.verium.gui.AttributeManagerGui;
import me.enderluca.verium.services.AttributeService;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class AttributeManagerCommand implements CommandExecutor {

    private final AttributeManagerGui gui;

    public AttributeManagerCommand(Plugin owner, ProtocolManager manager, AttributeService service){
        this.gui = new AttributeManagerGui(owner, manager, service);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(!(sender instanceof Player player))
            return true;
        gui.show(player);
        return true;
    }
}
