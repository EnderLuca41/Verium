package me.enderluca.verium.gui.widgets;

import me.enderluca.verium.gui.SoundEffect;
import me.enderluca.verium.gui.event.TextInputEvent;
import me.enderluca.verium.interfaces.Gui;
import me.enderluca.verium.interfaces.OnClick;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;

/**
 * A text input widget that allows the player to input text into a sign
 */
public class TextInput extends Widget implements OnClick, Listener {

    @Nullable
    protected SoundEffect clickSound;
    @Nullable
    protected SoundEffect successSound;
    @Nullable
    protected SoundEffect failSound;

    @Nonnull
    protected final Predicate<String> validator;
    @Nullable
    protected final Consumer<TextInputEvent> onTextEntered;
    @Nullable
    protected final Supplier<String> preText;

    @Nonnull
    private final ProtocolManager manager;

    @Nullable
    private final Gui returnGui;

    protected final boolean callOnValidationFail;

    /**
     * @param clickSound The sound to play when the player clicks on the text input, if not set no sound will be played
     * @param successSound The sound to play when the player submits the text, if not set no sound will be played
     * @param failSound The sound to play when the player if the validator returns false, if not set no sound will be played
     * @param validator The predicate to validate the text entered by the player, depending on the return value success/fail sound will be played and on success onTextEntered will be called <br>
     *                  Needs to be thread safe, meaning avoid side effects when implementing the predicate
     * @param callOnValidationFail If true, the onTextEntered consumer will be called even if the validator returns false
     * @param onTextEntered The consumer to be called when the player submits the text
     * @param returnGui The gui to return to after the text has been entered
     * @param preEnteredText The text that is already entered into the sign when the player opens the text input
     */
    public TextInput(@Nonnull Plugin owner, @Nonnull ProtocolManager manager, @Nullable ItemStack icon,
                     @Nullable SoundEffect clickSound, @Nullable SoundEffect successSound, @Nullable SoundEffect failSound,
                     @Nullable Predicate<String> validator, @Nullable Consumer<TextInputEvent> onTextEntered, boolean callOnValidationFail,
                     @Nullable Gui returnGui, @Nullable Supplier<String> preEnteredText){
        this.owner = owner;
        this.manager = manager;
        this.returnGui = returnGui;

        if(Objects.isNull(icon)){
            //Use the default icon e.g. a sign
            ItemStack defaultIcon = new ItemStack(Material.OAK_SIGN, 1);
            ItemMeta meta = defaultIcon.getItemMeta();
            meta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + "Input"); //Always non-null
            defaultIcon.setItemMeta(meta);
            this.icon = defaultIcon;
        }
        else
            this.icon = icon;

        this.clickSound = clickSound;
        this.successSound = successSound;
        this.failSound = failSound;

        this.validator = Objects.nonNull(validator) ? validator : (s) -> true;
        this.onTextEntered = onTextEntered;
        this.preText = preEnteredText;

        this.callOnValidationFail = callOnValidationFail;
    }

    @Override
    public void render(Inventory inv, int index) {
        inv.setItem(index, icon);
        this.index = index;
    }


    /**
     * Creates a packet listener to listen for the player submitting the entered text
     */
    private void createPacketListener(Player player){
        manager.addPacketListener(new PacketAdapter(owner, PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if(event.getPlayer() != player)
                    return;

                String[] lines = event.getPacket().getStringArrays().read(0);
                BlockPosition position = event.getPacket().getBlockPositionModifier().read(0);
                String text = String.join("\n", lines);
                if(!Objects.nonNull(onTextEntered))
                    return;

                player.sendBlockChange(position.toLocation(player.getWorld()), player.getWorld().getBlockData(position.toLocation(player.getWorld()).getBlock().getLocation())); //Thread safe

                manager.removePacketListener(this);

                boolean validationResult = validator.test(text);
                if(!validationResult){
                    if(Objects.nonNull(failSound))
                        failSound.play(player); //Thread safe

                    if(!callOnValidationFail)
                        return;
                }
                else
                    if(Objects.nonNull(successSound))
                        successSound.play(player); //Thread safe

                //Call in sync context
                Bukkit.getScheduler().scheduleSyncDelayedTask(owner, () -> {
                    if(Objects.nonNull(returnGui))
                        returnGui.show(player);

                    onTextEntered.accept(new TextInputEvent(player, text, validationResult));
                });
            }
        });
    }

    /**
     * Handles the click event for the text input widget
     */
    @Override
    public void handleOnClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if(!(event.getWhoClicked() instanceof Player player))
            return;

        if(event.isShiftClick() || !event.isLeftClick())
            return; //We only count non-shift, left clicks

        if(event.getClick() == ClickType.DOUBLE_CLICK)
            return;

        if(Objects.nonNull(clickSound))
            clickSound.play(player);

        Location signLocation = player.getLocation().clone().add(player.getLocation().getDirection().multiply(-1)).add(0, -2, 0);

        BlockData signData = Material.OAK_SIGN.createBlockData();
        player.sendBlockChange(signLocation, signData);
        String text = Objects.nonNull(preText) ? preText.get() : "\n\n\n";
        String[] lines = text.split("\n");
        if(lines.length < 4){ //Submitted text has less than 4 lines, and need to be padded
            String[] newLines = new String[4];
            System.arraycopy(lines, 0, newLines, 0, lines.length);
            lines = newLines;
        }
        player.sendSignChange(signLocation, lines);

        PacketContainer openSign = manager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        openSign.getBlockPositionModifier().write(0, new BlockPosition(signLocation.getBlockX(), signLocation.getBlockY(), signLocation.getBlockZ()));
        openSign.getBooleans().write(0, true);
        try {
            manager.sendServerPacket(player, openSign);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to text input for player " + player.getName());
        }

        createPacketListener(player);
    }
}
