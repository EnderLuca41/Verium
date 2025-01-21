package me.enderluca.verium.gui;

import com.comphenix.protocol.ProtocolManager;
import me.enderluca.verium.ListingType;
import me.enderluca.verium.PotionEffect;
import me.enderluca.verium.gui.builder.ButtonBuilder;
import me.enderluca.verium.gui.builder.SwitchBuilder;
import me.enderluca.verium.gui.builder.TextInputBuilder;
import me.enderluca.verium.gui.widgets.Button;
import me.enderluca.verium.gui.widgets.Icon;
import me.enderluca.verium.gui.widgets.Switch;
import me.enderluca.verium.gui.widgets.TextInput;
import me.enderluca.verium.interfaces.Gui;
import me.enderluca.verium.interfaces.MultipageGui;
import me.enderluca.verium.services.PotionEffectsService;
import me.enderluca.verium.util.GuiUtil;
import me.enderluca.verium.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DecoratedPotInventory;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.UUID;

public class PotionEffectGui implements MultipageGui {

    @Nonnull
    protected final PotionEffectsService service;

    @Nonnull
    protected final Plugin owner;

    @Nonnull
    protected final ProtocolManager protocolManager;

    @Nonnull
    protected final MultipageInventoryGui gui;

    @Nonnull
    protected final PotionEffectsListGui effectsListGui;


    private final SoundEffect inputSuccessSound = new SoundEffect(Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1.25f, 0);
    private final SoundEffect inputErrorSound = new SoundEffect(Sound.BLOCK_NOTE_BLOCK_BASS, 1, 0.5f, 0);
    private final SoundEffect switchTrueSound = new SoundEffect(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1.1f, 0);
    private final SoundEffect switchFalseSound = new SoundEffect(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.7f, 0);

    public PotionEffectGui(@Nonnull Plugin owner, @Nonnull ProtocolManager protocolManager, @Nonnull PotionEffectsService service){
        this.owner = owner;
        this.protocolManager = protocolManager;
        this.service = service;
        this.effectsListGui = new PotionEffectsListGui(owner, protocolManager, this, type -> {
            service.addEffect(type, 0, true);
            createWidgets();
            renderWidgets();
        });

        gui = new MultipageInventoryGui(owner, protocolManager, 10, "Potion Effects");
        gui.addNavigationBarWidget(createAddEffectButton(), 3);
        createWidgets();
        renderWidgets();
    }

    private Icon createEffectIcon(PotionEffect effect){
        return new Icon(GuiUtil.getPotionEffectIcon(effect.getEffectType()));
    }

    private TextInput createChangeAmplifierInputPotionEffect(PotionEffect effect){
        TextInputBuilder builder = new TextInputBuilder(owner, protocolManager);
        return builder.addIcon(GuiUtil.getChangeAmplifierIcon(effect.getAmplifier()))
                .addPreEnteredText(() -> String.valueOf(effect.getAmplifier()))
                .addValidator(input -> {
                    int amplifier = 0;

                    try{
                        amplifier = Integer.parseInt(input.replace(" ", "").replace("\n", ""));
                    } catch (NumberFormatException e){
                        return false;
                    }

                    return amplifier >= 0 && amplifier <= 127;
                })
                .listenOnTextEntered(event -> {
                    if(!event.getValidationResult()){
                        event.getPlayer().sendMessage(ChatColor.RED + "Input was either not a number or not in the range of" + ChatColor.GOLD + "0-127.");
                    }

                    int amplifier = Integer.parseInt(event.getText().replace(" ", "").replace("\n", ""));
                    effect.setAmplifier(amplifier);
                    createWidgets();
                    renderWidgets();
                })
                .callOnValidationFail(true)
                .addSuccessSound(inputSuccessSound)
                .addFailSound(inputErrorSound)
                .addClickSound(new SoundEffect(Sound.BLOCK_BARREL_OPEN))
                .build();
    }

    private Switch createParticleSwitch(PotionEffect effect){
        SwitchBuilder builder = new SwitchBuilder(owner, protocolManager);
        return builder.addTrueIcon(GuiUtil.getParticlesSwitchIconTrue())
                .addFalseIcon(GuiUtil.getParticlesSwitchIconFalse())
                .addTrueSound(switchTrueSound)
                .addFalseSound(switchFalseSound)
                .addGetter(() -> !effect.getHideParticles())
                .addSetter(b -> effect.setHideParticles(!b))
                .build();
    }

    private TextInput createAddPlayerInput(PotionEffect effect){
        TextInputBuilder builder = new TextInputBuilder(owner, protocolManager);
        return builder.addIcon(GuiUtil.getAddPlayerInputIcon())
                .addValidator(input -> {
                    input = input.replace(" ", "").replace("\n", "");
                    if(input.isEmpty())
                        return false;
                    UUID uuid = PlayerUtil.getUUID(input);
                    return uuid != null;
                })
                .listenOnTextEntered(event -> {
                    if(!event.getValidationResult()){
                        event.getPlayer().sendMessage(ChatColor.RED + "Invalid player entered.");
                        return;
                    }

                    String input = event.getText().replace(" ", "").replace("\n", "");
                    UUID uuid = PlayerUtil.getUUID(input);
                    effect.addPlayer(uuid);
                    createWidgets();
                    renderWidgets();
                })
                .callOnValidationFail(true)
                .addReturnGui(this)
                .addSuccessSound(inputSuccessSound)
                .addFailSound(inputErrorSound)
                .addClickSound(new SoundEffect(Sound.BLOCK_BARREL_OPEN))
                .build();
    }

