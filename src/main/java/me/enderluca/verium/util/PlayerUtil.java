package me.enderluca.verium.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

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

public final class PlayerUtil {

    private PlayerUtil() {}

    @Nonnull
    private static final Map<String, UUID> playerUuidCache = new HashMap<>();

    /**
     * Get the UUID of a player by their name, returns null if the player does not exist or some other problem with the mojang api occurs
     */
    @Nullable
    public static UUID getUUID(@Nonnull String name){
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
    public static String getPlayerName(@Nonnull UUID uuid){
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
}
