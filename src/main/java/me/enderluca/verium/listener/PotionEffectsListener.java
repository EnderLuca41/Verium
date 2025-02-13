package me.enderluca.verium.listener;

import me.enderluca.verium.PotionEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class PotionEffectsListener implements Listener {

    @Nonnull
    private final Consumer<Player> onPlayerLeave;

    @Nonnull
    private final Consumer<Player> onPlayerJoin;

    @Nonnull
    private final Function<PotionEffectType, PotionEffect> getEffect;

    public PotionEffectsListener(@Nonnull Consumer<Player> onPlayerLeave, @Nonnull Consumer<Player> onPlayerJoin, @Nonnull Function<PotionEffectType, PotionEffect> getEffect) {
        this.onPlayerLeave = onPlayerLeave;
        this.onPlayerJoin = onPlayerJoin;
        this.getEffect = getEffect;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        onPlayerJoin.accept(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent event) {
        onPlayerLeave.accept(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPotionEffectChange(EntityPotionEffectEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        PotionEffect effect = getEffect.apply(event.getModifiedType());
        if(!(Objects.nonNull(effect) && effect.isAffected(player.getUniqueId())))
            return;

        if(event.getOldEffect() == null) {
            return;
        }

        if(event.getOldEffect().isInfinite() && event.getCause() != EntityPotionEffectEvent.Cause.PLUGIN) {
            event.setCancelled(true);
            return;
        }
    }
}
