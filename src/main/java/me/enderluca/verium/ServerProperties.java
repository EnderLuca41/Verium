package me.enderluca.verium;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    public String getLevelName() {
        return levelName;
    }
}
