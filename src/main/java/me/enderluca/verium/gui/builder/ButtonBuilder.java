package me.enderluca.verium.gui.builder;

import me.enderluca.verium.gui.widgets.Button;

import com.comphenix.protocol.ProtocolManager;

import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ButtonBuilder extends WidgetBuilder {
    @Nullable
    protected Sound clickSound = null;
    @Nullable
    protected Consumer<InventoryClickEvent> onClick = null;
    protected boolean doubleClick = false;

    public ButtonBuilder(@Nonnull Plugin owner, @Nonnull ProtocolManager manager){
        super(owner, manager);
    }

    public ButtonBuilder addClickSound(Sound sound){
        clickSound = sound;
        return this;
    }

    public ButtonBuilder addClickEvent(Consumer<InventoryClickEvent> onClick){
        this.onClick = onClick;
        return this;
    }

    public ButtonBuilder doubleClick(){
        doubleClick = true;
        return this;
    }

    @Override
    public ButtonBuilder addIcon(@Nullable ItemStack icon){
        this.icon = icon;
        return this;
    }

    @Override
    public Button build(){
        return new Button(icon, clickSound, onClick, doubleClick);
    }
}
