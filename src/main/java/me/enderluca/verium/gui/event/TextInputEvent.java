package me.enderluca.verium.gui.event;

import org.bukkit.entity.Player;

public class TextInputEvent {
    protected final String text;

    protected final Player player;

    public TextInputEvent(Player player, String text) {
        this.player = player;
        this.text = text;
    }

    /**
     * Returns the text that was entered by the player
     */
    public String getText() {
        return this.text;
    }

    /**
     * Returns the player that entered the text
     */
    public Player getPlayer() {
        return this.player;
    }
}