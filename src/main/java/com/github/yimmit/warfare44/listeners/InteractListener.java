package com.github.yimmit.warfare44.listeners;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;

import java.util.Arrays;

public class InteractListener
{
    @Listener(order = Order.LATE)
    public void onInteractSign(InteractBlockEvent e, @First Player p) {
        BlockSnapshot b = e.getTargetBlock();
        if (b.getState().getType().equals(BlockTypes.WALL_SIGN) || b.getState().getType().equals(BlockTypes.STANDING_SIGN)) {
            if (!b.getLocation().get().hasTileEntity()) return;

            Sign s = (Sign) b.getLocation().get().getTileEntity().get();

            s.offer(Keys.SIGN_LINES, Arrays.asList(Text.of("God fuck"), Text.of("These Fucking"), Text.of("Signs")));
        }
    }

}
