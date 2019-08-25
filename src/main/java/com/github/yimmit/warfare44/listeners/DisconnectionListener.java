package com.github.yimmit.warfare44.listeners;

import com.github.yimmit.warfare44.Warfare44;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.util.Tristate;

import java.util.UUID;

public class DisconnectionListener
{
    @Listener(order = Order.EARLY)
    @IsCancelled(Tristate.UNDEFINED)
    public void onClientDisconnection(ClientConnectionEvent.Disconnect event)
    {
        Logger log = Warfare44.getWarfare44().getLogger();
        if(!event.getCause().first(Player.class).isPresent())
        {
            log.info("No player found");
            return;
        }
        Player discon = event.getCause().first(Player.class).get();
        UUID id = discon.getUniqueId();
        Warfare44.getWarfare44().getDeathMatch().exitMatch(id);
    }
}

