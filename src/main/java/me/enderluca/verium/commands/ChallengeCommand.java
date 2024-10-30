package me.enderluca.verium.commands;

import me.enderluca.verium.gui.ChallengesGui;
import me.enderluca.verium.services.ChallengesService;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class ChallengeCommand implements CommandExecutor {
    private final ChallengesGui challengeGui;

    public ChallengeCommand(Plugin owner, ProtocolManager manager, ChallengesService challengesService){
        challengeGui = new ChallengesGui(owner, manager, challengesService);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, String[] args) {
        if(args.length != 0)
            return false;

        if(!(sender instanceof Player player))
            return false;

        challengeGui.show(player);

        return true;
    }
}
