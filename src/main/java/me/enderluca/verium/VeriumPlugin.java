package me.enderluca.verium;

import me.enderluca.verium.commands.ChallengeCommand;
import me.enderluca.verium.commands.ResetCommand;
import me.enderluca.verium.commands.TimerCommand;
import me.enderluca.verium.services.ChallengesService;
import me.enderluca.verium.services.WorldResetService;
import me.enderluca.verium.services.TimerService;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeriumPlugin extends JavaPlugin {

    Logger logger;
    TimerService timer;
    WorldResetService reset;
    ChallengesService challenges;
    @Override
    public void onEnable() {
        logger = getLogger();

        logger.info("Reading server properties");
        Properties serverProps = new Properties();
        String levelName;
        try {
            serverProps.load(new FileInputStream("./server.properties"));
            levelName = serverProps.getProperty("level-name");
            if(levelName == null)
                levelName = "world";
        }
        catch (FileNotFoundException ex) {
            logger.warning("server.properties file was not found, proceeding with default properties");
            levelName = "world";
        }
        catch (IOException ex){
            logger.warning("Error while reading server.properties file, proceeding with default properties");
            levelName = "world";
        }
        logger.info("Done reading server properties");


        logger.log(Level.INFO, "Creating reset service");
        boolean resetScheduled = getConfig().getBoolean("reset.scheduled", false);
        reset = new WorldResetService(logger, levelName, resetScheduled);
        logger.log(Level.INFO, "Creating reset service complete");

        logger.log(Level.INFO, "Creating timer");
        long sec = getConfig().getLong("timer.seconds", 0);
        boolean timerEnabled = getConfig().getBoolean("timer.enabled", true);
        timer = new TimerService(this, sec, timerEnabled);
        logger.log(Level.INFO, "Creating timer complete");

        logger.info("Creating challenge service");
        challenges = new ChallengesService(this, getConfig());
        logger.info("Creating challenges service complete");

        logger.log(Level.INFO, "Creating commands");
        getCommand("timer").setExecutor(new TimerCommand(timer));
        getCommand("reset").setExecutor(new ResetCommand(reset));
        getCommand("challenges").setExecutor(new ChallengeCommand(this, challenges));
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

        saveConfig();

        logger.log(Level.INFO, "Saving config complete");
    }


}
