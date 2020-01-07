package com.github.yimmit.warfare44.deathmatch;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.model.Match;
import com.github.yimmit.warfare44.util.InventoryUtil;
import org.spongepowered.api.Game;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;

import java.util.*;

public class Deathmatch
{
    private ArrayList<Match> mMatchList = new ArrayList<>();
    private HashSet<UUID> mActivePlayers = new HashSet<>();
    private ArrayList<Integer> mActiveMaps = new ArrayList<>();

    private int mNumMatches;

    public Deathmatch()
    {
        mNumMatches = Warfare44.getWarfare44().getConfig().CONFIG.number_of_matches;
        if(mNumMatches > Warfare44.getWarfare44().getWorldData().mapdata.maps.size())
        {
            mNumMatches = Warfare44.getWarfare44().getWorldData().mapdata.maps.size();
        }
        for(int i = 0; i < mNumMatches; i++)
        {
            mMatchList.add(new Match());
            int id = pickMapID();
            mActiveMaps.add(id);
            mMatchList.get(i).setMapID(id);
        }
    }

    private int pickMapID()
    {
        ArrayList<Integer> potentialmaps = new ArrayList<>();
        for(int i = 0; i < Warfare44.getWarfare44().getWorldData().mapdata.maps.size(); i++)
        {
            potentialmaps.add(i);
        }
        for(Integer id : mActiveMaps)
        {
            potentialmaps.remove(id);
        }

        Random r = new Random();
        return potentialmaps.get(r.nextInt(potentialmaps.size()));
    }

    public int getMatchNum()
    {
        return mMatchList.size();
    }

    public int joinMatch(UUID id, String playerclass)
    {
        for(int i = 0; i < mNumMatches; i++)
        {
            if(joinMatchByNum(id,i + 1, playerclass) != -1)
            {
                return i+1;
            }
        }
        return -1;
    }

    public int joinMatchByNum(UUID id, int matchnum, String playerclass)
    {
        if(!mMatchList.get(matchnum - 1).isFull())
        {
            int sidenum = mMatchList.get(matchnum - 1).addPlayer(id, playerclass);

            mActivePlayers.add(id);
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public void exitMatch(UUID id)
    {
        mActivePlayers.remove(id);
        for(int i = 0; i < mNumMatches; i++)
        {
            mMatchList.get(i).removePlayer(id);
        }
        Game game = Warfare44.getWarfare44().getGame();
        if(game.getServer().getPlayer(id).isPresent())
        {
            Player player = game.getServer().getPlayer(id).get();
            player.offer(Keys.GAME_MODE, GameModes.ADVENTURE);
            InventoryUtil.clearInventory(id);
            game.getCommandManager().process(player, "spawn");
        }

    }

    public void ejectPlayers()
    {
        Game game = Warfare44.getWarfare44().getGame();
        for(UUID id : mActivePlayers)
        {
            if(game.getServer().getPlayer(id).isPresent())
            {
                game.getCommandManager().process(game.getServer().getPlayer(id).get(), "spawn");
            }
        }
    }

    public boolean clearMatch(int Matchid)
    {
        this.mMatchList.get(Matchid).reset();
        return true;
    }


    public ArrayList<String> getAllMatchStatus()
    {
        ArrayList<String> statuslist = new ArrayList<>();

        for(int i = 0; i < mMatchList.size(); i++)
        {
            Match match = mMatchList.get(i);
            statuslist.add("Match " + (i+1));
            statuslist.add("Map Name: " + match.getMapName());
            statuslist.add("In Progress: ");
            statuslist.add("" + match.inprogress);
            statuslist.add("Time left: " + match.getMatchTime() + "s");
            statuslist.add("Players: ");
            statuslist.add(match.getSide1().size() + "/" + match.maxplayers/2);
            statuslist.add(match.getSide2().size() + "/" + match.maxplayers/2);
            statuslist.add("Score: ");
            statuslist.add("" + match.getSide1score());
            statuslist.add("" + match.getSide2score());

        }
        return statuslist;
    }

    public Optional<Match> getPlayerMatch(UUID id)
    {
        Optional<Match> optionalmatch = Optional.empty();
        for(Match m : mMatchList)
        {
            if(m.contains(id))
            {
                optionalmatch = Optional.of(m);
            }
        }
        return optionalmatch;
    }

    public int getFirstOpenMatch()
    {
        for(int i = 0; i < mMatchList.size(); i++)
        {
            if(!mMatchList.get(i).isFull())
            {
                return i + 1;
            }
        }
        return -1;
    }

    public ArrayList<Match> getmMatchList() {
        return mMatchList;
    }

    public boolean isPlayerActive(UUID id){return mActivePlayers.contains(id);}
}
