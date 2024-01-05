package me.enderluca.verium.listener;

import me.enderluca.verium.util.MessageUtil;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class KillWitherListener implements Listener {

    private final BooleanSupplier isActive;
    private final Consumer<BaseComponent[]> onWitherDead;

    public KillWitherListener(BooleanSupplier isActive, Consumer<BaseComponent[]> onWitherDead){
        this.isActive = isActive;
        this.onWitherDead = onWitherDead;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(event.getEntity().getType() != EntityType.WITHER)
            return;

        Wither wither = (Wither) event.getEntity();
        BaseComponent[] message = MessageUtil.buildKillWitherComplete(wither.getKiller());
        onWitherDead.accept(message);
    }
}
