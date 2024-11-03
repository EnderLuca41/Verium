package me.enderluca.verium.gui.widgets;

import me.enderluca.verium.gui.event.TextInputEvent;
import me.enderluca.verium.interfaces.IInventoryGui;
import me.enderluca.verium.interfaces.IOnClick;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;

import org.bukkit.*;
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
import java.util.logging.Level;

/**
 * A text input widget that allows the player to input text into a sign
 */
public class TextInput extends Widget implements IOnClick, Listener {

    @Nonnull
    protected Sound clickSound;
    @Nonnull
    protected Sound doneSound;

    @Nullable
    protected final Consumer<TextInputEvent> onTextEntered;

    @Nonnull
    private final ProtocolManager manager;

    @Nullable
    private final IInventoryGui returnGui;

    /**
     * @param clickSound The sound to play when the player clicks on the text input, if not set a default sound will be used
     * @param doneSound The sound to play when the player submits the text, if not set a default sound will be used
     * @param onTextEntered The consumer to be called when the player submits the text
     * @param returnGui The gui to return to after the text has been entered
     */
    public TextInput(@Nonnull Plugin owner, @Nonnull ProtocolManager manager, @Nullable ItemStack icon,
                     @Nullable Sound clickSound, @Nullable Sound doneSound, @Nullable Consumer<TextInputEvent> onTextEntered,
                     @Nullable IInventoryGui returnGui){
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

        if(Objects.isNull(clickSound))
            this.clickSound = Sound.BLOCK_BARREL_OPEN; //Default sound
        else
            this.clickSound = clickSound;

        if(Objects.isNull(doneSound))
            this.doneSound = Sound.BLOCK_BARREL_CLOSE; //Default sound
        else
            this.doneSound = doneSound;

        this.onTextEntered = onTextEntered;
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
                String text = String.join("\n", lines);
                if(!Objects.nonNull(onTextEntered))
                    return;

                player.playSound(player.getLocation(), doneSound, 1, 1);

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

        player.playSound(player.getLocation(), clickSound, 1, 1);

        PacketContainer openSign = manager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        BlockPosition pos = new BlockPosition(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        openSign.getBlockPositionModifier().write(0, pos);
        openSign.getBooleans().write(0, true);
        try {
            manager.sendServerPacket(player, openSign);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to text input for player " + player.getName());
        }

        createPacketListener(player);
    }
}
