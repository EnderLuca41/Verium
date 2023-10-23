package me.enderluca.verium.listener;

import me.enderluca.verium.GameRulesConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class NoHungerListener implements Listener {

    private final GameRulesConfig config;

    public NoHungerListener(GameRulesConfig config){
        this.config = config;
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        if(!config.getNoHunger())
            return;

        event.setCancelled(true);
        event.getEntity().setFoodLevel(20); //Food might be not 20, because the player joined after the rule was activated
    }
}
