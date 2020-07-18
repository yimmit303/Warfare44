package com.github.yimmit.warfare44.listeners;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.userdata.PlayerData;
import com.github.yimmit.warfare44.userdata.PlayerList;
import com.github.yimmit.warfare44.util.PlayerDataUtil;
import org.slf4j.Logger;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.util.Tristate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static com.github.yimmit.warfare44.Warfare44.getWarfare44;

public class ConnectionListener
{
    @Listener(order = Order.EARLY)
    @IsCancelled(Tristate.UNDEFINED)
    public void onClientConnection(ClientConnectionEvent.Auth event)
    {
        Logger log = getWarfare44().getLogger();
        log.info("Player connecting");

    }

    @Listener
    public void onClientJoin(ClientConnectionEvent.Join event)
    {
        Player p = event.getTargetEntity();
        p.offer(Keys.GAME_MODE, GameModes.ADVENTURE);
        Logger log = getWarfare44().getLogger();
        log.info("Player's UUID: " + p.getUniqueId().toString());
        log.info("Logged in at " + java.time.LocalDate.now().toString());
        HashMap<UUID, PlayerData> data = getWarfare44().getUserData().PLAYERLIST;
        if (!data.containsKey(p.getUniqueId()))
        {
            PlayerDataUtil.initializeNewPlayer(p.getUniqueId());
        }
        else
        {
            data.get(p.getUniqueId()).mLastLogged = java.time.LocalDate.now().toString();
            Warfare44.getWarfare44().saveUserData();
        }
    }
}
