package me.enderluca.verium;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

//
//Default values and descriptions are taken from https://docs.papermc.io/paper/reference/server-properties/
//

public class ServerProperties {

    private boolean acceptTransfers;
    private boolean allowFlight;
    private boolean allowNether;
    private boolean broadcastConsoleToOps;
    private boolean broadcastRconToOps;
    private String bugReportLink;
    private boolean debug;
    private String difficulty; // Allowed values: peaceful, easy, normal, hard
    private boolean enableCommandBlock;
    private boolean enableJmxMonitoring;
    private boolean enableQuery;
    private boolean enableRcon;
    private boolean enableStatus;
    private boolean enforceSecureProfile;
    private boolean enforceWhitelist;
    private int entityBroadcastRangePercentage;
    private boolean forceGamemode;
    private int functionPermissionLevel; //Allowed values 1 (moderator), 2 (gamemaster), 3 (admin), 4 (owner)
    private String gamemode;
    private boolean generateStructures;
    private String generatorSettings;
    private boolean hardcore;
    private boolean hideOnlinePlayers;
    private String initialDisabledPacks;
    private String initialEnabledPacks;
    private String levelName;
    private String levelSeed;
    private String levelType;
    private boolean logIps;
    private int maxChainedNeighborUpdates;
    private int maxPlayers;
    private int maxTickTime;
    private int maxWorldSize;
    private String motd;
    private int networkCompressionThreshold;
    private boolean onlineMode;
    private  int opPermissionLevel;
    private int pauseWhenEmptySeconds;
    private int playerIdleTimeout; //In seconds, 0 means no timeout
    private boolean preventProxyConnections;
    private boolean pvp;
    private int queryPort;
    private int rateLimit;
    private String rconPassword;
    private int rconPort;
    private String regionFileCompression; // Allowed values: deflate and none
    private boolean requireResourcePack;
    private String resourcePack;
    @Nullable
    private UUID resourcePackId;
    private String resourcePackPrompt;
    private String resourcePackSha1;
    private String serverIp;
    private int serverPort;
    private int simulationDistance;
    private boolean spawnMonsters;
    private int spawnProtection; //2x+1 formula is used, where x is the radius in blocks
    private boolean syncChunkWrites;
    private String textFilteringConfig;
    private int textFilteringVersion;
    private boolean useNativeTransport;
    private boolean whiteList;
    private int viewDistance;

