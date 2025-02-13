package me.enderluca.verium.gui;

import com.comphenix.protocol.ProtocolManager;

import me.enderluca.verium.AttributeChange;
import me.enderluca.verium.ListingType;
import me.enderluca.verium.gui.builder.ButtonBuilder;
import me.enderluca.verium.gui.builder.SwitchBuilder;
import me.enderluca.verium.gui.builder.TextInputBuilder;
import me.enderluca.verium.gui.widgets.Button;
import me.enderluca.verium.gui.widgets.Icon;
import me.enderluca.verium.gui.widgets.Switch;
import me.enderluca.verium.gui.widgets.TextInput;
import me.enderluca.verium.interfaces.Gui;
import me.enderluca.verium.services.AttributeService;
import me.enderluca.verium.util.AttributeUtil;
import me.enderluca.verium.util.GuiUtil;
import me.enderluca.verium.util.PlayerUtil;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AttributeManagerGui implements Gui {

    @Nonnull
    protected final AttributeService attributeService;
    @Nonnull

    protected final MultipageInventoryGui gui;

    @Nonnull
    protected Plugin owner;
    @Nonnull
    protected ProtocolManager protocolManager;

    //Cache for the player uuids to avoid querying the mojang api multiple times
    @Nonnull
    public final Map<String, UUID> playerUuidCache;

    //Gui to select the attributes from to create a new attribute change
    @Nonnull
    private final AttributeListGui attributeListGui;


    private final SoundEffect inputSuccessSound = new SoundEffect(Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1.25f, 0);
    private final SoundEffect inputErrorSound = new SoundEffect(Sound.BLOCK_NOTE_BLOCK_BASS, 1, 0.5f, 0);

    public AttributeManagerGui(@Nonnull Plugin owner, @Nonnull ProtocolManager manager, @Nonnull AttributeService service){
        this.owner = owner;
        attributeService = service;
        protocolManager = manager;

        playerUuidCache = new ConcurrentHashMap<>();

        gui = new MultipageInventoryGui(owner, manager, 10, "Attribute Manager");
        renderWidgets();

        attributeListGui = new AttributeListGui(owner, manager, this::onAttributeSelected, gui);
        gui.addNavigationBarWidget(createAddAttributeButton(), 3);
    }

    private void onAttributeSelected(Attribute attribute){
        attributeService.createAttributeChange(attribute, AttributeUtil.getDefaultBaseValue(attribute));
        renderWidgets();
    }

    /**
     * Creates a button for the navigation bar that is used to create a new attribute change
     */
    private Button createAddAttributeButton(){
        ButtonBuilder builder = new ButtonBuilder(owner, protocolManager);
        return builder.addIcon(GuiUtil.getAddAttributeIcon())
                      .addClickEvent(event -> attributeListGui.show((Player) event.getWhoClicked()))
                      .build();
    }


    /**
     * Creates a switch used in the gui to switch between whitelist and blacklist
     */
    private Switch createWhitelistBlacklistSwitch(AttributeChange change, String playerList){
        SwitchBuilder switchBuilder = new SwitchBuilder(owner, protocolManager);
        switchBuilder.addTrueIcon(GuiUtil.getWhitelistIcon(playerList));
        switchBuilder.addFalseIcon(GuiUtil.getBlacklistIcon(playerList));
        switchBuilder.addGetter(() -> change.getListType() == ListingType.Whitelist);
        switchBuilder.addSetter(b -> change.setListType(ListingType.values()[b ? 1 : 0]));
        switchBuilder.addTrueSound(new SoundEffect(Sound.ITEM_BOOK_PAGE_TURN, 1, 1.25f, 0));
        switchBuilder.addFalseSound(new SoundEffect(Sound.ITEM_BOOK_PAGE_TURN, 1, 0.7f, 0));
        return switchBuilder.build();
    }

    /**
     * Creates a text input used in the gui to add players to the whitelist/blacklist
     */
    private TextInput createAddPlayer(AttributeChange change){
        TextInputBuilder builder = new TextInputBuilder(owner, protocolManager);
        builder.addIcon(GuiUtil.getAddPlayerInputIcon())
        .listenOnTextEntered(args -> {
            if(!args.getValidationResult()){
                args.getPlayer().sendMessage(ChatColor.RED + "Invalid player entered.");
                return;
            }

            UUID uuid = PlayerUtil.getUUID(args.getText());
            change.addPlayer(uuid);
            renderWidgets();
        })
        .addValidator(s -> {
            UUID uuid = PlayerUtil.getUUID(s);
            return Objects.nonNull(uuid) && !change.containsPlayer(uuid);
        })
        .callOnValidationFail(true)
        .addSuccessSound(inputSuccessSound)
        .addFailSound(inputErrorSound)
        .addClickSound(new SoundEffect(Sound.BLOCK_BARREL_OPEN))
        .addReturnGui(this);
        return builder.build();
    }

    /**
     * Creates a text input used in the gui to remove players from the whitelist/blacklist
     */
    private TextInput createRemovePlayer(AttributeChange change){
        TextInputBuilder builder = new TextInputBuilder(owner, protocolManager);
        builder.addIcon(GuiUtil.getRemovePlayerInputIcon())
        .listenOnTextEntered(args -> {
            if(!args.getValidationResult()){
                args.getPlayer().sendMessage(ChatColor.RED + "Invalid player entered.");
                return;
            }

            UUID uuid = PlayerUtil.getUUID(args.getText());
            change.removePlayer(uuid);
            renderWidgets();
        })
        .addValidator(s -> {
            UUID uuid = PlayerUtil.getUUID(s);
            return Objects.nonNull(uuid) && change.containsPlayer(uuid);
        })
        .callOnValidationFail(true)
        .addClickSound(new SoundEffect(Sound.BLOCK_BARREL_OPEN))
        .addSuccessSound(inputSuccessSound)
        .addFailSound(inputErrorSound)
        .addReturnGui(this);
        return builder.build();
    }

    /**
     * Creates a switch used in the gui to enable/disable an attribute change
     */
    private Switch createEnableDisableSwitch(AttributeChange change){
        SwitchBuilder switchBuilder = new SwitchBuilder(owner, protocolManager);
        switchBuilder.addTrueIcon(GuiUtil.getEnabledIcon());
        switchBuilder.addFalseIcon(GuiUtil.getDisabledIcon());
        switchBuilder.addGetter(change::isEnabled);
        switchBuilder.addSetter(change::setEnabled);
        switchBuilder.addTrueSound(new SoundEffect(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1.1f, 0));
        switchBuilder.addFalseSound(new SoundEffect(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.7f, 0));
        return switchBuilder.build();
    }

    /**
     * Creates a button used in the gui to remove an attribute change
     */
    private Button createRemoveButton(AttributeChange change){
        return new Button(GuiUtil.getRemoveAttributeIcon(), new SoundEffect(Sound.ITEM_BUNDLE_DROP_CONTENTS), event -> {
            attributeService.removeAttributeChange(change);
            renderWidgets();
        }, false);
    }

    /**
     * Creates an input used to change the attribute value
     */
    private TextInput createChangeValue(AttributeChange change){
        TextInputBuilder builder = new TextInputBuilder(owner, protocolManager);
        return builder.addIcon(GuiUtil.getChangeAttributeValueIcon(change.getAttribute(), change.getValue()))
        .listenOnTextEntered(args -> {
            if(!args.getValidationResult()){
                args.getPlayer().sendMessage(ChatColor.RED + "Invalid number entered.");
                return;
            }

            change.setValue(Double.parseDouble(args.getText().replace(",", ".")));
            renderWidgets();
        })
        .addValidator(s -> {
            try {
                String[] lines = s.split("\n");
                lines[0] = lines[0].replace(",", "."); //Replace commas with dots to allow for decimal numbers
                Double.parseDouble(lines[0]);
                return true;
            } catch (NumberFormatException e){
                return false;
            }
        })
        .callOnValidationFail(true)
        .addPreEnteredText(() -> Double.toString(change.getValue()))
        .addClickSound(new SoundEffect(Sound.BLOCK_BARREL_OPEN))
        .addSuccessSound(inputSuccessSound)
        .addFailSound(inputErrorSound)
        .addReturnGui(this)
        .build();
    }

    /**
     * Creates the widgets for a single row in the multipage gui
     * @param gui The MulitpageInventoryGui
     * @param i The cross page index of the row
     */
    private void createWidgetsRow(MultipageInventoryGui gui, int i, AttributeChange change){
        StringBuilder playerList = new StringBuilder();
        for(UUID uuid : change.getPlayers()){
            String playerName = PlayerUtil.getPlayerName(uuid);
            if(Objects.isNull(playerName))
                return;
            playerList.append(playerName).append(", ");
        }

        Icon attributeIcon = new Icon(GuiUtil.getAttributeIcon(change.getAttribute()));
        gui.addWidget(attributeIcon, (i / 5),  (i % 5) * 9);

        TextInput changeValue = createChangeValue(change);
        gui.addWidget(changeValue, (i / 5), (i % 5) * 9 + 1);

        Switch whitelistBlacklistSwitch = createWhitelistBlacklistSwitch(change, playerList.toString());
        gui.addWidget(whitelistBlacklistSwitch, (i / 5), (i % 5) * 9 + 5);

        TextInput addBuilder = createAddPlayer(change);
        gui.addWidget(addBuilder, (i / 5), (i % 5) * 9 + 3);

        TextInput removePlayerButton = createRemovePlayer(change);
        gui.addWidget(removePlayerButton, (i / 5), (i % 5) * 9 + 4);

        Switch enableDisableSwitch = createEnableDisableSwitch(change);
        gui.addWidget(enableDisableSwitch, (i / 5), (i % 5) * 9 + 7);

        Button removeButton = createRemoveButton(change);
        gui.addWidget(removeButton, (i / 5), (i % 5) * 9 + 8);

    }

    /**
     * Creates all the widgets in the multipage gui to manage the attributes
     */
    public void createWidgets(){
        int i = 0;
        for(AttributeChange change : attributeService.getAttributeChanges()){
            createWidgetsRow(gui, i, change);
            i++;
        }
    }

    public void renderWidgets(){
        gui.clearWidgets();
        createWidgets();
        gui.renderWidgets();
    }

    public void show(Player player){
        renderWidgets();
        gui.show(player);
    }

    public void clearWidgets(){
        throw new UnsupportedOperationException("Attribute manager gui is read-only");
    }
}
