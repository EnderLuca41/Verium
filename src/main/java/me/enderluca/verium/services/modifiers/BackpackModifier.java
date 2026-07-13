package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class BackpackModifier implements GameModifier, CommandExecutor {

    private boolean enabled;
    private boolean paused;

    private final Inventory backpack;


    public BackpackModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        ((JavaPlugin) owner).getCommand("backpack").setExecutor(this);

        backpack = Bukkit.createInventory(null, 27, "Backpack");
    }

    @Override
    public void setEnabled(boolean val) {
        enabled = val;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setPaused(boolean val) {
        paused = val;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }


    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("modifiers.backpack.enabled", false);
        paused = src.getBoolean("modifiers.backpack.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.backpack.enabled", enabled);
        dest.set("modifiers.backpack.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) {
        dest.set("modifiers.backpack.pack", null);
    }

    @Override
    public GameModifierType getType() {
        return GameModifierType.Backpack;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String s, @Nonnull String[] args){
        if(!enabled || !paused)
            return true;

        if(!(sender instanceof Player player))
            return true;

        player.openInventory(backpack);
        return true;
    }
}
