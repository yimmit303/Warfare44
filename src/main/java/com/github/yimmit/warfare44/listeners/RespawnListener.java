package com.github.yimmit.warfare44.listeners;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.deathmatch.Deathmatch;
import com.github.yimmit.warfare44.model.Match;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.scheduler.Task;

public class RespawnListener
{
    @Listener
    public void onRespawn(RespawnPlayerEvent event)
    {
        Player victim = event.getTargetEntity();
        Deathmatch dm = Warfare44.getWarfare44().getDeathMatch();

        if(dm.isPlayerActive(victim.getUniqueId()))
        {
            if(dm.getPlayerMatch(victim.getUniqueId()).isPresent())
            {
                Match m = dm.getPlayerMatch(victim.getUniqueId()).get();
                event.setToTransform(m.spawnPlayer(victim.getUniqueId()));
                Task.builder().execute(t -> {
                    m.supply(victim.getUniqueId(), m.getPlayerClass(victim.getUniqueId()));
                }).submit(Warfare44.getWarfare44());
            }
        }

    }
}
