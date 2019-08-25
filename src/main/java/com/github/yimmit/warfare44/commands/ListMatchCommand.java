package com.github.yimmit.warfare44.commands;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.deathmatch.Deathmatch;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;

public class ListMatchCommand implements CommandExecutor
{
    public static CommandSpec commandSpec() {
        return CommandSpec.builder()
                .description(Text.of("List the matches currently going on"))
                .permission("warfare44.command.listMatch")
                .executor(new ListMatchCommand())
                .arguments().build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if(src instanceof Player || src instanceof ConsoleSource)
        {
            Deathmatch dm = Warfare44.getWarfare44().getDeathMatch();

            if(dm.getMatchNum() == 0)
            {
                src.sendMessage(Text.of("There are no matches"));
                return CommandResult.success();
            }

            ArrayList<String> statuslist = dm.getAllMatchStatus();

            for(int i = 0; i < dm.getMatchNum(); i++)
            {
                Text match = Text.builder(statuslist.remove(0))
                        .color(TextColors.DARK_GREEN)
                        .onClick(TextActions.runCommand("/joinmatch " + (i+1)))
                        .onHover(TextActions.showText(Text.of("Join this match")))
                        .build();
                src.sendMessage(match);                                                         //Match #

                if(statuslist.get(0).equalsIgnoreCase("true"))                      //inprogress TRUE
                {
                    Text inprogress =
                            Text.builder(statuslist.remove(0)).append(
                            Text.builder(statuslist.remove(0)).color(TextColors.AQUA).build()
                    ).build();
                    src.sendMessage(inprogress);
                }
                else                                                                            //inprogress FALSE
                {
                    Text inprogress =
                            Text.builder(statuslist.remove(0)).append(
                            Text.builder(statuslist.remove(0)).color(TextColors.RED).build()
                    ).build();
                    src.sendMessage(inprogress);
                }

                src.sendMessage(Text.of(statuslist.remove(0)));                           //timer

                Text playercount =
                        Text.builder(statuslist.remove(0)).append(
                                Text.builder(statuslist.remove(0) + " ").color(TextColors.DARK_RED).append(
                                        Text.builder(statuslist.remove(0)).color(TextColors.DARK_BLUE).build()
                                ).build()
                        ).build();
                src.sendMessage(playercount);

                Text score =
                        Text.builder(statuslist.remove(0)).append(
                                Text.builder(statuslist.remove(0) + " ").color(TextColors.DARK_RED).append(
                                        Text.builder(statuslist.remove(0)).color(TextColors.DARK_BLUE).build()
                                ).build()
                        ).build();
                src.sendMessage(score);
            }
            src.sendMessage(Text.of("----------------------"));
            return CommandResult.success();
        }
        else
        {
            throw new CommandException(Text.of("[W44] This command can only be run by a Console or Player"));
        }
    }
}