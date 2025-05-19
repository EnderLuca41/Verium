package me.enderluca.verium.listener.modifiers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class SharedDamageListener implements Listener {

    @Nonnull
    private final BooleanSupplier isActive;

    public SharedDamageListener(@Nonnull BooleanSupplier isActive){
        this.isActive = isActive;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerDamage(EntityDamageEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(!(event.getEntity() instanceof Player player))
            return;

        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getUniqueId() == player.getUniqueId())
                continue;

            double newHealth = p.getHealth() - event.getFinalDamage();
            if (newHealth < 0)
                newHealth = 0;
            p.setHealth(newHealth); //Does not invoke damage event
        }
    }
}
