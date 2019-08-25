package com.github.yimmit.warfare44.model;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.config.Configcategory;

import java.util.HashSet;
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
        return true;
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

    public HashSet<UUID> getSide1() {
        return side1;
    }

    public HashSet<UUID> getSide2() {
        return side2;
    }

    public int getMatchTime() {
        return matchTime;
    }

    /*
    public String toString()
    {
        String retString = "";
        retString.concat("Match Time = " + matchTime.toString())
        return retString;
    }

 */
}
