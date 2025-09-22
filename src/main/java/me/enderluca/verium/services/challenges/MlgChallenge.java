package me.enderluca.verium.services.challenges;

import me.enderluca.verium.ChallengeType;
import me.enderluca.verium.interfaces.Challenge;
import me.enderluca.verium.listener.challenges.MlgListener;
import me.enderluca.verium.runnable.MlgRunnable;
import me.enderluca.verium.util.MessageUtil;
import me.enderluca.verium.util.PlayerUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class MlgChallenge implements Challenge, Listener {

    private final int COOLDOWN = 300; //Amount of seconds between each MLG
    private final int COOLDOWN_VARIATION = 60;
    private final int HEIGHT = 40;
    private final int HEIGHT_VARIATION = 5;
    private final boolean ALL_PLAYERS = false; //If true, all players will need to do an MLG each COOLDOWN, if false only one random player will need to do it
    private final int FALLING_TIME = PlayerUtil.fallTime(HEIGHT + HEIGHT_VARIATION);

    private boolean enabled;
    private boolean paused;
    private boolean failed;
    @Nullable
    private BaseComponent[] failedMessage;

    private final Plugin owner;
    private final Consumer<BaseComponent[]> onFail;

    private MlgRunnable runnable;
    private BukkitTask mlgTask;

    private boolean mlgActive = false;

    public MlgChallenge(Plugin owner, FileConfiguration config, Consumer<BaseComponent[]> onFail){
        this.owner = owner;
        this.onFail = onFail;
        loadConfig(config);

        Bukkit.getPluginManager().registerEvents(this, owner);
    }

    private void activate(){
        if(mlgTask != null && !mlgTask.isCancelled()) {
            mlgTask.cancel();
        }

        runnable = new MlgRunnable(ALL_PLAYERS, HEIGHT, HEIGHT_VARIATION, players -> {
            mlgActive = true;
            Bukkit.getScheduler().scheduleSyncDelayedTask(owner, () -> {
                mlgActive = false;
                if(failed)
                    return; //Failed, listener will be called
                runnable.resetPlayers();
                BaseComponent[] successMessage = MessageUtil.buildMlgSuccess();
                Bukkit.spigot().broadcast(successMessage);
                activate();
            }, FALLING_TIME + 10);  ;
        });

        mlgTask = runnable.runTaskLater(owner,(COOLDOWN + (int) (Math.random() * COOLDOWN_VARIATION)) * 20);
    }

    private void deactivate(){
        if(mlgTask == null)
            return;
        if(!mlgTask.isCancelled())
            mlgTask.cancel();
        mlgTask = null;
    }

    @Override
    public void setEnabled(boolean val) {
        if(val == enabled)
            return;

        enabled = val;

        if(enabled && !paused && !failed) {
            activate();
        } else {
            deactivate();
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setPaused(boolean val) {
        if(val == paused)
            return;

        paused = val;

        if(enabled && !paused && !failed) {
            activate();
        } else {
            deactivate();
        }
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Nullable
    @Override
    public BaseComponent[] getFailedMessage() {
        return failedMessage;
    }

    @Override
    public void reset() {
        failed = false;
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.Mlg;
    }

    @Override
    public void loadConfig(FileConfiguration src) {
        enabled = src.getBoolean("challenges.mlg.enabled", false);
        paused = src.getBoolean("challenges.mlg.paused", false);
        failed = src.getBoolean("challenges.mlg.failed", false);
    }

    @Override
    public void saveConfig(FileConfiguration dest) {
        dest.set("challenges.mlg.enabled", enabled);
        dest.set("challenges.mlg.paused", paused);
        dest.set("challenges.mlg.failed", failed);
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest) {
        dest.set("challenges.mlg.failed", false);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event){
        if(!mlgActive)
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(owner, () -> {
            runnable.resetPlayers(); //Delay because the server does not have respawned the dead player yet
        },  1);

        failed = true;
        failedMessage = MessageUtil.buildMlgFail(event.getEntity().getDisplayName());
        deactivate();
        onFail.accept(failedMessage);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onServerLoad(ServerLoadEvent event){
        WorldCreator creator = new WorldCreator("mlg");
        creator.generateStructures(false);
        creator.type(WorldType.FLAT);
        World world = Bukkit.getServer().createWorld(creator);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setAutoSave(false);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlackPlace(BlockPlaceEvent event){
        if(!event.getPlayer().getWorld().getName().equals("mlg"))
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(owner, () -> {
            event.getBlockPlaced().setType(Material.AIR);
        }, 60);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEmptyBucket(PlayerBucketEmptyEvent event){
        if(!event.getPlayer().getWorld().getName().equals("mlg"))
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(owner, () -> {
            event.getBlock().setType(Material.AIR);
        },  60);
    }
}
