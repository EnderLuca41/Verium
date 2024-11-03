package me.enderluca.verium.listener;

import me.enderluca.verium.util.MessageUtil;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class NoDeathListener implements Listener {

    private final BooleanSupplier isActive;
    private final Consumer<BaseComponent[]> onPlayerDeath;

    public NoDeathListener(BooleanSupplier isActive, Consumer<BaseComponent[]> onPlayerDeath){
        this.isActive = isActive;
        this.onPlayerDeath = onPlayerDeath;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event){
        if(!isActive.getAsBoolean())
            return;

        //For now, we will change the death message instead of passing on through the consumer
        //But for the sake of consistency, we will keep the consumer

        BaseComponent[] deathMessage = MessageUtil.buildDeathMessage(event.getEntity().getDisplayName(), event.getDeathMessage());
        event.setDeathMessage(TextComponent.toLegacyText(deathMessage));
        onPlayerDeath.accept(null);
    }
}
