package me.enderluca.verium.listener;

import me.enderluca.verium.util.EntityUtil;
import me.enderluca.verium.util.MessageUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class WolfSurviveListener implements Listener {
    private final Map<UUID, UUID> wolfMap;

    private final Consumer<BaseComponent[]> onFail;
    private final BooleanSupplier isActive;

    public WolfSurviveListener(Map<UUID, UUID> wolfMap, Consumer<BaseComponent[]> onFail, BooleanSupplier isActive){
        this.wolfMap = wolfMap;

        this.onFail = onFail;
        this.isActive = isActive;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(wolfMap.containsKey(event.getPlayer().getUniqueId()))
            return;

        Wolf wolf = EntityUtil.createTamedWolf("Wolfi", event.getPlayer().getLocation(), event.getPlayer());

        wolfMap.put(event.getPlayer().getUniqueId(), wolf.getUniqueId());
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDead(EntityDeathEvent event){
        if(!isActive.getAsBoolean())
            return;

        if(!(event.getEntity() instanceof Wolf wolf))
            return;

        if(wolf.getCustomName() == null || !wolf.getCustomName().equals("Wolfi"))
            return;

        for(Map.Entry<UUID, UUID> entry : wolfMap.entrySet()){
            if(!wolf.getUniqueId().equals(entry.getValue()))
                return;

            //If the wolf is in the map, is also must have an owner
            onFail.accept(MessageUtil.buildWolfDead(((Player) wolf.getOwner()).getDisplayName()));
            return;
        }
    }
}
