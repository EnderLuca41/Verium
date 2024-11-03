package me.enderluca.verium.listener;

import me.enderluca.verium.util.MessageUtil;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class KillWardenListener implements Listener {

    private final BooleanSupplier isActive;
    private final Consumer<BaseComponent[]> onWardenDead;

    public KillWardenListener(BooleanSupplier isActive, Consumer<BaseComponent[]> onWardenDead){
        this.isActive = isActive;
        this.onWardenDead = onWardenDead;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event){
        if(!isActive.getAsBoolean())
            return;

        Entity entity = event.getEntity();

        if(!(entity instanceof Warden warden))
            return;

        BaseComponent[] message = MessageUtil.buildKillWardenComplete(warden.getKiller());
        onWardenDead.accept(message);
    }
}
