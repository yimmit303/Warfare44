package com.github.yimmit.warfare44.commands;

import com.github.yimmit.warfare44.Warfare44;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class ReloadDeathmatchCommand implements CommandExecutor
{
    public static CommandSpec commandSpec() {
        return CommandSpec.builder()
                .description(Text.of("Reload the deathmatch"))
                .permission("warfare44.command.reloadDM")
                .executor(new ReloadDeathmatchCommand())
                .arguments()
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        Warfare44.getWarfare44().getDeathMatch().ejectPlayers();
        Warfare44.getWarfare44().reloadDeathmatch();
        src.sendMessage(Text.of("Reload successful"));
        return CommandResult.success();
    }
}
