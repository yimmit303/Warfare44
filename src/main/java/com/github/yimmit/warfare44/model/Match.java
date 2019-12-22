package com.github.yimmit.warfare44.model;

import com.flowpowered.math.vector.Vector3d;
import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.config.Configcategory;
import com.github.yimmit.warfare44.config.CoordConfigData;
import com.github.yimmit.warfare44.util.InventoryUtil;
import org.slf4j.Logger;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;
import java.util.concurrent.TimeUnit;


//TODO: Maybe remove potion effects on joinmatch
//TODO: Make the timer actually tick down
//TODO: When timer is done tell the players if they won or lost
//TODO: Scoreboard
//TODO: Make match only start when two players are in it


public class Match
{
    public int maxplayers;

    private int side1score;
    private int side2score;

    private HashSet<UUID> side1 = new HashSet<>();
    private HashSet<UUID> side2 = new HashSet<>();

    private HashMap<UUID, String> playerClasses = new HashMap<>();

    private int matchTime;

    private int mapID;

    public boolean inprogress;

    public Match()
    {
        initialize();
    }

    private void initialize()
    {
        Configcategory root = Warfare44.getWarfare44().getConfig();
        this.inprogress = false;

        this.side1score = 0;
        this.side2score = 0;
        this.matchTime = root.CONFIG.time_limit;
        this.maxplayers = root.CONFIG.players_per_match;
        countdown(this.matchTime);
    }

    public int addPlayer(UUID id, String playerclass)
    {
        int side = -1;
        if(side1.size() + side2.size() >= maxplayers)
        {
            return -1;
        }
        if(this.side1.size() > this.side2.size())
        {
            side2.add(id);
            side = 2;
        }
        else if(this.side1.size() < this.side2.size())
        {
            side1.add(id);
            side = 1;
        }
        else
        {
            if(this.side1score > this.side2score)
            {
                side2.add(id);
                side = 2;
            }
            else if(this.side1score < this.side2score)
            {
                side1.add(id);
                side = 1;
            }
            else
            {
                side1.add(id);
                side = 1;
            }
        }
        if (side1.size() + side2.size() >= 2)
        {

        }
        playerClasses.put(id, playerclass);
        spawnPlayer(id);
        return side;
    }

    //Randomly selects a spawnpoint for whatever side the player is on
    public Transform<World> spawnPlayer(UUID id)
    {
        Random rand = new Random();
        ArrayList<CoordConfigData> spawnlist;

        if(side1.contains(id))
        {
            spawnlist = Warfare44.getWarfare44().getWorldData().mapdata.maps.get(mapID).mSide1Spawns;
        }
        else
        {
            spawnlist = Warfare44.getWarfare44().getWorldData().mapdata.maps.get(mapID).mSide2Spawns;
        }

        CoordConfigData spawncoord = spawnlist.get(rand.nextInt(spawnlist.size()));
        Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
        World world = player.getWorld();

        Vector3d locationvector = new Vector3d(spawncoord.mX, spawncoord.mY, spawncoord.mZ);
        Vector3d rotationvector = new Vector3d(spawncoord.mRotX, spawncoord.mRotY, spawncoord.mRotZ);

        Transform<World> transform = new Transform<>(world, locationvector, rotationvector);
        player.setTransform(transform);
        Logger log = Warfare44.getWarfare44().getLogger();
        log.info("We are spawning the player. About to supply");
        supply(id, playerClasses.get(id));
        return transform;
    }

    public void removePlayer(UUID id)
    {
        side1.remove(id);
        side2.remove(id);
        if ((side1.size() + side2.size()) == 0) // If there are no players, reset the match
        {
            reset();
        }
    }

    public void supply(UUID id, String playerclass)
    {
        InventoryUtil.clearInventory(id);

        String country = getSidecountry(getPlayerSide(id));

        InventoryUtil.giveItem(id, "modularwarfare:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mPrimary, 1);
        InventoryUtil.giveMaxItem(id, "modularwarfare:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mPrimary + "ammo");
        InventoryUtil.giveItem(id, "modularwarfare:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mSecondary, 1);
        InventoryUtil.giveMaxItem(id, "modularwarfare:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mSecondary + "ammo");

    }

    private void countdown(int time)
    {
        Logger log = Warfare44.getWarfare44().getLogger();
        log.info("Time remaining: " + Integer.toString(this.matchTime));
        this.matchTime -= 1;
        Task.builder().delay(1, TimeUnit.SECONDS).execute(t -> {
            countdown(matchTime);
        }).submit(Warfare44.getWarfare44());
    }

    public void reset()
    {
        this.side1.clear();
        this.side2.clear();

        initialize();
    }

    public boolean isFull()
    {
        return (side1.size()+side2.size()) == maxplayers;
    }

    public boolean contains(UUID id)
    {
        return side1.contains(id) || side2.contains(id);
    }

    public String getPlayerClass(UUID id)
    {
        return playerClasses.get(id);
    }

    public int getMaxplayers()
    {
        return maxplayers;
    }

    public int getNumPlayer()
    {
        return side1.size() + side2.size();
    }

    public int getSide1score() {
        return side1score;
    }

    public int getSide2score() {
        return side2score;
    }

    public void setSide1score(int num){side1score = num;}

    public void setSide2score(int num){side2score = num;}

    private String getSidecountry(int side)
    {
        if (side == 1)
        {
            return Warfare44.getWarfare44().getWorldData().mapdata.maps.get(mapID).mSide1Country;
        }
        else
        {
            return Warfare44.getWarfare44().getWorldData().mapdata.maps.get(mapID).mSide2Country;
        }
    }

    private int getPlayerSide(UUID id)
    {
        if (side1.contains(id))
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    public HashSet<UUID> getSide1() {
        return side1;
    }

    public HashSet<UUID> getSide2() {
        return side2;
    }

    public int getMatchTime() {
        return matchTime;
    }

    public void setMapID(int id){ mapID = id;}

    /*
    public String toString()
    {
        String retString = "";
        retString.concat("Match Time = " + matchTime.toString())
        return retString;
    }

 */
}