    private Integer tryParseInt(String value, Integer defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void load(String path) throws FileNotFoundException, IOException{
        Properties props = new Properties();
        props.load(new FileInputStream(path));

        acceptTransfers = Boolean.parseBoolean(props.getProperty("accept-transfers", "false"));
        allowFlight = Boolean.parseBoolean(props.getProperty("allow-flight", "false"));
        allowNether = Boolean.parseBoolean(props.getProperty("allow-nether", "true"));
        broadcastConsoleToOps = Boolean.parseBoolean(props.getProperty("broadcast-console-to-ops", "true"));
        broadcastRconToOps = Boolean.parseBoolean(props.getProperty("broadcast-rcon-to-ops", "true"));
        bugReportLink = props.getProperty("bug-report-link", "");
        debug = Boolean.parseBoolean(props.getProperty("debug", "false"));
        difficulty = props.getProperty("difficulty", "easy");
        enableCommandBlock = Boolean.parseBoolean(props.getProperty("enable-command-block", "false"));
        enableJmxMonitoring = Boolean.parseBoolean(props.getProperty("enable-jmx-monitoring", "false"));
        enableQuery = Boolean.parseBoolean(props.getProperty("enable-query", "false"));
        enableRcon = Boolean.parseBoolean(props.getProperty("enable-rcon", "false"));
        enableStatus = Boolean.parseBoolean(props.getProperty("enable-status", "true"));
        enforceSecureProfile = Boolean.parseBoolean(props.getProperty("enforce-secure-profile", "true"));
        enforceWhitelist = Boolean.parseBoolean(props.getProperty("enforce-whitelist", "false"));
        entityBroadcastRangePercentage = tryParseInt(props.getProperty("entity-broadcast-range-percentage", "100"), 100);
        forceGamemode = Boolean.parseBoolean(props.getProperty("force-gamemode", "false"));
        functionPermissionLevel = tryParseInt(props.getProperty("function-permission-level", "2"), 2);
        gamemode = props.getProperty("gamemode", "survival");
        generateStructures = Boolean.parseBoolean(props.getProperty("generate-structures", "true"));
        generatorSettings = props.getProperty("generator-settings", "{}");
        hardcore = Boolean.parseBoolean(props.getProperty("hardcore", "false"));
        hideOnlinePlayers = Boolean.parseBoolean(props.getProperty("hide-online-players", "false"));
        initialDisabledPacks = props.getProperty("initial-disabled-packs", "");
        initialEnabledPacks = props.getProperty("initial-enabled-packs", "vanilla");
        levelName = props.getProperty("level-name", "world");
        levelSeed = props.getProperty("level-seed", "");
        levelType = props.getProperty("level-type", "minecraft\\:normal");
        logIps = Boolean.parseBoolean(props.getProperty("log-ips", "true"));
        maxChainedNeighborUpdates = tryParseInt(props.getProperty("max-chained-neighbor-updates", "1000000"), 1000000);
        maxPlayers = tryParseInt(props.getProperty("max-players", "20"), 20);
        maxTickTime = tryParseInt(props.getProperty("max-tick-time", "60000"), 60000);
        maxWorldSize = tryParseInt(props.getProperty("max-world-size", "29999984"), 29999984);
        motd = props.getProperty("motd", "A Minecraft Server");
        onlineMode = Boolean.parseBoolean(props.getProperty("online-mode", "true"));
        opPermissionLevel = tryParseInt(props.getProperty("op-permission-level", "4"), 4);
        pauseWhenEmptySeconds = tryParseInt(props.getProperty("pause-when-empty-seconds", "60"), 60);
        playerIdleTimeout = tryParseInt(props.getProperty("player-idle-timeout", "0"), 0);
        preventProxyConnections = Boolean.parseBoolean(props.getProperty("prevent-proxy-connections", "false"));
        pvp = Boolean.parseBoolean(props.getProperty("pvp", "true"));
        queryPort = tryParseInt(props.getProperty("query-port", "25565"), 25565);
        rateLimit = tryParseInt(props.getProperty("rate-limit", "0"), 0);
        rconPassword = props.getProperty("rcon-password", "");
        rconPort = tryParseInt(props.getProperty("rcon-port", "25575"), 25575);
        regionFileCompression = props.getProperty("region-file-compression", "deflate");
        requireResourcePack = Boolean.parseBoolean(props.getProperty("require-resource-pack", "false"));
        resourcePack = props.getProperty("resource-pack", "");
        String resourcePackIdStr = props.getProperty("resource-pack-id", "");
        resourcePackId = resourcePackIdStr.isEmpty() ? null : UUID.fromString(resourcePackIdStr);
        resourcePackPrompt = props.getProperty("resource-pack-prompt", "");
        resourcePackSha1 = props.getProperty("resource-pack-sha1", "");
        serverIp = props.getProperty("server-ip", "");
        serverPort = tryParseInt(props.getProperty("server-port", "25565"), 25565);
        simulationDistance = tryParseInt(props.getProperty("simulation-distance", "10"), 10);
        spawnMonsters = Boolean.parseBoolean(props.getProperty("spawn-monsters", "true"));
        spawnProtection = tryParseInt(props.getProperty("spawn-protection", "16"), 16);
        syncChunkWrites = Boolean.parseBoolean(props.getProperty("sync-chunk-writes", "true"));
        textFilteringConfig = props.getProperty("text-filtering-config", "");
        textFilteringVersion = tryParseInt(props.getProperty("text-filtering-version", "0"), 0);
        useNativeTransport = Boolean.parseBoolean(props.getProperty("use-native-transport", "true"));
        viewDistance = tryParseInt(props.getProperty("view-distance", "10"), 10);


    }

    public void load() throws FileNotFoundException, IOException {
        load("./server.properties");
    }

    public void loadDefault(){
        acceptTransfers = false;
        allowFlight = false;
        allowNether = true;
        broadcastConsoleToOps = true;
        broadcastRconToOps = true;
        bugReportLink = "";
        debug = false;
        difficulty = "easy";
        enableCommandBlock = false;
        enableJmxMonitoring = false;
        enableQuery = false;
        enableRcon = false;
        enableStatus = true;
        enforceSecureProfile = true;
        enforceWhitelist = false;
        entityBroadcastRangePercentage = 100;
        forceGamemode = false;
        functionPermissionLevel = 2; // Gamemaster
        gamemode = "survival";
        generateStructures = true;
        generatorSettings = "{}";
        hardcore = false;
        hideOnlinePlayers = false;
        initialDisabledPacks = "";
        initialEnabledPacks = "vanilla";
        levelName = "world";
        levelSeed = "";
        levelType = "minecraft:normal";
        logIps = true;
        maxChainedNeighborUpdates = 1000000;
        maxPlayers = 20;
        maxTickTime = 60000;
        maxWorldSize = 29999984;
        motd = "A Minecraft Server";
        onlineMode = true;
        opPermissionLevel = 4;
        pauseWhenEmptySeconds = 60;
        playerIdleTimeout = 0; // No timeout
        preventProxyConnections = false;
        pvp = true;
        queryPort = 25565;
        rateLimit = 0;
        rconPassword = "";
        rconPort = 25575;
        regionFileCompression = "deflate";
        requireResourcePack = false;
        resourcePack = "";
        resourcePackId = null;
        resourcePackPrompt = "";
        resourcePackSha1 = "";
        serverIp = "";
        serverPort = 25565;
        simulationDistance = 10;
        spawnMonsters = true;
        spawnProtection = 16;
        syncChunkWrites = true;
        textFilteringConfig = "";
        textFilteringVersion = 0;
        useNativeTransport = true;
        viewDistance = 10;
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

    /**
     * Whether this server accepts transfers from other servers using the transfer command/packet.
     * If this is set to false, the server will disconnect the client.
     */
    public boolean getAcceptTransfers() { return acceptTransfers; }

    /**
     * Means that users will not be kicked if they fly whilst in Survival mode.
     * This is likely to occur through hacking however there can be false positives.
     */
    public boolean getAllowFlight() { return allowFlight; }

    /**
     * Allows users to travel to the Nether.
     */
    public boolean getAllowNether() { return allowNether; }

    /**
     * Send console command output to all online operators.
     */
    public boolean getBroadcastConsoleToOps() { return broadcastConsoleToOps; }

    /**
     * Send rcon command output to all online operators.
     */
    public boolean getBroadcastRconToOps() { return broadcastRconToOps; }

    /**
     * A URL value used for the Report Server Bugs button in the Server Links client menu.
     */
    public String getBugReportLink() { return bugReportLink; }

    /**
     * Enables the server’s debug mode.
     */
    public boolean getDebug() { return debug; }

    /**
     * Defines the difficulty of the server. (Allowed values: “peaceful”, “easy”, “normal”, “hard”)
     */
    public String getDifficulty() { return difficulty; }

    /**
     * Enables command blocks.
     */
    public boolean getEnableCommandBlock() { return enableCommandBlock; }

    /**
     * Exposes an MBean with the Object name “net.minecraft.server:type=Server”
     * and two attributes “averageTickTime” and “tickTimes” exposing the tick times in milliseconds.
     */
    public boolean getEnableJmxMonitoring() { return enableJmxMonitoring; }

    /**
     * Enables GameSpy4 protocol server listener. Used to get information about server.
     */
    public boolean getEnableQuery() { return enableQuery; }

    /**
     * Enables remote access to the server console.
     */
    public boolean getEnableRcon() { return enableRcon; }

    /**
     * Makes the server appear on the server list and also enables listener for getting server information.
     * If turned off, server will appear offline but players will still be able to connect.
     */
    public boolean getEnableStatus() { return enableStatus; }

    /**
     * If set to true, players without a Mojang-signed public key will not be able to connect to the server.
     */
    public boolean getEnforceSecureProfile() { return enforceSecureProfile; }

    /**
     * If set to true, the server will kick players who are not on the whitelist.
     */
    public boolean getEnforceWhitelist() { return enforceWhitelist; }

    /**
     * Controls how close entities need to be before being sent to clients.
     * Higher values means they will be rendered from farther away, potentially causing more lag.
     * This is expressed the percentage of the default value. For example, setting to 50 will make it half as usual.
     * This mimics the function on the client video settings (not unlike Render Distance, which the client can customize so long as it”s under the server”s setting).
     * This must be between 10 and 1000 percent.
     */
    public int getEntityBroadcastRangePercentage() { return entityBroadcastRangePercentage; }

    /**
     * Force players to join in the default game mode. This will reset their previous game mode when they reconnect.
     */
    public boolean getForceGamemode() { return forceGamemode; }

    /**
     * Sets the default permission level for functions. (Allowed values: 1, 2, 3, 4)
     */
    public int getFunctionPermissionLevel() { return functionPermissionLevel; }

    /**
     * Defines the mode of gameplay. (Allowed values: “survival”, “creative”, “adventure”, “spectator”)
     */
    public String getGamemode() { return gamemode; }

    /**
     * Defines whether structures (such as villages) will be generated.
     */
    public boolean getGenerateStructures() { return generateStructures; }

    /**
     * The settings used to customize world generation. Follow its format and write the corresponding JSON string.
     */
    public String getGeneratorSettings() { return generatorSettings; }

    /**
     * If set to true, players will be set to spectator mode if they die.
     */
    public boolean getHardcore() { return hardcore; }

    /**
     * Hides the player list sent with the status request packets.
     */
    public boolean getHideOnlinePlayers() { return hideOnlinePlayers; }

    /**
     * Comma-separated list of datapacks to not be auto-enabled on world creation.
     */
    public String getInitialDisabledPacks() { return initialDisabledPacks; }

    /**
     * Comma-separated list of datapacks to be enabled during world creation.
     * Feature packs need to be explicitly enabled.
     */
    public String getInitialEnabledPacks() { return initialEnabledPacks; }

    /**
     * The name of the world. This will be the name of the folder in which the world is saved.
     */
    public String getLevelName() { return levelName; }

    /**
     * The seed used to generate the world. Leave blank to default to random.
     */
    public String getLevelSeed() { return levelSeed; }

    /**
     * Defines the type of the world generator. (Allowed values: “normal”, “flat”, “large_biomes”, “amplified”, “single_biome_surface”, “buffet”, “default_1_1”, “customized”)
     */
    public String getLevelType() { return levelType; }

    /**
     * Whether player IP addresses should be logged by the server.
     * This does not impact the ability of plugins to log the IP addresses of players
     */
    public boolean getLogIps() { return logIps; }

    /**
     * Limits the number of consecutive neighbor updates before skipping subsequent updates.
     * Negative values will disable the limit.
     */
    public int getMaxChainedNeighborUpdates() { return maxChainedNeighborUpdates; }

    /**
     * The maximum number of players that can be connected to the server at the same time.
     */
    public int getMaxPlayers() { return maxPlayers; }

    /**
     * The maximum number of milliseconds a single tick may take before the server watchdog stops the server with the message.
     * If a single server tick took 60.00 seconds (should be max 0.05) it will be considered
     * to be crashed and the server will forcibly shut down via calling System.exit(1).
     * Setting this to -1 will disable watchdog entirely.
     */
    public int getMaxTickTime() { return maxTickTime; }

    /**
     * The maximum allowed size of the world radius, in blocks.
     * This only affects the chunks that are generated when the world is initially created,
     * and not the world border (Limited to 29999984).
     */
    public int getMaxWorldSize() { return maxWorldSize; }

    /**
     * The message of the day, displayed in the server list.
     */
    public String getMotd() { return motd; }

    /**
     * The number of bytes of a packet before it is compressed. Setting to a negative disables compression.
     */
    public int getNetworkCompressionThreshold() { return networkCompressionThreshold; }

    /**
     * If set to true, the server checks all connecting players against Minecraft's account database.
     * This requires all connected players to have a valid Minecraft account and makes it impossible for cracked players to connect.
     */
    public boolean getOnlineMode() { return onlineMode; }

    /**
     * Sets the default permission level for ops when using /op. (Allowed values: 0, 1, 2, 3, 4)
     */
    public int getOpPermissionLevel() { return opPermissionLevel; }

    /**
     * How many seconds have to pass after no player has been online before the server is paused.
     * This is disabled by default because it is incompatible with what plugins expect and might do with no players online.
     */
    public int getPauseWhenEmptySeconds() { return pauseWhenEmptySeconds; }

    /**
     * If non-zero, players are kicked from the server if they are idle for more than that many minutes. (Default: 0).
     * The following packets stop this timer: “Click Window”, “Enchant Item”, “Update Sign”, “Player Digging”,
     * “Player Block Placement”, “Held Item Change”, “Animation (swing arm)”, “Entity Action”, “Client Status”, “Chat Message”, “Use Entity”.
     */
    public int getPlayerIdleTimeout() { return playerIdleTimeout; }

    /**
     * If the ISP/AS sent from the server is different from the one from Mojang Studios authentication server, the player not allowed to join the server.
     */
    public boolean getPreventProxyConnections() { return preventProxyConnections; }

    /**
     * If set to false, players are not allowed to attack other players.
     */
    public boolean getPvp() { return pvp; }

    /**
     * The port for the query server. This is used to get information about the server.
     */
    public int getQueryPort() { return queryPort; }

    /**
     * Sets the maximum allowed number of packets that can be sent before getting kicked. Setting this to 0 disables the limit.
     */
    public int getRateLimit() { return rateLimit; }

    /**
     * The password for the rcon server.
     */
    public String getRconPassword() { return rconPassword; }

    /**
     * The port to start the rcon server on.
     */
    public int getRconPort() { return rconPort; }

    /**
     * Specifies the compression type used to compress region files. Possible values are: “deflate”, “lz4” and “none”.
     * If set to “none”, region files will take up significantly more disk space, but it might make sense together with filesystem-level compression.
     */
    public String getRegionFileCompression() { return regionFileCompression; }

    /**
     * If true, a player must have the given resource pack to connect. They will be kicked if they do not have it.
     */
    public boolean getRequireResourcePack() { return requireResourcePack; }

    /**
     * The URL to the server’s resource pack.
     */
    public String getResourcePack() { return resourcePack; }

    /**
     * The UUID of the server resource pack to use.
     */
    @Nullable
    public UUID getResourcePackId() { return resourcePackId; }

    /**
     * The message that is displayed when the client is prompted to download the resource pack.
     */
    public String getResourcePackPrompt() { return resourcePackPrompt; }

    /**
     * The hash of the resource pack, used for verification.
     * This is recommended to be set to ensure players are downloading the correct pack.
     */
    public String getResourcePackSha1() { return resourcePackSha1; }

    /**
     * The IP address to bind to. Leave blank to bind to all interfaces.
     */
    public String getServerIp() { return serverIp; }

    /**
     * The port to listen on for connections.
     */
    public int getServerPort() { return serverPort; }

    /**
     * Sets the maximum distance from players that living entities may be located in order to be updated by the server,
     * measured in chunks in each direction of the player (radius, not diameter).
     * If entities are outside this radius, then they will not be ticked by the server nor will they be visible to players.
     * Must be between 3 and 32 inclusive.
     */
    public int getSimulationDistance() { return simulationDistance; }

    /**
     * Determines if monsters will be spawned.
     */
    public boolean getSpawnMonsters() { return spawnMonsters; }

    /**
     * Used to determine the side length of the spawn protection.
     * The formula of 2x+1 is used. A value of 1 will result in a side length of 3 blocks.
     * Setting this to 0 will disable spawn protection. There must be at least 1 operator to be enabled.
     */
    public int getSpawnProtection() { return spawnProtection; }

    /**
     * Enables synchronous writing of chunk data. Has no effect on Paper by default, unless the corresponding system property is also set to true
     */
    public boolean getSyncChunkWrites() { return syncChunkWrites; }

    /**
     * The path to the text filtering configuration file. Leave blank to disable text filtering.
     */
    public String getTextFilteringConfig() { return textFilteringConfig; }

    /**
     * The version of the configuration format used for text-filtering-config. Valid values are 0 and 1.
     */
    public int getTextFilteringVersion() { return textFilteringVersion; }

    /**
     * Provides a performance boost for Linux servers.
     */
    public boolean getUseNativeTransport() { return useNativeTransport; }

    /**
     * Sets the amount of world data the server sends the client, measured in chunks in each direction of the player (radius, not diameter).
     * It determines the server-side viewing distance (Default: 10, Min: 3, Max: 32).
     */
    public int getViewDistance() { return viewDistance; }

    /**
     * Enables a whitelist on the server. If enabled, the server will only allow selected users to connect.
     */
    public boolean getWhiteList() { return whiteList; }
}
