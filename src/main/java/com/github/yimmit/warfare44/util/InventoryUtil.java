package com.github.yimmit.warfare44.util;

import com.github.yimmit.warfare44.Warfare44;
import org.spongepowered.api.entity.living.player.Player;

import java.util.UUID;

public class InventoryUtil
{
    public static void clearInventory(UUID id)
    {
        if(Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent())
        {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            player.getInventory().clear();
        }
    }
}
