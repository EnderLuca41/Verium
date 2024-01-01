package me.enderluca.verium.listener;


import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.function.DoubleSupplier;

public class HealthpointsListener implements Listener {

    private final DoubleSupplier getMaxHp;


    public HealthpointsListener(DoubleSupplier getMaxHp){
        this.getMaxHp = getMaxHp;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        //Exception is impossible, since player entity has this attribute
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(getMaxHp.getAsDouble());
    }
}
