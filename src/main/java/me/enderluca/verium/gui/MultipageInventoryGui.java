package me.enderluca.verium.gui;

import com.comphenix.protocol.ProtocolManager;
import me.enderluca.verium.gui.builder.ButtonBuilder;
import me.enderluca.verium.gui.widgets.Button;
import me.enderluca.verium.gui.widgets.Icon;
import me.enderluca.verium.gui.widgets.Widget;
import me.enderluca.verium.interfaces.Gui;
import me.enderluca.verium.interfaces.MultipageGui;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A gui that has multiple inventories (= pages) and a navigation bar to switch between them
 */
public class MultipageInventoryGui implements MultipageGui {

    @Nonnull
    protected InventoryGui[] invPages;

    @Nonnull
    protected final Widget[] navigationBarWidgets = new Widget[7];


    @Nonnull
    protected final Plugin owner;
    @Nonnull
    protected final ProtocolManager protocolManager;


    public MultipageInventoryGui(@Nonnull Plugin owner, @Nonnull ProtocolManager manager, @Nonnegative int pages, @Nonnull String title){
        this.owner = owner;
        this.protocolManager = manager;

        invPages = new InventoryGui[pages];
        for(int i = 0; i < pages; i++)
            invPages[i] = new InventoryGui(owner, 54, title + " Page " + (i+1), null);
        renderNavigationBar();
        renderWidgets();
    }

    /**
     * Renders the navigation bar on all pages with the buttons for the previous and next page and the custom navigation bar widgets
     */
    protected void renderNavigationBar(){
        ItemStack blankIcon = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
        ItemMeta meta = blankIcon.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "");
        blankIcon.setItemMeta(meta);

        for(int i = 0; i < invPages.length; i++){
            for(int j = 0; j < 7; j++){
                if(navigationBarWidgets[j] != null)
                    invPages[i].addWidget(navigationBarWidgets[j], 45 + j + 1);
                else
                    invPages[i].addWidget(new Icon(blankIcon),  45 + j + 1);
            }

            if(i != 0)
                invPages[i].addWidget(getPreviousButton(i), 45);
            else
                invPages[i].addWidget(new Icon(blankIcon), 45);



            if(i != (invPages.length - 1))
                invPages[i].addWidget(getNextButton(i), 53);
            else
                invPages[i].addWidget(new Icon(blankIcon), 53);
        }
    }

    /**
     * Returns the button used in the navigation bar to go to the previous page
     */
    protected Button getPreviousButton(int pageIndex){
        ButtonBuilder builder = new ButtonBuilder(owner, protocolManager);

        ItemStack icon = new ItemStack(Material.RED_STAINED_GLASS, 1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Previous"); //Guaranteed to not be null
        icon.setItemMeta(meta);

        builder.addIcon(icon);
        builder.addClickSound(new SoundEffect(Sound.UI_BUTTON_CLICK));
        builder.addClickEvent(event -> {
            if (!(event.getWhoClicked() instanceof Player player))
                return;
            show(player, pageIndex - 1);
        });

        return builder.build();
    }

    /**
     * Returns the button used in the navigation bar to go to the next page
     */
    protected Button getNextButton(int pageIndex){
        ButtonBuilder builder = new ButtonBuilder(owner, protocolManager);

        ItemStack icon = new ItemStack(Material.GREEN_STAINED_GLASS, 1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Next"); //Guaranteed to not be null
        icon.setItemMeta(meta);

        builder.addIcon(icon);
        builder.addClickSound(new SoundEffect(Sound.UI_BUTTON_CLICK));
        builder.addClickEvent(event -> {
            if(!(event.getWhoClicked() instanceof Player player))
                return;
            show(player, pageIndex + 1);
        });

        return builder.build();
    }

    public void renderWidgets(){
        for (InventoryGui invPage : invPages) {
            invPage.renderWidgets();
        }
    }

    public void renderWidget(int page, int index){
        invPages[page].renderWidget(index);
    }

    /**
     * Shows the first page to the specified player
     */
    public void show(Player player){
        invPages[0].show(player);
    }

    /**
     * Shows the specified page to the specified player
     */
    public void show(Player player, int page){
        invPages[page].show(player);
    }

    /**
     * Clears all widgets from all pages except the navigation bar
     */
    public void clearWidgets(){
        for(InventoryGui invPage : invPages){
            invPage.clearWidgets();
        }
        renderNavigationBar(); //Navigation bar got removed because of clearWidgets
    }

    /**
     * Adds a widget to the navigation bar of all pages
     * Index of the widget will be ignored
     *
     * @param widget Widget to add to the navigation, null to clears the slot
     * @param index index inside the navigation bar, must be between 0 and 6
     */
    public void addNavigationBarWidget(@Nullable Widget widget, int index){
        if(index < 0 || index > 6)
            throw new IllegalArgumentException("Index must be between 0 and 6");

        navigationBarWidgets[index] = widget;
        renderNavigationBar();
    }
    /**
     * Adds a widget to the first page
     */
    public void addWidget(Widget widget, int index){
        invPages[0].addWidget(widget, index);
    }

    /**
     * Adds a widget to the specified page
     */
    public void addWidget(Widget widget, int page, int index){
        invPages[page].addWidget(widget, index);
    }
}
