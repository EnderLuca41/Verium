package me.enderluca.verium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ServerProperties {

    private String levelName;

    public void load(String path) throws FileNotFoundException, IOException{
        Properties props = new Properties();
        props.load(new FileInputStream(path));

        levelName = props.getProperty("level-name", "world");
    }

    public void load() throws FileNotFoundException, IOException {
        load("./server.properties");
    }

    public void loadDefault(){
        levelName = "world";
    }

    /**
     * Tries to load the server properties from {@code path} <br>
     * If this fails, the default values will be loaded
     */
    public void tryLoad(String path){
        if(new File(path).exists()){
            loadDefault();
            return;
        }

        try{
            Properties props = new Properties();
            props.load(new FileInputStream(path));

            levelName = props.getProperty("level-name", "world");
        }
        catch (IOException e){
            loadDefault();
        }
    }

    /**
     * Tries to load the server properties from the default path
     * If this fails, the default values will be loaded
     */
    public void tryLoad(){
        tryLoad("./server.properties");
    }

    public String getLevelName() {
        return levelName;
    }
}
