package me.enderluca.verium.gui;

import com.comphenix.protocol.ProtocolManager;

import me.enderluca.verium.gui.builder.ButtonBuilder;
import me.enderluca.verium.gui.builder.SwitchBuilder;
import me.enderluca.verium.gui.builder.TextInputBuilder;
import me.enderluca.verium.gui.widgets.*;
import me.enderluca.verium.interfaces.Gui;
import me.enderluca.verium.services.TimeService;
import me.enderluca.verium.util.GuiUtil;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import net.md_5.bungee.api.ChatColor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Chest gui used to manage the current time and freeze it
 */
public class TimeGui implements Gui {

    @Nonnull
    protected final InventoryGui gui;

    @Nonnull
    protected final TimeService service;

    @Nonnull
    protected final Plugin owner;

    @Nonnull
    protected final ProtocolManager protocolManager;

    @Nullable
    protected BukkitTask clockTask;

    public TimeGui(@Nonnull Plugin owner, @Nonnull ProtocolManager manager, @Nonnull TimeService service) {
        gui = new InventoryGui(owner, 18, "Time manager", null);
        this.owner = owner;
        this.protocolManager = manager;
        this.service = service;

        createWidgets();
        renderWidgets();
    }

    public void createWidgets(){
        if(clockTask != null)
            clockTask.cancel();

        clockTask = Bukkit.getScheduler().runTaskTimer(owner, () -> {
            gui.addWidget(new Icon(GuiUtil.getTimeIcon(service.getTimeString())), 0);
            gui.addWidget(new Icon(GuiUtil.getTimeTicksIcon(service.getTime())), 1);
            gui.addWidget(new Icon(GuiUtil.getTimeRealIcon(service.getRealTimeString())), 2);
        }, 0, 5);

        gui.addWidget(createChangeTicksInput(), 5);

        gui.addWidget(createChangeTimeInput(), 6);

        gui.addWidget(createChangeTimeRealInput(), 7);

        gui.addWidget(createFreezeSwitch(), 8);

        //Buttons that set the time to different presents which are also supported by the /time set command
        gui.addWidget(createDayButton(), 14);
        gui.addWidget(createNoonButton(), 15);
        gui.addWidget(createNightButton(), 16);
        gui.addWidget(createMidnightButton(), 17);
    }

    /**
     * Creates a switch widget used to freeze the time
     */
    private Widget createFreezeSwitch(){
        SwitchBuilder freezeSwitchBuilder = new SwitchBuilder(owner, protocolManager);
        return freezeSwitchBuilder
                .addTrueIcon(GuiUtil.getFreezeTrueIcon())
                .addFalseIcon(GuiUtil.getFreezeFalseIcon())
                .addGetter(service::isFrozen)
                .addSetter(service::setFrozen)
                .addTrueSound(new SoundEffect(Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1f, 1.1f))
                .addFalseSound(new SoundEffect(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0.7f))
                .build();
    }

