package com.github.yimmit.warfare44.util;

import com.github.yimmit.warfare44.Warfare44;
import io.modworks.modulus.Modulus;
import io.modworks.modulus.api.MArmorType;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import io.modworks.modulus.api.ArmorApi;

import java.util.UUID;

public class InventoryUtil {
    public static void clearInventory(UUID id) {
        if (Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent()) {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            player.getInventory().clear();
        }
    }

    public static ItemStack getItemStackByName(String itemName) {
        return ItemStack.builder().itemType(Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).get()).build();
    }

    public static void giveItem(UUID id, String itemName, int number) {
        if (Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent()) {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            if (Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).isPresent()) {
                ItemStack item = ItemStack.builder().itemType(Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).get()).quantity(number).build();
                player.getInventory().offer(item);
            } else {
                player.sendMessage(Text.of(TextColors.RED, "Error getting item of name " + itemName + " please report this error to staff or the discord"));
            }
        }
    }

    public static void giveMaxItem(UUID id, String itemName) {
        if (Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent()) {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            if (Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).isPresent()) {
                ItemType item = Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).get();
                giveItem(id, itemName, item.getMaxStackQuantity());
            } else {
                player.sendMessage(Text.of(TextColors.RED, "Error getting item of name " + itemName + " please report this error to staff or the discord"));
            }
        }
    }

    public static void giveHead(UUID id, String itemName) {
        if (Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent()) {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            if (Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).isPresent()) {
                ItemType item = Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).get();
                player.setHelmet(ItemStack.of(item));
            } else {
                player.sendMessage(Text.of(TextColors.RED, "Error getting item of name " + itemName + " please report this error to staff or the discord"));
            }
        }
    }

    public static void giveChest(UUID id, String itemName) {
        if (Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent()) {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            if (Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).isPresent()) {
                ItemType item = Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).get();
                player.setChestplate(ItemStack.of(item));
            } else {
                player.sendMessage(Text.of(TextColors.RED, "Error getting item of name " + itemName + " please report this error to staff or the discord"));
            }
        }
    }

    public static void giveLegs(UUID id, String itemName) {
        if (Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent()) {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            if (Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).isPresent()) {
                ItemType item = Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).get();
                player.setLeggings(ItemStack.of(item));
            } else {
                player.sendMessage(Text.of(TextColors.RED, "Error getting item of name " + itemName + " please report this error to staff or the discord"));
            }
        }
    }

    public static void giveBoots(UUID id, String itemName) {
        if (Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent()) {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            if (Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).isPresent()) {
                ItemType item = Warfare44.getWarfare44().getGame().getRegistry().getType(ItemType.class, itemName).get();
                player.setBoots(ItemStack.of(item));
            } else {
                player.sendMessage(Text.of(TextColors.RED, "Error getting item of name " + itemName + " please report this error to staff or the discord"));
            }
        }
    }

    public static void giveAccessory(UUID id, String itemName, String accessorySlot) {
//        ItemStack.of(Modulus.specialArmorTypes.get("cf.platevest"), 1);
//        ArmorApi.setArmorInSlot();
//        ArmorApi.setArmorInSlot((EntityPlayer) player, MArmorType.Vest.getFirstSlot(), new net.minecraft.item.ItemStack(Modulus.specialArmorTypes.get("cf.platevest")));
        if (Warfare44.getWarfare44().getGame().getServer().getPlayer(id).isPresent()) {
            Player player = Warfare44.getWarfare44().getGame().getServer().getPlayer(id).get();
            MArmorType type = MArmorType.valueOf(accessorySlot);
            ArmorApi.setArmorInSlot((EntityPlayer) player, type.getFirstSlot(), new net.minecraft.item.ItemStack(Modulus.specialArmorTypes.get(itemName)));
        }
    }
}
