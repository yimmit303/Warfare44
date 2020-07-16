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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.World;

import java.util.*;
import java.util.concurrent.TimeUnit;


//TODO: Maybe remove potion effects on joinmatch
//TODO: Scoreboard


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
    }

    private void beginMatch()
    {
        this.inprogress = true;
        countdown();
        for(UUID id : side1)
        {
            spawnPlayer(id);
        }
        for(UUID id : side2)
        {
            spawnPlayer(id);
        }
        titleToSide1("The match is starting");
        titleToSide2("The match is starting");
    }

    private void endMatch()
    {
        if(side1score > side2score)
        {
            titleToSide1("You win!");
            titleToSide2("You lose!");
        }
        else if(side1score < side2score)
        {
            titleToSide1("You lose!");
            titleToSide2("You win!");
        }
        else
        {
            titleToSide1("It was a tie.");
            titleToSide2("It was a tie.");
        }
        reset();
    }

    private void titleToSide1(String text)
    {
        for(UUID id : side1)
        {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            player.sendTitle(Title.builder().title(Text.of(text)).build());
        }
    }

    private void titleToSide2(String text)
    {
        for(UUID id : side2)
        {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            player.sendTitle(Title.builder().title(Text.of(text)).build());
        }
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
        playerClasses.put(id, playerclass);
        if (side1.size() + side2.size() == 2)
        {
            beginMatch();
        }
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

        // Gives the primary weapon
        InventoryUtil.giveItem(id, "modulus:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mPrimary, 1);

        // Tries to give the player an item named mPrimary + "ammo"
        // Some weapons don't follow this so the AmmoOverride will be set if that is the case
        if(Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mPrimaryAmmoOverride.equals(""))
        {
            InventoryUtil.giveMaxItem(id, "modulus:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mPrimary + "ammo");
        }
        else
        {
            InventoryUtil.giveMaxItem(id, "modulus:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mPrimaryAmmoOverride);
        }
        InventoryUtil.giveItem(id, "modulus:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mSecondary, 1);
        InventoryUtil.giveMaxItem(id, "modulus:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mSecondary + "ammo");

        InventoryUtil.giveHead(id, "modulus:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mHead);
        InventoryUtil.giveChest(id, "modulus:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mChest);
        InventoryUtil.giveLegs(id, "modulus:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mLegs);
        InventoryUtil.giveBoots(id, "modulus:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mBoots);

        InventoryUtil.giveAccessory(id, "w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mUpperface, "UpperFace");
        InventoryUtil.giveAccessory(id, "w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mLowerface, "LowerFace");
        InventoryUtil.giveAccessory(id, "w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mGloves, "Gloves");
        InventoryUtil.giveAccessory(id, "w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mArms, "Arms");
        InventoryUtil.giveAccessory(id, "w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mAccessory, "Accessory");
        InventoryUtil.giveAccessory(id, "w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mBackpack, "Backpack");
        InventoryUtil.giveAccessory(id, "w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get(country).mClassList.get(playerclass).mVest, "Vest");
    }

    private void countdown()
    {
        if (this.inprogress)
        {
            if(this.matchTime <= 0)
            {
                endMatch();
                return;
            }
            this.matchTime -= 1;
            Task.builder().delay(1, TimeUnit.SECONDS).execute(t -> {
                countdown();
            }).submit(Warfare44.getWarfare44());
        }
    }

    public void reset()
    {

        for(UUID id : side1)
        {
            Warfare44.getWarfare44().getGame().getCommandManager().process(Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get(), "spawn");
        }
        for(UUID id : side2)
        {
            Warfare44.getWarfare44().getGame().getCommandManager().process(Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get(), "spawn");
        }

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

    public void setSide1score(int num)
    {
        side1score = num;
        if(side1score >= Warfare44.getWarfare44().getConfig().CONFIG.points_to_win)
        {
            endMatch();
        }
    }

    public void setSide2score(int num)
    {
        side2score = num;
        if(side2score >= Warfare44.getWarfare44().getConfig().CONFIG.points_to_win)
        {
            endMatch();
        }
    }

    public String getSidecountry(int side)
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

    public String getMapName()
    {
        return (Warfare44.getWarfare44().getWorldData().mapdata.maps.get(mapID).mMapName);
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
