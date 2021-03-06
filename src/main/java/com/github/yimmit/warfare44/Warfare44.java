package com.github.yimmit.warfare44;

import com.github.yimmit.warfare44.commands.*;

import com.github.yimmit.warfare44.config.Configcategory;
import com.github.yimmit.warfare44.config.WorldCategory;
import com.github.yimmit.warfare44.userdata.PlayerList;

import com.github.yimmit.warfare44.deathmatch.Deathmatch;
import com.github.yimmit.warfare44.listeners.*;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;

import static com.google.common.reflect.TypeToken.of;

// Imports for logger
import com.google.inject.Inject;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;


@Plugin(id = "warfare44", name = "Warfare 44", version = "0.2.2", description = "Custom team death matches and classes")
public class Warfare44 {

    @Inject
    private GuiceObjectMapperFactory factory;

    @Inject
    private Logger logger;

    @Inject
    private Game game;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;
    private ConfigurationLoader<CommentedConfigurationNode> cfgLoader;
    private ConfigurationLoader<CommentedConfigurationNode> dataLoader;
    private ConfigurationLoader<CommentedConfigurationNode> userDataLoader;
    private ConfigurationNode dataNode;
    private ConfigurationNode configNode;
    private ConfigurationNode userDataNode;
    private Configcategory configData;  //Used to access the values of config.conf seen in W44config
    private WorldCategory worldData;    //Used to access the values of data.conf seen in W44config
    private PlayerList userData;    //Used to access the values of userdata.conf seen in W44config
    private ArrayList<String> validClasses = new ArrayList<>();

    private static Warfare44 warfare44;
    private Deathmatch dm;

    //Method used for all plugin initialization
    @Listener
    public void onServerStart(GameStartedServerEvent event)
    {
        validClasses.add("assault");
        validClasses.add("recon");
        validClasses.add("medic");
        validClasses.add("support");
        if(!configDir.toFile().exists())
        {
            configDir.toFile().mkdir();
        }

        cfgLoader = HoconConfigurationLoader.builder().setFile(new File(configDir.toFile(), "config.conf")).build();
        dataLoader = HoconConfigurationLoader.builder().setFile(new File(configDir.toFile(), "data.conf")).build();
        userDataLoader = HoconConfigurationLoader.builder().setFile(new File(configDir.toFile(), "userdata.conf")).build();
        initConfig();

        this.dm = new Deathmatch();
    }

    @Listener
    public void onLoadingComplete(GameLoadCompleteEvent event)
    {

    }

    @Listener
    public void preInit(GamePreInitializationEvent event)
    {
        warfare44 = this;
        logger.info("Warfare44 started");
    }

    @Listener
    public void onGameInit(GameInitializationEvent e)
    {
        registerListeners();
        registerCommands();
    }

    private void registerListeners()
    {
        Sponge.getEventManager().registerListeners(this, new ConnectionListener());
        Sponge.getEventManager().registerListeners(this, new DisconnectionListener());
        Sponge.getEventManager().registerListeners(this, new SignListener());
        Sponge.getEventManager().registerListeners(this, new InteractListener());
        Sponge.getEventManager().registerListeners(this, new CommandListener());
        Sponge.getEventManager().registerListeners(this, new DeathListener());
        Sponge.getEventManager().registerListeners(this, new RespawnListener());
    }

    private void registerCommands()
    {
        game.getCommandManager().register(this, JoinMatchCommand.commandSpec(),"joinmatch", "join");
        game.getCommandManager().register(this, ExitMatchCommand.commandSpec(), "exitmatch", "leave", "exit");
        game.getCommandManager().register(this, ListMatchCommand.commandSpec(), "listmatch", "games");
        game.getCommandManager().register(this, MakeMapCommand.commandSpec(), "makemap");
        game.getCommandManager().register(this, SetMapSpawnCommand.commandSpec(), "setmapspawn");
        game.getCommandManager().register(this, ReloadDeathmatchCommand.commandSpec(), "reloaddm");
        game.getCommandManager().register(this, SetMapCountriesCommand.commandSpec(), "setmapcountry");
        game.getCommandManager().register(this, ShowRankCommand.commandSpec(), "showrank");
    }

    private void initConfig()
    {
        try {
            configNode = cfgLoader.load(ConfigurationOptions.defaults().setObjectMapperFactory(factory).setShouldCopyDefaults(true));
            configData = configNode.getValue(of(Configcategory.class), new Configcategory());
            cfgLoader.save(configNode);

            dataNode = dataLoader.load(ConfigurationOptions.defaults().setObjectMapperFactory(factory).setShouldCopyDefaults(true));
            worldData = dataNode.getValue(of(WorldCategory.class), new WorldCategory());
            dataLoader.save(dataNode);

            userDataNode = userDataLoader.load(ConfigurationOptions.defaults().setObjectMapperFactory(factory).setShouldCopyDefaults(true));
            userData = userDataNode.getValue(of(PlayerList.class), new PlayerList());
            userDataLoader.save(userDataNode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveDataConfig()
    {
        try
        {
            dataNode.setValue(of(WorldCategory.class), worldData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            dataLoader.save(dataNode);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveConfig()
    {
        try
        {
            configNode.setValue(of(Configcategory.class), configData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            cfgLoader.save(configNode);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveUserData()
    {
        try
        {
            userDataNode.setValue(of(PlayerList.class), userData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            userDataLoader.save(userDataNode);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void reloadDeathmatch()
    {
        this.dm = new Deathmatch();
    }

    @Listener
    public void onDisable(GameStoppingServerEvent event) {

    }

    public Logger getLogger()
    {
        return logger;
    }

    public Deathmatch getDeathMatch()
    {
        return dm;
    }

    public Game getGame()
    {
        return game;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getCfgLoader() {
        return cfgLoader;
    }

    public Configcategory getConfig()
    {
        return configData;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getDataLoader() {
        return dataLoader;
    }

    public ConfigurationNode getSignnode() {
        return dataNode;
    }

    public WorldCategory getWorldData()
    {
        return worldData;
    }

    public PlayerList getUserData() {
        return userData;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getUserDataLoader() {
        return userDataLoader;
    }

    public static Warfare44 getWarfare44()
    {
        return warfare44;
    }

    public ArrayList<String> getValidClasses()
    {
        return validClasses;
    }
}