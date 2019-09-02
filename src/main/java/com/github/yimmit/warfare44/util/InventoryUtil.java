package com.github.yimmit.warfare44.util;

import com.github.yimmit.warfare44.Warfare44;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

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

    public static void giveItem(UUID id)
    {
        if(Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent())
        {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            ItemStack gun;
            String typeid = "flansmod:ak47";
            if(Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, typeid).isPresent())
            {
                player.sendMessage(Text.of("We found item of name " + typeid));
                gun = ItemStack.builder().itemType(Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, typeid).get()).build();
                player.getInventory().offer(gun);
                return;
            }
            player.sendMessage(Text.of("We did not find item of name " + typeid));
        }
    }
}
