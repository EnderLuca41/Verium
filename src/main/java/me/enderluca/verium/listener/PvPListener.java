package me.enderluca.verium.listener;

import me.enderluca.verium.GameRulesConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvPListener implements Listener {
    private final GameRulesConfig config;
    public PvPListener(GameRulesConfig config){
        this.config = config;
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(config.getPvp())
            return;

        if(!(event.getDamager() instanceof Player))
            return;

        if(!(event.getEntity() instanceof Player))
            return;

        event.setCancelled(true);
    }
}
