package com.github.yimmit.warfare44.listeners;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.util.ScoreboardUtil;
import org.slf4j.Logger;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.util.Tristate;

public class ConnectionListener
{
    @Listener(order = Order.EARLY)
    @IsCancelled(Tristate.UNDEFINED)
    public void onClientConnection(ClientConnectionEvent.Auth event)
    {
        Logger log = Warfare44.getWarfare44().getLogger();
        log.info("Player connecting");
    }

    @Listener
    public void onClientJoin(ClientConnectionEvent.Join event)
    {
        Player p = event.getTargetEntity();
        p.offer(Keys.GAME_MODE, GameModes.ADVENTURE);
        p.setScoreboard(ScoreboardUtil.getScoreboard());
    }
}
