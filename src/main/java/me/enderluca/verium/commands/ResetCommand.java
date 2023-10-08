package me.enderluca.verium.commands;

import me.enderluca.verium.services.WorldResetService;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ResetCommand implements TabExecutor {

    private final WorldResetService reset;
    public ResetCommand(WorldResetService service){
        reset = service;
    }
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(args.length == 0) {
            if(reset.isWaitingForConfirmation()){
                sender.spigot().sendMessage(TextComponent
                        .fromLegacyText(ChatColor.RED + "There is already a pending world reset, confirm it with /reset confirm"));
                return true;
            }

            reset.reset();
            sender.spigot().sendMessage(TextComponent.fromLegacyText(ChatColor.GREEN + "Confirm the world reset with /reset confirm"));
            return true;
        }

        if(args.length == 1 && args[0].equals("confirm")){
            if(!reset.isWaitingForConfirmation()){
                sender.spigot().sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "There is no pending world reset"));
                return true;
            }

            reset.confirmAndRestart();
            return true;
        }

        return false;
    }

    @Override
    @Nullable
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(args.length != 1)
            return null;
        if("confirm".startsWith(args[0]))
            return List.of("confirm");
        return null;
    }
}
