package me.enderluca.verium.listener;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.function.Consumer;

public class TimeListener implements Listener {

    private final Consumer<World> onWorldLoad;

    public TimeListener(Consumer<World> onWorldLoad){
        this.onWorldLoad = onWorldLoad;
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldLoad(WorldLoadEvent event){
        onWorldLoad.accept(event.getWorld());
    }

}
