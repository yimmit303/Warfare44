package com.github.yimmit.warfare44.model;

import com.flowpowered.math.vector.Vector3d;
import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.config.Configcategory;
import com.github.yimmit.warfare44.config.CoordConfigData;
import org.slf4j.Logger;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class Match
{
    public int maxplayers;

    private int side1score;
    private int side2score;

    private HashSet<UUID> side1 = new HashSet<>();
    private HashSet<UUID> side2 = new HashSet<>();

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
    }

    public boolean addPlayer(UUID id)
    {
        if(side1.size() + side2.size() >= maxplayers)
        {
            return false;
        }
        if(this.side1.size() > this.side2.size())
        {
            side2.add(id);
        }
        else if(this.side1.size() < this.side2.size())
        {
            side1.add(id);
        }
        else
        {
            if(this.side1score > this.side2score)
            {
                side2.add(id);
            }
            else if(this.side1score < this.side2score)
            {
                side1.add(id);
            }
            else
            {
                side1.add(id);
            }
        }
        spawnPlayer(id);
        return true;
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
        return transform;
    }

    public void removePlayer(UUID id)
    {
        side1.remove(id);
        side2.remove(id);
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
