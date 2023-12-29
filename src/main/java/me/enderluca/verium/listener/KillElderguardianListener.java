package me.enderluca.verium.listener;

import me.enderluca.verium.util.MessageUtil;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.entity.ElderGuardian;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class KillElderguardianListener implements Listener {

    private final BooleanSupplier isActive;
    private final Consumer<BaseComponent[]> onElderGuardianDead;

    public KillElderguardianListener(BooleanSupplier isActive, Consumer<BaseComponent[]> onElderguardianDead){
        this.isActive = isActive;
        this.onElderGuardianDead = onElderguardianDead;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(!(event.getEntity() instanceof ElderGuardian elderguardian))
            return;

        BaseComponent[] message = MessageUtil.buildKillElderguardianComplete(elderguardian.getKiller());
        onElderGuardianDead.accept(message);
    }
}
