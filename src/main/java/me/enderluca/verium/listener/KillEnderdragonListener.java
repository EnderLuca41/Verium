package me.enderluca.verium.listener;

import me.enderluca.verium.util.MessageUtil;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class KillEnderdragonListener  implements Listener {

    private final BooleanSupplier isActive;
    private final Consumer<BaseComponent[]> onEnderdragonDead;

    public KillEnderdragonListener(BooleanSupplier isActive, Consumer<BaseComponent[]> onEnderdragonDead){
        this.isActive = isActive;
        this.onEnderdragonDead = onEnderdragonDead;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(!isActive.getAsBoolean())
            return;

        Entity entity = event.getEntity();

        if(entity.getType() != EntityType.ENDER_DRAGON)
            return;

        EnderDragon dragon = (EnderDragon) entity;
        BaseComponent[] message = MessageUtil.buildKillEnderdragonComplete(dragon.getKiller());
        onEnderdragonDead.accept(message);
    }
}
