package com.github.yimmit.warfare44.util;

import com.github.yimmit.warfare44.Warfare44;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MenuUtil
{
    private int mMatchNumber;
    private MenuType mOpenInventory;

    public enum MenuType {
        CLASS, RANK, SHOP
    }

    public Inventory createClassMenu(int num)
    {
        mMatchNumber = num;
        mOpenInventory = MenuType.CLASS;
        Inventory inv =  Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
                        .property(InventoryTitle.of(Text.of("")))
                        .listener(ClickInventoryEvent.class, this::processClick).build(Warfare44.getWarfare44());
        populateInventory(inv);
        return inv;
    }

    private void populateInventory(Inventory inv)
    {
        HashMap<Integer, ItemStack> contents = setInventoryContents(new HashMap<>());
        for (int slot : contents.keySet())
        {
            inv.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(slot))).first().set(contents.get(slot));
        }
    }

    private HashMap<Integer, ItemStack> setInventoryContents(HashMap<Integer, ItemStack> contents)
    {
        ArrayList<ItemStack> classprimaries = getCountryPrimaries();
        for(int i = 0; i < classprimaries.size(); i++)
        {
            contents.put(i, classprimaries.get(i));
        }
        return contents;
    }

    private ArrayList<ItemStack> getCountryPrimaries()
    {
        ArrayList<ItemStack> primaries = new ArrayList<>();
        for (String dmclass : Warfare44.getWarfare44().getValidClasses())
        {
            ItemStack primary = InventoryUtil.getItemStackByName("modulus:w44." + Warfare44.getWarfare44().getConfig().CLASSES.mCountryClassList.get("American").mClassList.get(dmclass).mPrimary);
            String dmuppercase = dmclass.substring(0, 1).toUpperCase() + dmclass.substring(1);
            primary.offer(Keys.DISPLAY_NAME, Text.of(dmuppercase));
            primary.setQuantity(128);
            primaries.add(primary);
        }
        return primaries;
    }

    private void processClick(ClickInventoryEvent event)
    {
        List<Slot> slots = event.getTransactions().stream()
                .map(SlotTransaction::getSlot)
                .filter(s -> s.getInventoryProperty(SlotIndex.class).filter(i -> i.getValue() < 54).isPresent())
                .collect(Collectors.toList());
        event.setCancelled(true);
        if (!slots.isEmpty()) {
            event.getCause().first(Player.class).ifPresent(p -> slots.forEach(s -> s.getInventoryProperty(SlotIndex.class)
                    .ifPresent(i -> onSlotClick(p, event, i.getValue()))));
        }
    }

    public void onSlotClick(Player player, ClickInventoryEvent event, int slot)
    {
        Task.builder().execute(t -> {
            if(event instanceof ClickInventoryEvent.Primary)
            {
                if (mMatchNumber == -1)
                {
                    mMatchNumber = Warfare44.getWarfare44().getDeathMatch().getFirstOpenMatch();
                }
                Game game = Warfare44.getWarfare44().getGame();
                switch (slot)
                {
                    case 0:
                        game.getCommandManager().process(player, "join " + mMatchNumber + " assault");
                        break;
                    case 1:
                        game.getCommandManager().process(player, "join " + mMatchNumber + " recon");
                        break;
                    case 2:
                        game.getCommandManager().process(player, "join " + mMatchNumber + " medic");
                        break;
                    case 3:
                        game.getCommandManager().process(player, "join " + mMatchNumber + " support");
                        break;
                    case 4:
                        game.getCommandManager().process(player, "join " + mMatchNumber + " engineer");
                        break;
                }
            }
        }).submit(Warfare44.getWarfare44());
    }

    public void openInventory(Player player, Inventory inv)
    {
        Task.builder().execute(t -> {
            player.openInventory(inv);
        }).submit(Warfare44.getWarfare44());
    }
}
