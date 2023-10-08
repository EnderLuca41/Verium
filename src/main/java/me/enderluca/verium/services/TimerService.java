package me.enderluca.verium.services;

import me.enderluca.verium.TimerRunnable;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;
import java.time.Instant;

public class TimerService {

    private final BukkitTask bukkitTask;
    private final TimerRunnable timerRunnable;

    @Nullable
    private Instant timerStarted;
    private long realSeconds;


    public TimerService(Plugin plugin, long startSec, boolean isEnabled){
        timerRunnable = new TimerRunnable(startSec, isEnabled);
        bukkitTask = timerRunnable.runTaskTimer(plugin, 0, 20);

        timerStarted = null;
        realSeconds = startSec;
    }

    /**
     * Pauses the timer and cancels the bukkit task, that broadcasts the timer state.<br>
     * A cancelled timer <STRONG>cannot</STRONG> be activated again
     * @return The total elapsed seconds before pausing
     */
    public long cancel(){
        if(!isPaused())
            pause();
        sync();

        long sec = timerRunnable.getSeconds();
        bukkitTask.cancel();
        return sec;
    }

    /**
     * Starts/unpauses the timer
     * @return if start was successful
     */
    public boolean start(){
        if(!timerRunnable.getPaused())
            return false;
        if(!timerRunnable.getEnabled())
            return false;

        timerStarted = Instant.now();

        timerRunnable.setPaused(false);
        return true;
    }

    /**
     * Pauses the timer but still shows it in the actionbar
     * @return if pause was successful
     */

    public boolean pause() {
        if(timerRunnable.getPaused())
            return false;

        Instant now = Instant.now();
        realSeconds += now.getEpochSecond() - timerStarted.getEpochSecond();
        timerStarted = null;

        timerRunnable.setPaused(true);
        return true;
    }

    /**
     * Resets the time and pauses the timer
     */
    public void reset(){
        timerStarted = null;
        realSeconds = 0;
        timerRunnable.setPaused(true);
        timerRunnable.setSeconds(0);
    }

    /**
     * Enables the timer to be shown in the actionbar.<br>
     * Makes it possible to interact with timer, e.g. start, pause, etc...
     * @return If enabling timer was successful
     */
    public boolean enable(){
        if(isEnabled())
            return false;

        timerRunnable.setEnabled(true);
        return true;
    }

    /**
     * Disables the timer to be shown in the actionbar.<br>
     * Deactivates the ability to interaction with the timer, e.g. start, pause, etc...
     * @return If disabling was successful
     */
    public boolean disable(){
        if(!isEnabled())
            return false;

        if(!isPaused())
            pause();

        timerRunnable.setEnabled(false);
        return true;
    }

    /**
     * Corrects the time of the timer to the real value of elapsed seconds.<br>
     * The timer gets a wrong value over time because the server may run slower than 20 tps.<br>
     * This causes the runnable to be executed fewer than every second, which creates a false elapsed seconds count.
     */
    public void sync(){
        if(!isPaused()){
            Instant now = Instant.now();
            long seconds = now.getEpochSecond() - timerStarted.getEpochSecond() + realSeconds;
            timerStarted = now;
            realSeconds = seconds;

            timerRunnable.setSeconds(seconds);
            return;
        }

        //Timer is paused
        timerRunnable.setSeconds(realSeconds);
    }


    public boolean isPaused(){
        return timerRunnable.getPaused();
    }

    public boolean isEnabled(){
        return timerRunnable.getEnabled();
    }

    public long getEplSeconds(){
        return timerRunnable.getSeconds();
    }

    public void setSeconds(long sec){
        realSeconds = sec;
        if(!isPaused())
            timerStarted = Instant.now();
        timerRunnable.setSeconds(sec);
    }
}
