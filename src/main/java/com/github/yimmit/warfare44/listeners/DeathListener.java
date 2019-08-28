package com.github.yimmit.warfare44.listeners;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.deathmatch.Deathmatch;
import com.github.yimmit.warfare44.model.Match;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;

public class DeathListener
{
    @Listener
    public void onPlayerDeath(DestructEntityEvent.Death event)
    {

        if(!event.getCause().first(Player.class).isPresent() || !(event.getTargetEntity() instanceof Player))
        {
            return;
        }

        Deathmatch dm = Warfare44.getWarfare44().getDeathMatch();
        Player killer = event.getCause().first(Player.class).get();
        Player victim = (Player) event.getTargetEntity();
        boolean killeractive = dm.isPlayerActive(killer.getUniqueId());
        boolean victimactive = dm.isPlayerActive(victim.getUniqueId());

        if( killeractive && victimactive) //try to award points
        {
            if (dm.getPlayerMatch(killer.getUniqueId()).isPresent())
            {
                Match m = dm.getPlayerMatch(killer.getUniqueId()).get();
                if(m.getSide2().contains(killer.getUniqueId()))
                {
                    m.setSide1score(m.getSide1score() + 1);
                }
                else
                {
                    m.setSide2score(m.getSide2score() + 1);
                }
            }
        }

    }
}
