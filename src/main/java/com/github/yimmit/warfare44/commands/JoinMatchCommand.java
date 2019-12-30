package com.github.yimmit.warfare44.commands;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.deathmatch.Deathmatch;
import org.spongepowered.api.Server;
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

import java.util.ArrayList;
import java.util.Optional;

public class JoinMatchCommand implements CommandExecutor
{
    public static CommandSpec commandSpec() {
        return CommandSpec.builder()
                .description(Text.of("Join a team death match"))
                .permission("warfare44.command.joinMatch")
                .executor(new JoinMatchCommand())
                .arguments(
                        GenericArguments.optional(GenericArguments.integer(Text.of("Match number"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("Class")))
                ).build();
    }

        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
        {
            if (src instanceof Player)
            {
                Player sender = (Player) src;
                Deathmatch dm = Warfare44.getWarfare44().getDeathMatch();
                if(!dm.isPlayerActive(sender.getUniqueId()))
                {
                    Optional<String> optclass = args.getOne("Class");
                    String playerclass = "assault";


                    if (optclass.isPresent())
                    {
                        if (Warfare44.getWarfare44().getValidClasses().contains(optclass.get().toLowerCase()))
                        {
                            playerclass = optclass.get().toLowerCase();
                        }
                        else
                        {
                            throw new CommandException(Text.of(TextColors.RED, "Invalid Class name given"));
                        }
                    }


                    Optional<Integer> optmatchnum = args.getOne("Match number");
                    if(optmatchnum.isPresent())
                    {
                        int matchnum = optmatchnum.get();
                        int result = dm.joinMatchByNum(sender.getUniqueId(), matchnum, playerclass);
                        if(result == -1)
                        {
                            throw new CommandException(Text.of(TextColors.RED, "Specified match is full"));
                        }
                        else if(result == 1)
                        {
                            sender.sendMessage(Text.of("Joined match #" + matchnum + " successfully"));
                            return CommandResult.success();
                        }
                    }
                    int resultmatchnum = dm.joinMatch(sender.getUniqueId(), playerclass);
                    if(resultmatchnum > 0)
                    {
                        src.sendMessage(Text.of("Joined match #" + resultmatchnum + " successfully"));
                    }
                    else
                    {
                        throw new CommandException(Text.of(TextColors.RED, "Could not find an open match"));
                    }
                    return CommandResult.success();
                }
                else
                {
                    throw new CommandException(Text.of(TextColors.RED, "You are already in a match"));
                }

            }
            else
            {
                throw new CommandException(Text.of("[W44] This command can only be run by a player!"));
            }
        }
    }
