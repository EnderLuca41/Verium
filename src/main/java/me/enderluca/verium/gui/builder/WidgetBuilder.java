package me.enderluca.verium.gui.builder;

import com.comphenix.protocol.ProtocolManager;
import me.enderluca.verium.gui.widgets.Widget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class WidgetBuilder {
    @Nonnull
    protected Plugin owner;
    @Nonnull
    protected ProtocolManager manager;

    @Nullable
    protected ItemStack icon;

    public WidgetBuilder(@Nonnull Plugin owner, @Nonnull ProtocolManager manager){
        this.owner = owner;
        this.manager = manager;
    }

    public abstract WidgetBuilder addIcon(@Nullable ItemStack icon);

    public abstract Widget build();
}
