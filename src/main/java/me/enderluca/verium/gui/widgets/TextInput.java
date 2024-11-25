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
import java.util.function.Supplier;
import java.util.logging.Level;

/**
 * A text input widget that allows the player to input text into a sign
 */
public class TextInput extends Widget implements OnClick, Listener {

    @Nullable
    protected SoundEffect clickSound;
    @Nullable
    protected SoundEffect doneSound;

    @Nullable
    protected final Consumer<TextInputEvent> onTextEntered;
    @Nullable
    protected final Supplier<String> preText;

    @Nonnull
    private final ProtocolManager manager;

    @Nullable
    private final Gui returnGui;

    /**
     * @param clickSound The sound to play when the player clicks on the text input, if not set no sound will be played
     * @param doneSound The sound to play when the player submits the text, if not set no sound will be played
     * @param onTextEntered The consumer to be called when the player submits the text
     * @param returnGui The gui to return to after the text has been entered
     * @param preEnteredText The text that is already entered into the sign when the player opens the text input
     */
    public TextInput(@Nonnull Plugin owner, @Nonnull ProtocolManager manager, @Nullable ItemStack icon,
                     @Nullable SoundEffect clickSound, @Nullable SoundEffect doneSound, @Nullable Consumer<TextInputEvent> onTextEntered,
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
        this.doneSound = doneSound;

        this.onTextEntered = onTextEntered;
        this.preText = preEnteredText;
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

                player.sendBlockChange(position.toLocation(player.getWorld()), player.getWorld().getBlockData(position.toLocation(player.getWorld()).getBlock().getLocation()));

                if(Objects.nonNull(doneSound))
                    doneSound.play(player);

                manager.removePacketListener(this);

                Bukkit.getScheduler().runTask(owner, () -> {
                    if(Objects.nonNull(returnGui))
                        returnGui.show(player);
                });

                onTextEntered.accept(new TextInputEvent(player, text));
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
        String text = Objects.nonNull(preText) ? preText.get() : "";
        String[] lines = text.split("\n");
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
