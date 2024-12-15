package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.listener.modifiers.NoArmorListener;

import me.enderluca.verium.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class NoArmorModifier implements GameModifier {

    private boolean enabled;
    private boolean paused;

    public NoArmorModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);

        Bukkit.getPluginManager().registerEvents(new NoArmorListener(() -> enabled && !paused), owner);
    }


    @Override
    public void setEnabled(boolean val){
        if(val == enabled)
            return;

        enabled = val;

        if(enabled && !paused)
            resetArmor();
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }


    @Override
    public void setPaused(boolean val){
        if(val == paused)
            return;

        paused = val;

        if(!paused && enabled)
            resetArmor();
    }

    @Override
    public boolean isPaused(){
        return paused;
    }


    @Override
    public void loadConfig(FileConfiguration src){
        enabled = src.getBoolean("modifiers.noarmor.enabled", false);
        paused = src.getBoolean("modifiers.noarmor.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest){
        dest.set("modifiers.noarmor.enabled", enabled);
        dest.set("modifiers.noarmor.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest){ }


    /**
     * Drops all equipped armor of all players at their location
     */
    private void resetArmor(){
        ItemStack[] emptyArmorSlots = {null, null, null, null};

        for(Player p : Bukkit.getOnlinePlayers()){
            for(ItemStack item : p.getInventory().getContents()){
                if(Objects.isNull(item))
                    continue;

                if(!ItemUtil.isArmor(item))
                    continue;

                p.getInventory().remove(item);
                p.getWorld().dropItem(p.getLocation(), item);
            }

            ItemStack[] emptyArmor = {null, null, null, null};
            p.getInventory().setArmorContents(emptyArmor);
        }


    }


    @Override
    public GameModifierType getType(){
        return GameModifierType.NoArmor;
    }
}
