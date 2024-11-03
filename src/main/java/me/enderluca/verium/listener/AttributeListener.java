package me.enderluca.verium.listener;

import me.enderluca.verium.AttributeChange;
import me.enderluca.verium.util.AttributeUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class AttributeListener implements Listener {


    private final BooleanSupplier isActive;
    private final Consumer<Player> applyAttributes;

    public AttributeListener(BooleanSupplier isActive, Consumer<Player> applyAttributes){
        this.isActive = isActive;
        this.applyAttributes = applyAttributes;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event){
        if(!isActive.getAsBoolean())
            return;

        this.applyAttributes.accept(event.getPlayer());
    }

    //Death resets attributes
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event){
        if(!isActive.getAsBoolean())
            return;

        this.applyAttributes.accept(event.getEntity());
    }
}
