package com.github.yimmit.warfare44;

import com.github.yimmit.warfare44.commands.*;
import com.github.yimmit.warfare44.config.Configcategory;
import com.github.yimmit.warfare44.config.WorldCategory;
import com.github.yimmit.warfare44.deathmatch.Deathmatch;
import com.github.yimmit.warfare44.listeners.ConnectionListener;
import com.github.yimmit.warfare44.listeners.DisconnectionListener;
import com.github.yimmit.warfare44.listeners.InteractListener;
import com.github.yimmit.warfare44.listeners.SignListener;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;

import static com.google.common.reflect.TypeToken.of;

// Imports for logger
import com.google.inject.Inject;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;


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
    private Configcategory root; //Used to access the values of config.conf seen in W44config.javas
    private ConfigurationNode dataNode;
    private WorldCategory worldData;

    private static Warfare44 warfare44;
    private Deathmatch dm;

    //Method used for all plugin initialization
    @Listener
    public void onServerStart(GameStartedServerEvent event)
    {

        if(!configDir.toFile().exists())
        {
            configDir.toFile().mkdir();
        }

        cfgLoader = HoconConfigurationLoader.builder().setFile(new File(configDir.toFile(), "config.conf")).build();
        dataLoader = HoconConfigurationLoader.builder().setFile(new File(configDir.toFile(), "data.conf")).build();
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
    }

    private void registerCommands()
    {
        game.getCommandManager().register(this, JoinMatchCommand.commandSpec(),"joinmatch");
        game.getCommandManager().register(this, ExitMatchCommand.commandSpec(), "exitmatch");
        game.getCommandManager().register(this, ListMatchCommand.commandSpec(), "listmatch");
        game.getCommandManager().register(this, MakeMapCommand.commandSpec(), "makemap");
        game.getCommandManager().register(this, SetMapSpawnCommand.commandSpec(), "setmapspawn");
        game.getCommandManager().register(this, ReloadDeathmatchCommand.commandSpec(), "reloaddm");
    }

    private void initConfig()
    {
        try {
            ConfigurationNode configRoot = cfgLoader.load(ConfigurationOptions.defaults().setObjectMapperFactory(factory).setShouldCopyDefaults(true));
            root = configRoot.getValue(of(Configcategory.class), new Configcategory());
            cfgLoader.save(configRoot);

            dataNode = dataLoader.load(ConfigurationOptions.defaults().setObjectMapperFactory(factory).setShouldCopyDefaults(true));
            worldData = dataNode.getValue(of(WorldCategory.class), new WorldCategory());
            dataLoader.save(dataNode);

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
        return root;
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

    public static Warfare44 getWarfare44()
    {
        return warfare44;
    }

}