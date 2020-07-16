package com.github.yimmit.warfare44.commands;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.deathmatch.Deathmatch;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ExitMatchCommand implements CommandExecutor
{
    public static CommandSpec commandSpec() {
        return CommandSpec.builder()
                .description(Text.of("Exit the match you are part of"))
                .permission("warfare44.command.exitMatch")
                .executor(new ExitMatchCommand())
                .arguments().build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if (src instanceof Player)
        {
            Player sender = (Player) src;
            Deathmatch dm = Warfare44.getWarfare44().getDeathMatch();
            if(dm.isPlayerActive(sender.getUniqueId()))
            {
                dm.exitMatch(sender.getUniqueId());
                src.sendMessage(Text.of("Exited match successfully"));
                return CommandResult.success();
            }
            else
            {
                throw new CommandException(Text.of(TextColors.RED, "You aren't in a match"));
            }

        }
        else
        {
            throw new CommandException(Text.of("[W44] This command can only be run by a player!"));
        }
    }
}
