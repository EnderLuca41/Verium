package me.enderluca.verium.services.challenges;

import me.enderluca.verium.interfaces.Challenge;
import me.enderluca.verium.ChallengeType;
import me.enderluca.verium.runnable.WolfSurviveRunnable;
import me.enderluca.verium.listener.challenges.WolfSurviveListener;
import me.enderluca.verium.util.EntityUtil;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class WolfSurviveChallenge implements Challenge {

    private boolean enabled;
    private boolean paused;
    private boolean failed;

    @Nullable
    private BaseComponent[] failedMessage;

    /**
     * Maps player uuid to a entity (wolf) uuid
     */
    private final Map<UUID, UUID> wolfMap;


    private final Consumer<BaseComponent[]> onFail;

    public WolfSurviveChallenge(Plugin owner, FileConfiguration config, Consumer<BaseComponent[]> onFail){
        wolfMap = new HashMap<>();
        this.onFail = onFail;

        loadConfig(config);

        WolfSurviveRunnable runnable = new WolfSurviveRunnable(wolfMap, () -> enabled && !paused && !failed, this::fail);
        runnable.runTaskTimer(owner, 0, 10);

        Bukkit.getPluginManager().registerEvents(new WolfSurviveListener(wolfMap, this::fail, () -> (enabled && !paused)), owner);
    }

    private void fail(BaseComponent[] message){
        failedMessage = message;
        failed = true;
        onFail.accept(message);
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    @Nullable
    public BaseComponent[] getFailedMessage(){
        return failedMessage;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean val){
        if(val == enabled)
            return;

        enabled = val;

        if(paused)
            return;

        if(val){
            for(Player p : Bukkit.getOnlinePlayers()){
                createWolf(p);
            }
        }
        else {
            for(Map.Entry<UUID, UUID> e : wolfMap.entrySet()){
                Entity wolf = Bukkit.getEntity(e.getValue());

                if(wolf == null)
                    continue;

                wolf.remove();
            }
            wolfMap.clear();
        }
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void setPaused(boolean val) {
        if(paused == val)
            return;

        paused = val;

        if(!enabled)
           return;

        if(!val){
            for(Player p : Bukkit.getOnlinePlayers()){
                createWolf(p);
            }
        }
        else{
            for(Map.Entry<UUID, UUID> e : wolfMap.entrySet()){
                Entity wolf = Bukkit.getEntity(e.getValue());

                if(wolf == null)
                    continue;

                wolf.remove();
            }
            wolfMap.clear();
        }
    }
    @Override
    public void reset(){
        failed = false;

        if(!enabled)
            return;

        if(paused){
            wolfMap.clear();
            return;
        }

        for(Map.Entry<UUID, UUID> entry : wolfMap.entrySet()){
            Entity entity = Bukkit.getEntity(entry.getValue());
            if(entity != null)
                entity.remove();
        }

        wolfMap.clear();

        for(Player p : Bukkit.getOnlinePlayers()){
            createWolf(p);
        }
    }

    private void createWolf(Player owner){
        Wolf wolf = EntityUtil.createTamedWolf("Wolfi", owner.getLocation(), owner);
        wolfMap.put(owner.getUniqueId(), wolf.getUniqueId());
    }

    @Override
    public void saveConfig(FileConfiguration dest){
        dest.set("challenges.wolf.enabled", enabled);
        dest.set("challenges.wolf.paused", paused);
        dest.set("challenges.wolf.failed", failed);
        dest.set("challenges.wolf.wolfs", null);

        for(Map.Entry<UUID, UUID> e : wolfMap.entrySet()){
            dest.set("challenges.wolf.wolfs." + e.getKey().toString(), e.getValue().toString());
        }
    }

    @Override
    public void loadConfig(FileConfiguration src){
        enabled = src.getBoolean("challenges.wolf.enabled", false);
        paused = src.getBoolean("challenges.wolf.paused", false);
        failed = src.getBoolean("challenges.wolf.failed", false);


        if(!src.isSet("challenges.wolf.wolfs"))
            return;

        //We already checked if the path is set, so NullPointerException is impossible
        for (String key : src.getConfigurationSection("challenges.wolf.wolfs").getKeys(false)) {

            Pattern uuidCheck = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

            if(!uuidCheck.matcher(key).matches()){
                src.set("challenges.wolf.wolfs." + key, null); //Remove not valid uuid
                continue;
            }

            //Cannot be null, because we got the key though getKeys()
            String valueString = src.getString("challenges.wolf.wolfs." + key);
            if(!uuidCheck.matcher(valueString).matches()){
                src.set("challenges.wolf.wolfs." + key, null); //Remove not valid uuid
                continue;
            }

            UUID value = UUID.fromString(valueString);
            wolfMap.put(UUID.fromString(key), value);
        }
    }

    @Override
    public void clearWorldSpecificConfig(FileConfiguration dest){
        dest.set("challenges.wolf.wolfs", null);
        dest.set("challenges.wolf.failed", false);
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.WolfSurvive;
    }
}
