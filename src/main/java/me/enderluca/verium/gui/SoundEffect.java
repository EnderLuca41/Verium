package me.enderluca.verium.gui;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

/**
 * Records that encapsulates all parameters needed to play a sound with the playSound method
 */
public record SoundEffect(Sound sound, float volume, float pitch, long seed) {
    public SoundEffect(Sound sound, float volume, float pitch) {
        this(sound, volume, pitch, 0);
    }

    public SoundEffect(Sound sound) {
        this(sound, 1.0F, 1.0F, 0);
    }

    public void play(Player player){
        player.playSound(player.getLocation(), sound, SoundCategory.MASTER, volume, pitch, seed);
    }
}
