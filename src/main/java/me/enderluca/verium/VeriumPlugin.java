package me.enderluca.verium;

import me.enderluca.verium.commands.*;
import me.enderluca.verium.services.*;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeriumPlugin extends JavaPlugin {

    Logger logger;
    ServerProperties serverProps;
    TimerService timer;
    WorldResetService reset;
    ModificationsService modifications;

    @Override
    public void onEnable() {
        logger = getLogger();

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

        logger.info("Creating Modifications service to handle challenges and gamerules");
        modifications = new ModificationsService(this, getConfig(), timer);
        logger.info("Creating Modifications service to handle challenges and gamerules complete");

        logger.log(Level.INFO, "Creating commands");
        getCommand("timer").setExecutor(new TimerCommand(timer));
        getCommand("reset").setExecutor(new ResetCommand(reset));
        getCommand("challenges").setExecutor(new ChallengeCommand(this, modifications.getChallengeService()));
        getCommand("gamerules").setExecutor(new GameRulesCommand(this, modifications.getGamerulesService()));
        getCommand("pause").setExecutor(new PauseCommand(modifications));
        getCommand("resume").setExecutor(new ResumeCommand(modifications));
        getCommand("goals").setExecutor(new GoalsCommand(this, modifications.getGoalsService()));
        logger.log(Level.INFO, "Creating commands complete");
    }

    @Override
    public void onDisable() {
        logger.info("Pausing challenges, gamerules and goals");
        modifications.pause();

        logger.log(Level.INFO, "Saving config");

        boolean timerEnabled = timer.isEnabled();
        long sec = timer.cancel();
        getConfig().set("timer.seconds", sec);
        getConfig().set("timer.enabled", timerEnabled);

        getConfig().set("reset.scheduled", reset.isResetScheduled());

        modifications.saveConfig(getConfig());

        if(reset.isResetScheduled()){
            modifications.getChallengeService().cleanWorldSpecificConfig(getConfig());
            modifications.getGamerulesService().cleanWorldSpecificConfig(getConfig());
            modifications.getGoalsService().clearWorldSpecificConfig(getConfig());
        }

        saveConfig();

        logger.log(Level.INFO, "Saving config complete");
    }


}
