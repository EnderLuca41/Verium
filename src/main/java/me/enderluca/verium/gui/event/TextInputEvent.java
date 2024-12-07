package me.enderluca.verium.gui.event;

import org.bukkit.entity.Player;

public class TextInputEvent {
    protected final String text;

    protected final Player player;

    protected final boolean validationResult;

    public TextInputEvent(Player player, String text, boolean validationResult) {
        this.player = player;
        this.text = text;
        this.validationResult = validationResult;
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

    /**
     * Returns the result of the validation predicate
     */
    public boolean getValidationResult() {
        return this.validationResult;
    }
}
