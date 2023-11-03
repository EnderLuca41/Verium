package me.enderluca.verium.commands;

import me.enderluca.verium.services.ModificationsService;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class PauseCommand implements CommandExecutor {

    private final ModificationsService modifications;

    public PauseCommand(ModificationsService service){
        modifications = service;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(args.length != 0)
            return false;

        modifications.pause();
        return true;
    }
}
