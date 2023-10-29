package me.enderluca.verium.runnable;

import me.enderluca.verium.util.MessageUtil;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class WolfSurviveRunnable extends BukkitRunnable {
    private final BooleanSupplier isActive;
    private final Consumer<BaseComponent[]> onFail;

    /**
     * Maps a player uuid, to a wolf uuid
     */
    private final Map<UUID, UUID> wolfMap;

    /**
     * Keeps track of the time a player or their wolf entered the end
     */
    private final Map<UUID, Instant> endEntered;

    public WolfSurviveRunnable(Map<UUID, UUID> wolfMap, BooleanSupplier isActive, Consumer<BaseComponent[]> onFail){
        this.wolfMap = wolfMap;
        this.isActive = isActive;
        this.onFail = onFail;

        endEntered = new HashMap<>();
    }

    @Override
    public void run() {
        if(!isActive.getAsBoolean())
            return;

        //Loop through players/wolf to check their
        for(Map.Entry<UUID, UUID> e : wolfMap.entrySet()){
            Player player = Bukkit.getPlayer(e.getKey());

            if(player == null)
                continue; //Player is offline

            Wolf wolf = (Wolf) Bukkit.getEntity(e.getValue());

            if(wolf == null)
                continue; //Should never happen

            World.Environment playerWorldType = player.getWorld().getEnvironment();
            World.Environment wolfWorldType = wolf.getWorld().getEnvironment();

            Instant playerEndEntered = endEntered.get(player.getUniqueId());

            //Check if both player and wolf are in the end or the overworld
            if(playerEndEntered != null && playerWorldType == wolfWorldType)
                endEntered.put(player.getUniqueId(), null);

            //Both are in the same world
            if(playerWorldType == wolfWorldType){
                double deltaX = player.getLocation().getX()  - wolf.getLocation().getX();
                double deltaZ = player.getLocation().getZ() - wolf.getLocation().getZ();

                double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

                if(distance > 50){
                    onFail.accept(MessageUtil.buildPlayerWolfToFarApart(player.getDisplayName()));
                    return;
                }

                return;
            }

            //One is in the Nether and the other one in the end
            if((playerWorldType == World.Environment.NETHER && wolfWorldType == World.Environment.THE_END) ||
                    (playerWorldType == World.Environment.THE_END && wolfWorldType == World.Environment.NETHER)){
                onFail.accept(MessageUtil.builderPlayerWolfEndNether(player.getDisplayName()));
                return;
            }

            //One is in the nether
            if(playerWorldType == World.Environment.NETHER || wolfWorldType == World.Environment.NETHER){
                double netherX = playerWorldType == World.Environment.NETHER ? player.getLocation().getX() : wolf.getLocation().getX();
                double netherZ = playerWorldType == World.Environment.NETHER ? player.getLocation().getZ() : wolf.getLocation().getZ();
                double overworldX = playerWorldType == World.Environment.NETHER ? wolf.getLocation().getX() : player.getLocation().getX();
                double overworldZ = playerWorldType == World.Environment.NETHER ? wolf.getLocation().getZ() : player.getLocation().getZ();

                //Since one block in the nether means 8 in the overworld, we multiply the nether coordinates times 8
                netherX *= 8;
                netherZ *= 8;

                //Since 1.16.2 vanilla minecraft has a connect radius of 16 block in the nether and 128 blocks in the overworld for portals
                //This value is also default in spigot and paper
                //But to be absolutely sure we will use 200

                if(Math.abs(netherX - overworldX) > 200 || Math.abs(netherZ - overworldZ) > 200){
                    onFail.accept(MessageUtil.buildPlayerWolfNetherApart(player.getDisplayName()));
                    return;
                }

                return;
            }

            //One is in the End
            if(playerWorldType == World.Environment.THE_END || wolfWorldType == World.Environment.THE_END){
                Instant now = Instant.now();

                if(playerEndEntered == null){
                    //End entered
                    endEntered.put(player.getUniqueId(), now);
                    return;
                }

                if((now.getEpochSecond() - playerEndEntered.getEpochSecond()) <= 20)
                    return; //One is not more than 20 seconds in the end

                //One is more than 20 seconds in the end
                onFail.accept(MessageUtil.buildPlayerWolfEndMessage(player.getDisplayName()));
                return;
            }

        }
    }
}
