package me.enderluca.verium;

import me.enderluca.verium.commands.*;
import me.enderluca.verium.services.*;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.ProtocolLibrary;

public class VeriumPlugin extends JavaPlugin {

    Logger logger;
    ServerProperties serverProps;
    TimerService timer;
    WorldResetService reset;
    ModificationsService modifications;

    ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        logger = getLogger();
        protocolManager = ProtocolLibrary.getProtocolManager();

        logger.info("Reading server properties");
        serverProps = new ServerProperties();
        serverProps.tryLoad();
        logger.info("Done reading server properties");


        logger.log(Level.INFO, "Creating reset service");
        boolean resetScheduled = getConfig().getBoolean("reset.scheduled", false);
        reset = new WorldResetService(logger, serverProps.getLevelName(), resetScheduled);
        logger.log(Level.INFO, "Creating reset service complete");

        logger.log(Level.INFO, "Creating timer");
        long sec = getConfig().getLong("timer.seconds", 0);
        boolean timerEnabled = getConfig().getBoolean("timer.enabled", true);
        timer = new TimerService(this, sec, timerEnabled);
        logger.log(Level.INFO, "Creating timer complete");

        logger.info("Creating Modifications service to handle challenges, gamerules, goals and attributes");
        modifications = new ModificationsService(this, getConfig(), timer);
        logger.info("Creating Modifications service complete");


        logger.log(Level.INFO, "Creating commands");
        getCommand("timer").setExecutor(new TimerCommand(timer));
        getCommand("reset").setExecutor(new ResetCommand(reset));
        getCommand("challenges").setExecutor(new ChallengeCommand(this, protocolManager, modifications.getChallengeService()));
        getCommand("gamerules").setExecutor(new GameRulesCommand(this, protocolManager, modifications.getGamerulesService()));
        getCommand("pause").setExecutor(new PauseCommand(modifications));
        getCommand("resume").setExecutor(new ResumeCommand(modifications));
        getCommand("goals").setExecutor(new GoalsCommand(this, protocolManager,modifications.getGoalsService()));
        getCommand("attributemanager").setExecutor(new AttributeManagerCommand(this, protocolManager, modifications.getAttributeService()));
        getCommand("time").setExecutor(new TimeCommand(this, protocolManager, modifications.getTimeService()));
        logger.log(Level.INFO, "Creating commands complete");
    }

    @Override
    public void onDisable() {
        logger.info("Pausing challenges, gamerules and goals");
        modifications.pause();

        logger.log(Level.INFO, "Saving config");

        getConfig().set("reset.scheduled", reset.isResetScheduled());

        modifications.saveConfig(getConfig());

        if(reset.isResetScheduled()){
            modifications.clearWorldSpecificConfig(getConfig());
            timer.reset();
        }

        boolean timerEnabled = timer.isEnabled();
        long sec = timer.cancel();
        getConfig().set("timer.seconds", sec);
        getConfig().set("timer.enabled", timerEnabled);

        saveConfig();

        logger.log(Level.INFO, "Saving config complete");
    }


}
