package me.enderluca.verium.services.modifiers;

import me.enderluca.verium.GameModifierType;
import me.enderluca.verium.interfaces.GameModifier;
import me.enderluca.verium.listener.modifiers.SoupListener;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Modifier that enables soup PVP, meaning soups can be consumed to instantly heal
 */
public class SoupModifier implements GameModifier {

    private boolean enabled;
    private boolean paused;

    public final int HEAL_AMOUNT = 4; //Amount on soup heals

    public SoupModifier(Plugin owner, FileConfiguration fileConfig){
        loadConfig(fileConfig);
        owner.getServer().getPluginManager().registerEvents(new SoupListener(() -> enabled && !paused, this::onSoupConsumed), owner);
    }

    void onSoupConsumed(Player player, int slot){
        player.getInventory().setItem(slot, new ItemStack(Material.BOWL));
        double newHealth = player.getHealth() + HEAL_AMOUNT;
        if(newHealth > player.getHealthScale())
            newHealth = player.getHealthScale();
        player.setHealth(newHealth);
    }

    @Override
    public void setEnabled(boolean val){
        enabled = val;
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }

    @Override
    public void setPaused(boolean val){
        paused = val;
    }

    @Override
    public boolean isPaused(){
        return paused;
    }

    @Override
    public GameModifierType getType() {
        return GameModifierType.Soup;
    }

    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("modifiers.soup.enabled", false);
        paused = src.getBoolean("modifiers.soup.paused", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("modifiers.soup.enabled", enabled);
        dest.set("modifiers.soup.paused", paused);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) { } //No world specific config for this modifier
}
