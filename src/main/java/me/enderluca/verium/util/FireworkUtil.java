package me.enderluca.verium.util;

import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collection;

/**
 * Utility class for spawning fireworks
 */
public final class FireworkUtil {
    private FireworkUtil(){}


    /**
     * Creates the initial win firework, consisting of multiple fireworks spawning around the players
     * @param owner Owner plugin, needed to create tasks on the bukkit scheduler
     */
    public static void createWinFirework(Collection<? extends Player> players, Plugin owner){
        //Collection was chosen as type for the collection because you can both iterate over it and get its size

        //Relative positions from the player where fireworks should appear
        Vector pos1 = new Vector(1, 0, 0);
        Vector pos2 = new Vector(-1, 0, 0);
        Vector pos3 = new Vector(0, 0, 1);
        Vector pos4 = new Vector(0, 0, -1);

        Firework[] fireworks = new Firework[players.size() * 4];
        int i = 0;

        for(Player p : players) {
            fireworks[i] = EntityUtil.createVictoryFireworkStar(p.getWorld(), p.getLocation().add(pos1));
            fireworks[i+1] = EntityUtil.createVictoryFireworkBurst(p.getWorld(), p.getLocation().add(pos2));
            fireworks[i+2] = EntityUtil.createVictoryFireworkCreeper(p.getWorld(), p.getLocation().add(pos3));
            fireworks[i+3] = EntityUtil.createVictoryFireworkBall(p.getWorld(), p.getLocation().add(pos4));
            i += 4;
        }

        BukkitRunnable detonateFireworks = new BukkitRunnable() {
            @Override
            public void run() {
                Arrays.stream(fireworks).forEach(Firework::detonate);
            }
        };

        detonateFireworks.runTaskLater(owner, 40);
    }

    /**
     * Creates a burst of fireworks, normally gets fired after the initial win firework
     * @param owner Plugin owner, used to create bukkit tasks
     */
    public static void createWinFireworkBurst(Collection<? extends Player> players, Plugin owner){
        //Collection was chosen as type for the collection because you can both iterate over it and get its size

        BukkitRunnable createFireworks = new BukkitRunnable() {
            int timesFired = 0;

            @Override
            public void run() {
                if(timesFired == 10)
                    cancel();

                Firework[] fireworks = new Firework[players.size()];

                //Relative position of the rockets to the players
                Vector relativePos = new Vector(0, 2, 0);

                int i = 0;
                for(Player p : players){
                    fireworks[i] = EntityUtil.createVictoryFireworkStar(p.getWorld(), p.getLocation().add(relativePos));
                    i++;
                }


                BukkitRunnable detonateFireworks = new BukkitRunnable() {
                    @Override
                    public void run() {
                        Arrays.stream(fireworks).forEach(Firework::detonate);
                    }
                };

                detonateFireworks.runTaskLater(owner, 40);

                timesFired ++;
            }
        };

        createFireworks.runTaskTimer(owner, 10, 10);
    }
}
