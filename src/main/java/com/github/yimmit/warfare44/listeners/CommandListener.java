package com.github.yimmit.warfare44.listeners;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;

public class CommandListener
{
    @Listener
    public void onCommandSend(SendCommandEvent event)
    {
        if(event.getCommand().compareTo("spawn") == 0)
        {
            event.setCommand("kill");
        }
    }
}
