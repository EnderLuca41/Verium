package me.enderluca.verium.runnable;

import me.enderluca.verium.util.TimerUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerRunnable extends BukkitRunnable {
    private long seconds;
    private boolean isPaused;
    private boolean isEnabled;

    public TimerRunnable(long startSeconds, boolean _isEnabled){
        seconds = startSeconds;
        isPaused = true;
        isEnabled = _isEnabled;
    }

    /**
     * Broadcast the timer to all players on the spigot server
     */
    private void broadcastTimer(long seconds, boolean isPaused){
        BaseComponent[] message = TimerUtil.buildTimerMessage(seconds, isPaused);

        for(Player p : Bukkit.getOnlinePlayers()){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
        }
    }

    @Override
    public void run() {
        if(!isEnabled)
            return;

        if(!isPaused)
            seconds++;

        broadcastTimer(this.seconds, this.isPaused);
    }

    public void setEnabled(boolean value){
        isEnabled = value;
        if(value)
            broadcastTimer(this.seconds, this.isPaused);
        else
        {
            for(Player p : Bukkit.getOnlinePlayers())
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("")); //Empty the actionbar
        }
    }
    public boolean getEnabled(){
        return isEnabled;
    }

    public void setPaused(boolean value){
        isPaused = value;
        broadcastTimer(this.seconds, this.isPaused);
    }
    public boolean getPaused(){
        return isPaused;
    }


    public void setSeconds(long val){
        seconds = val;
        broadcastTimer(this.seconds, this.isPaused);
    }
    public long getSeconds() {
        return seconds;
    }
}
