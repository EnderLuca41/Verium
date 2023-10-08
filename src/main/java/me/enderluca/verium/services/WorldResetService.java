package me.enderluca.verium.services;

import me.enderluca.verium.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorldResetService {
    private boolean resetScheduled;
    private boolean waitForConfirmation;
    private final Logger logger;


    public WorldResetService(Logger logger, String defaultWorldName, boolean resetScheduled) {
        this.logger = logger;

        String worldPath;
        try {
            worldPath = Bukkit.getWorldContainer().getCanonicalPath() + "/" + defaultWorldName;
        }
        catch (Exception ex){
            this.resetScheduled = resetScheduled;
            this.logger.warning("Unable to get canonical path of world container\n" + ex.getMessage());
            return;
        }

        if(!new File(worldPath).exists())
            this.logger.warning(String.format("Couldn't create ResetService properly because world %s does not exist", defaultWorldName));

        if(resetScheduled){
            if(!deleteWorld(defaultWorldName)){
                logger.warning("World reset was unsuccessful, retrying on next server start");
                this.resetScheduled = true; //If the world reset fails for some reason, we will retry on next startup
                return;
            }
        }

        this.resetScheduled = false;
    }

    /**
     * Check if the world folder of the main world (overworld/nether/end) are deletable
     * @param worldPath Path of the main world overworld
     * @return If the main worlds (if exist) are deletable
     */
    private boolean worldFoldersDeletable(String worldPath){
        File overworld = new File(worldPath);
        if(overworld.exists() && !FileUtil.isFolderDeletable(overworld))
            return false;

        File nether = new File(worldPath + "_nether");
        if(nether.exists() && !FileUtil.isFolderDeletable(nether))
            return false;

        File end = new File(worldPath + "_the_end");
        if(end.exists() && !FileUtil.isFolderDeletable(end))
            return false;

        return true;
    }
    private boolean deleteWorld(String name){
        logger.log(Level.INFO, "Trying to delete main world {0}", name);

        //We have to get the canonical path, because the absolute has a dot placed at the end
        String worldPath;
        try {
            worldPath = Bukkit.getWorldContainer().getCanonicalPath() + "/" + name;
        }
        catch (Exception ex){
            this.logger.warning("Unable to get canonical path of world container\n" + ex.getMessage());
            return false;
        }

        //World folder might be not deletable
        if(!worldFoldersDeletable(worldPath)){
            this.logger.warning("Unable to delete world folders because of missing write permission");
        }

        File overworld = new File(worldPath);
        File nether = new File(worldPath + "_nether");
        File end = new File(worldPath + "_the_end");

        //No need to check for the return value of deleteFolder, because
        //we are already checked if the folders are deletable
        if(overworld.exists()){
            FileUtil.deleteFolder(overworld);
            logger.log(Level.INFO, "Deleted overworld successfully");
        }

        if(nether.exists()){
            FileUtil.deleteFolder(nether);
            logger.log(Level.INFO, "Deleted nether successfully");
        }

        if(end.exists()){
            FileUtil.deleteFolder(end);
            logger.log(Level.INFO, "Deleted end successfully");
        }

        return true;
    }

    /**
     * Schedules a reset that needs to be confirmed with {@link #confirmAndRestart()}
     */
    public void reset(){
        waitForConfirmation = true;
    }

    /**
     * Confirms a reset, kicks all players and restarts the server
     */
    public void confirmAndRestart(){
        if(!waitForConfirmation)
            return;

        logger.log(Level.INFO, "Resetting world, server is restarting");
        resetScheduled = true;
        for(Player p : Bukkit.getOnlinePlayers())
            p.kickPlayer("Server is resetting");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
    }

    public boolean isResetScheduled(){
        return resetScheduled;
    }
    public boolean isWaitingForConfirmation(){
        return waitForConfirmation;
    }
}
