package me.enderluca.verium.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.function.DoubleConsumer;

public class HealthCommand implements CommandExecutor {

    private final DoubleConsumer setMaxHp;

    public HealthCommand(DoubleConsumer setMaxHp){
        this.setMaxHp = setMaxHp;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args) {
        if(args.length != 1)
            return false;

        try{
            double newHpValue = Double.parseDouble(args[0]);
            setMaxHp.accept(newHpValue);
            return true;
        }
        catch (NumberFormatException e) {
            ComponentBuilder builder = new ComponentBuilder();
            builder.append("Please enter a valid number to set the max health.").color(ChatColor.RED);
            sender.spigot().sendMessage(builder.create());
            return true;
        }
    }
}
