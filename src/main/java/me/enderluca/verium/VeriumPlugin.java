package me.enderluca.verium;

import me.enderluca.verium.commands.ChallengeCommand;
import me.enderluca.verium.commands.GameRulesCommand;
import me.enderluca.verium.commands.ResetCommand;
import me.enderluca.verium.commands.TimerCommand;
import me.enderluca.verium.services.ChallengesService;
import me.enderluca.verium.services.GamerulesService;
import me.enderluca.verium.services.WorldResetService;
import me.enderluca.verium.services.TimerService;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeriumPlugin extends JavaPlugin {

    Logger logger;
    ServerProperties serverProps;
    TimerService timer;
    WorldResetService reset;
    ChallengesService challenges;
    GamerulesService gamerules;

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

        logger.info("Creating gamerules service");
        gamerules = new GamerulesService(this, getConfig());
        logger.info("Creating gamerules service complete");
        
        logger.info("Creating challenge service");
        challenges = new ChallengesService(this, getConfig(), timer, gamerules);
        logger.info("Creating challenges service complete");

        logger.log(Level.INFO, "Creating commands");
        getCommand("timer").setExecutor(new TimerCommand(timer));
        getCommand("reset").setExecutor(new ResetCommand(reset));
        getCommand("challenges").setExecutor(new ChallengeCommand(this, challenges));
        getCommand("gamerules").setExecutor(new GameRulesCommand(this, gamerules));
        logger.log(Level.INFO, "Creating command complete");
    }

    @Override
    public void onDisable() {
        logger.log(Level.INFO, "Saving config");

        boolean timerEnabled = timer.isEnabled();
        long sec = timer.cancel();
        getConfig().set("timer.seconds", sec);
        getConfig().set("timer.enabled", timerEnabled);

        getConfig().set("reset.scheduled", reset.isResetScheduled());

        challenges.saveConfig(getConfig());

        gamerules.saveConfig(getConfig());

        if(reset.isResetScheduled()){
            challenges.cleanWorldSpecificConfig(getConfig());
            gamerules.cleanWorldSpecificConfig(getConfig());
        }

        saveConfig();

        logger.log(Level.INFO, "Saving config complete");
    }


}
