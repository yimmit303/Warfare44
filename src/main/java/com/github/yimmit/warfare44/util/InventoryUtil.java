package com.github.yimmit.warfare44.util;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.model.TDMClass;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

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

    public static void giveItem(UUID id, String itemname, int number)
    {
        if(Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent())
        {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            if(Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemname).isPresent())
            {
                ItemStack item = ItemStack.builder().itemType(Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemname).get()).quantity(number).build();
                player.getInventory().offer(item);
            }
            else
            {
                player.sendMessage(Text.of(TextColors.RED, "Error getting item of name " + itemname + " please report this error to staff or the discord"));
            }
        }
    }

    public static void giveMaxItem(UUID id, String itemname)
    {
        if(Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent())
        {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            if (Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemname).isPresent())
            {
                ItemType item = Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemname).get();
                giveItem(id, itemname, item.getMaxStackQuantity());
            }
            else
            {
                player.sendMessage(Text.of(TextColors.RED, "Error getting item of name " + itemname + " please report this error to staff or the discord"));
            }
        }
    }
}
