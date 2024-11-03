package me.enderluca.verium.listener.challenges;

import me.enderluca.verium.util.MessageUtil;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class NoFallDamageListener implements Listener {

    private final BooleanSupplier isActive;
    private final Consumer<BaseComponent[]> onFail;

    public NoFallDamageListener(BooleanSupplier isActive, Consumer<BaseComponent[]> onFail){
        this.isActive = isActive;
        this.onFail = onFail;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!isActive.getAsBoolean())
            return;

        if(event.getCause() != DamageCause.FALL)
            return;

        if (!(event.getEntity() instanceof Player player))
            return;

        onFail.accept(MessageUtil.buildFallDamage(player.getDisplayName(), Math.round(Math.ceil(event.getDamage()))));
    }
}
