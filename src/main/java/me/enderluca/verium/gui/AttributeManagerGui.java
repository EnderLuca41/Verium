package me.enderluca.verium.gui;

import com.comphenix.protocol.ProtocolManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.enderluca.verium.AttributeChange;
import me.enderluca.verium.ListingType;
import me.enderluca.verium.gui.builder.ButtonBuilder;
import me.enderluca.verium.gui.builder.SwitchBuilder;
import me.enderluca.verium.gui.builder.TextInputBuilder;
import me.enderluca.verium.gui.widgets.Button;
import me.enderluca.verium.gui.widgets.Icon;
import me.enderluca.verium.gui.widgets.Switch;
import me.enderluca.verium.gui.widgets.TextInput;
import me.enderluca.verium.interfaces.IInventoryGui;
import me.enderluca.verium.services.AttributeService;
import me.enderluca.verium.util.AttributeUtil;
import me.enderluca.verium.util.GuiUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AttributeManagerGui implements IInventoryGui {

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

    public AttributeManagerGui(@Nonnull Plugin owner, @Nonnull ProtocolManager manager, @Nonnull AttributeService service){
        this.owner = owner;
        attributeService = service;
        protocolManager = manager;

        playerUuidCache = new HashMap<>();

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
        return switchBuilder.build();
    }

    /**
     * Creates a text input used in the gui to add players to the whitelist/blacklist
     */
    private TextInput createAddPlayer(AttributeChange change){
        TextInputBuilder builder = new TextInputBuilder(owner, protocolManager);
        builder.addIcon(GuiUtil.getAddPlayerInputIcon())
        .listenOnTextEntered(args -> {
            UUID uuid = getUUID(args.getText());
            if(Objects.isNull(uuid)){
                args.getPlayer().sendMessage(ChatColor.RED + "Player entered does not exist.");
            }
            change.addPlayer(uuid);
        }).addReturnGui(this);
        return builder.build();
    }

    /**
     * Creates a text input used in the gui to remove players from the whitelist/blacklist
     */
    private TextInput createRemovePlayer(AttributeChange change){
        TextInputBuilder builder = new TextInputBuilder(owner, protocolManager);
        builder.addIcon(GuiUtil.getRemovePlayerInputIcon());
        builder.listenOnTextEntered(args -> {
            UUID uuid = getUUID(args.getText());
            if(!change.containsPlayer(uuid)){
                args.getPlayer().sendMessage(ChatColor.RED + "Player entered is not in the list.");
            }
            change.removePlayer(uuid);
        });
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
        return switchBuilder.build();
    }

    /**
     * Creates a button used in the gui to remove an attribute change
     */
    private Button createRemoveButton(AttributeChange change){
        return new Button(GuiUtil.getRemoveAttributeIcon(), null, event -> {
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
                          try {
                              String[] lines = args.getText().split("\n");
                              lines[0] = lines[0].replace(",", "."); //Replace commas with dots to allow for decimal numbers
                              double value = Double.parseDouble(lines[0]);
                              change.setValue(value);

                          } catch (NumberFormatException e){
                              args.getPlayer().sendMessage(ChatColor.RED + "Invalid number entered.");
                          }
                      })
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
            String playerName = getPlayerName(uuid);
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


    /**
     * Get the UUID of a player by their name, returns null if the player does not exist or some other problem with the mojang api occurs
     */
    @Nullable
    private UUID getUUID(String name){
        name = name.replace("\n", "");

        //Check if the player is already cached
        if(playerUuidCache.containsKey(name))
            return playerUuidCache.get(name);

        try {
            //Query the mojang api for the player's uuid
            URL url = new URI("https://api.mojang.com/users/profiles/minecraft/" + name).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            //Read the response body and concatenate it into a single string
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }

            //Parse the json, if the id field does not exist, the player does not exist
            JsonObject object = JsonParser.parseString(output.toString()).getAsJsonObject();
            JsonElement uuidElement = object.get("id");
            if(Objects.isNull(uuidElement))
                return null;

            //Insert missing dashes so the java library can parse the uuid
            StringBuilder uuidString = new StringBuilder(uuidElement.getAsString());
            uuidString.insert(20, "-").insert(16, "-").insert(12, "-").insert(8, "-");

            UUID uuid = UUID.fromString(uuidString.toString());
            playerUuidCache.put(name, uuid); //Cache the player uuid
            return UUID.fromString(uuidString.toString());

        } catch (Exception e){
            return null;
        }
    }

    /**
     * Get the name of a player by their uuid, returns null if the player does not exist or some other problem with the mojang api occurs
     */
    @Nullable
    private String getPlayerName(UUID uuid){
        //Try to get the player from the servers offline player list
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if(Objects.nonNull(player.getName()))
            return player.getName();

        //Check if the player is already cached
        for(Map.Entry<String, UUID> entry : playerUuidCache.entrySet()){
            if(entry.getValue().equals(uuid))
                return entry.getKey();
        }

        //Query the mojang api for the player's name
        try{
            String uuidString = uuid.toString().replace("-", "");
            URL url = new URI("https://api.mojang.com/user/profile/" + uuidString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            //Read the response body and concatenate it into a single string
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }

            //Parse the json, if the name field does not exist, the player does not exist
            JsonObject object = JsonParser.parseString(output.toString()).getAsJsonObject();
            JsonElement nameElement = object.get("name");
            if(Objects.isNull(nameElement))
                return null;

            //Return the player's name
            playerUuidCache.put(nameElement.getAsString(), uuid);

            return nameElement.getAsString();
        }
        catch (Exception e) {
            return null;
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
}
