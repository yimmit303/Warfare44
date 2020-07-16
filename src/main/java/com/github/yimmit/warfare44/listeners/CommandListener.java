package com.github.yimmit.warfare44.listeners;

import com.github.yimmit.warfare44.Warfare44;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.text.Text;

public class CommandListener
{
    @Listener
    public void onCommandSend(SendCommandEvent event)
    {
        String command = event.getCommand();
        if(command.compareTo("spawn") == 0)
        {
            Player commandplayer = event.getCause().first(Player.class).get();
            if(Warfare44.getWarfare44().getDeathMatch().isPlayerActive(commandplayer.getUniqueId()))
            {
                event.setCommand("exitmatch");
            }
        }
        else if(command.compareTo("warp") == 0)
        {
            Player commandplayer = event.getCause().first(Player.class).get();
            if(Warfare44.getWarfare44().getDeathMatch().isPlayerActive(commandplayer.getUniqueId()))
            {
                commandplayer.sendMessage(Text.of("Exit a match before you try to warp anywhere"));
                event.setArguments("");
                event.setCommand("exitmatch");
            }
        }
    }
}
