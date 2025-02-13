package me.enderluca.verium.commands;

import com.comphenix.protocol.ProtocolManager;
import me.enderluca.verium.gui.PotionEffectGui;
import me.enderluca.verium.services.PotionEffectsService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class PotionEffectsCommand implements CommandExecutor {

    private final PotionEffectGui gui;

    public PotionEffectsCommand(Plugin owner, ProtocolManager manager, PotionEffectsService service) {
        gui = new PotionEffectGui(owner, manager, service);
    }


    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        if(!(sender instanceof Player player))
            return false;
        gui.show(player);
        return true;
    }
}