    private TextInput createRemovePlayerInput(PotionEffect effect){
        TextInputBuilder builder = new TextInputBuilder(owner, protocolManager);
        return builder.addIcon(GuiUtil.getRemovePlayerInputIcon())
                .addValidator(input -> {
                    input = input.replace(" ", "").replace("\n", "");
                    if(input.isEmpty())
                        return false;
                    UUID uuid = PlayerUtil.getUUID(input);
                    if(uuid == null)
                        return false;
                    return effect.containsPlayer(uuid);
                })
                .listenOnTextEntered(event -> {
                    if(!event.getValidationResult()){
                        event.getPlayer().sendMessage(ChatColor.RED + "Invalid player entered.");
                        return;
                    }

                    String input = event.getText().replace(" ", "").replace("\n", "");
                    UUID uuid = PlayerUtil.getUUID(input);
                    effect.removePlayer(uuid);
                    createWidgets();
                    renderWidgets();
                })
                .callOnValidationFail(true)
                .addReturnGui(this)
                .addSuccessSound(inputSuccessSound)
                .addFailSound(inputErrorSound)
                .addClickSound(new SoundEffect(Sound.BLOCK_BARREL_OPEN))
                .build();
    }

    private Switch createBlacklistWhitelistSwitch(PotionEffect effect){
        StringBuilder string = new StringBuilder();
        for(UUID uuid : effect.getPlayers()){
            String name = PlayerUtil.getPlayerName(uuid);
            if(name == null)
                continue;
            string.append(name).append(", ");
        }


        SwitchBuilder builder = new SwitchBuilder(owner, protocolManager);
        return builder.addTrueIcon(GuiUtil.getWhitelistIcon(string.toString()))
                .addFalseIcon(GuiUtil.getBlacklistIcon(string.toString()))
                .addTrueSound(new SoundEffect(Sound.ITEM_BOOK_PAGE_TURN, 1, 1.25f, 0))
                .addFalseSound(new SoundEffect(Sound.ITEM_BOOK_PAGE_TURN, 1, 0.7f, 0))
                .addGetter(() -> effect.getListType() == ListingType.Whitelist)
                .addSetter(b -> effect.setListType(ListingType.values()[b ? 1 : 0]))
                .build();
    }

    private Switch createEnableSwitch(PotionEffect effect){
        SwitchBuilder builder = new SwitchBuilder(owner, protocolManager);
        return builder.addTrueIcon(GuiUtil.getEnabledIcon())
                .addFalseIcon(GuiUtil.getDisabledIcon())
                .addTrueSound(switchTrueSound)
                .addFalseSound(switchFalseSound)
                .addGetter(effect::isEnabled)
                .addSetter(effect::setEnabled)
                .build();
    }

    private Button createRemoveButton(PotionEffect effect){
        ButtonBuilder builder = new ButtonBuilder(owner, protocolManager);
        return builder.addIcon(GuiUtil.getRemovePotionEffectIcon())
                .addClickSound(new SoundEffect(Sound.ITEM_BUNDLE_DROP_CONTENTS))
                .addClickEvent(event -> {
                    service.removeEffect(effect);
                    createWidgets();
                    renderWidgets();
                })
                .build();

    }

    private Button createAddEffectButton(){
        ButtonBuilder builder = new ButtonBuilder(owner, protocolManager);
        return builder.addIcon(GuiUtil.getAddPotionEffectIcon())
                .addClickEvent(event -> effectsListGui.show((Player) event.getWhoClicked()))
                .build();
    }

    public void createWidgets(){
        gui.clearWidgets();

        int i = 0;
        for(PotionEffect effect : service.getEffects()){
            Icon effectIcon = createEffectIcon(effect);
            gui.addWidget(effectIcon, i / 5, (i % 5) * 9);

            TextInput changeAmplifierInput = createChangeAmplifierInputPotionEffect(effect);
            gui.addWidget(changeAmplifierInput, i / 5, (i % 5) * 9 + 1);

            Switch particleSwitch = createParticleSwitch(effect);
            gui.addWidget(particleSwitch, i / 5, (i % 5) * 9 + 2);

            TextInput addPlayerInput = createAddPlayerInput(effect);
            gui.addWidget(addPlayerInput, i / 5, (i % 5) * 9 + 3);

            TextInput removePlayerInput = createRemovePlayerInput(effect);
            gui.addWidget(removePlayerInput, i / 5, (i % 5) * 9 + 4);

            Switch blacklistWhitelistSwitch = createBlacklistWhitelistSwitch(effect);
            gui.addWidget(blacklistWhitelistSwitch, i / 5, (i % 5) * 9 + 5);

            Switch enableSwitch = createEnableSwitch(effect);
            gui.addWidget(enableSwitch, i / 5, (i % 5) * 9 + 7);

            Button removeButton = createRemoveButton(effect);
            gui.addWidget(removeButton, i / 5, (i % 5) * 9 + 8);

            i++;
        }
    }


    @Override
    public void show(Player player){
        createWidgets();
        gui.show(player);
    }

    @Override
    public void show(Player player, int page){
        createWidgets();
        gui.show(player, page);
    }

    @Override
    public void renderWidgets(){
        gui.renderWidgets();
    }
}