    /**
     * Creates a text input widget used to change the time in ticks
     */
    private Widget createChangeTicksInput(){
        TextInputBuilder changeTicksInputBuilder = new TextInputBuilder(owner, protocolManager);
        return changeTicksInputBuilder
                .addIcon(GuiUtil.getChangeTicksIcon())
                .addReturnGui(gui)
                .addPreEnteredText(() -> service.getTime() + "\n " + "\n " + "\n ")
                .listenOnTextEntered(event -> {
                    if(!event.getValidationResult()){
                        event.getPlayer().sendMessage(ChatColor.RED + "Invalid number entered.");
                        return;
                    }

                    long ticks = Long.parseLong(event.getText().replace(" ", "").replace("\n", ""));
                    service.setTime(ticks);
                })
                .addValidator((s) -> {
                    try {
                        Long.parseLong(s.replace(" ", "").replace("\n", ""));
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    return true;
                })
                .callOnValidationFail(true)
                .addSuccessSound(new SoundEffect(Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1.25f, 0))
                .addFailSound(new SoundEffect(Sound.BLOCK_NOTE_BLOCK_BASS, 1, 0.5f, 0))
                .build();
    }

    /**
     * Creates a text input widget used to change the time in HH:MM:SS format
     */
    private  Widget createChangeTimeInput(){
        TextInputBuilder changeTimeInputBuilder = new TextInputBuilder(owner, protocolManager);
        return changeTimeInputBuilder
                .addIcon(GuiUtil.getChangeTimeIcon())
                .addReturnGui(gui)
                .addPreEnteredText(() -> service.getTimeString() + "\n " + "\n " + "\n ")
                .listenOnTextEntered(event -> {
                    if(!event.getValidationResult()){
                        event.getPlayer().sendMessage(ChatColor.RED + "Invalid time format, please use HH:MM:SS.");
                        return;
                    }

                    String text = event.getText().replace(" ", "").replace("\n", "");
                    String[] split = text.split(":");

                    int hours = Integer.parseInt(split[0]);
                    int minutes = Integer.parseInt(split[1]);
                    int seconds = Integer.parseInt(split[2]);

                    service.setTime(hours, minutes, seconds);
                })
                .addValidator((s) -> {
                    String text = s.replace(" ", "").replace("\n", "");
                    String[] split = text.split(":");
                    if(split.length != 3)
                        return false;

                    int hours, minutes, seconds;
                    try {
                        hours = Integer.parseInt(split[0]);
                        minutes = Integer.parseInt(split[1]);
                        seconds = Integer.parseInt(split[2]);
                    } catch (NumberFormatException e) {
                        return false;
                    }

                    if(hours < 0 || hours > 23 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59)
                        return false;

                    return true;
                })
                .callOnValidationFail(true)
                .addSuccessSound(new SoundEffect(Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1.25f, 0))
                .addFailSound(new SoundEffect(Sound.BLOCK_NOTE_BLOCK_BASS, 1, 0.5f, 0))
                .build();
    }

    /**
     * Creates a text input widget used to change the time in real minutes and seconds in MM:SS format
     */
    private Widget createChangeTimeRealInput(){
        TextInputBuilder changeTimeInputBuilder = new TextInputBuilder(owner, protocolManager);
        return changeTimeInputBuilder
                .addIcon(GuiUtil.getChangeTimeRealIcon())
                .addReturnGui(gui)
                .addPreEnteredText(() -> service.getRealTimeString() + "\n " + "\n " + "\n ")
                .listenOnTextEntered(event -> {
                    String text = event.getText().replace(" ", "").replace("\n", "");
                    String[] split = text.split(":");
                    int minutes = Integer.parseInt(split[0]);
                    int seconds = Integer.parseInt(split[1]);

                    long ticks = (minutes * 1200L) + (seconds * 20L);
                    service.setTime(ticks);
                })
                .addValidator((s) -> {
                    String text = s.replace(" ", "").replace("\n", "");
                    String[] split = text.split(":");
                    if(split.length != 2)
                        return false;

                    try {
                        Integer.parseInt(split[0]);
                        Integer.parseInt(split[1]);
                    } catch (NumberFormatException e) {
                        return false;
                    }

                    return true;
                })
                .callOnValidationFail(true)
                .build();
    }

    private Widget createDayButton(){
        ButtonBuilder dayButtonBuilder = new ButtonBuilder(owner, protocolManager);
        return dayButtonBuilder
                .addIcon(GuiUtil.getDayIcon())
                .addClickEvent((event) -> service.setTime(0))
                .build();
    }

    private Widget createNoonButton(){
        ButtonBuilder noonButtonBuilder = new ButtonBuilder(owner, protocolManager);
        return noonButtonBuilder
                .addIcon(GuiUtil.getNoonIcon())
                .addClickEvent((event) -> service.setTime(6000))
                .build();
    }

    private Widget createNightButton(){
        ButtonBuilder nightButtonBuilder = new ButtonBuilder(owner, protocolManager);
        return nightButtonBuilder
                .addIcon(GuiUtil.getNightIcon())
                .addClickEvent((event) -> service.setTime(13000))
                .build();
    }

    private Widget createMidnightButton(){
        ButtonBuilder midnightButtonBuilder = new ButtonBuilder(owner, protocolManager);
        return midnightButtonBuilder
                .addIcon(GuiUtil.getMidnightIcon())
                .addClickEvent((event) -> service.setTime(18000))
                .build();
    }

    @Override
    public void show(Player player) {
        gui.show(player);
    }

    @Override
    public void renderWidgets() {
        gui.renderWidgets();
    }
}
