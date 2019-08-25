package com.github.yimmit.warfare44.commands;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.config.CoordConfigData;
import com.github.yimmit.warfare44.config.MapConfig;
import com.github.yimmit.warfare44.config.MapConfigData;
import com.github.yimmit.warfare44.config.WorldCategory;
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

public class SetMapSpawnCommand implements CommandExecutor
{
    public static CommandSpec commandSpec() {
        return CommandSpec.builder()
                .description(Text.of("Create a new TDM map"))
                .permission("warfare44.command.createMap")
                .executor(new SetMapSpawnCommand())
                .arguments(
                        GenericArguments.integer(Text.of("Map ID")),
                        GenericArguments.integer(Text.of("Side 1 or Side 2?"))
                                ).build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if(src instanceof Player)
        {
            int mapnum = args.<Integer>getOne("Map ID").get();
            int sidenum = args.<Integer>getOne("Side 1 or Side 2?").get();

            if(!(sidenum == 1 || sidenum == 2))
            {
                throw new CommandException(Text.of(TextColors.RED, "The second argument must be 1 or 2"));
            }


            Player target = (Player)src;
            WorldCategory worlddata = Warfare44.getWarfare44().getWorldData();
            if(!worlddata.world_data.containsKey((target.getWorld().getName())))
            {
                throw new CommandException(Text.of(TextColors.RED, "You need to create a map before you can set a spawn on one"));
            }
            MapConfig mapdata = worlddata.world_data.get(target.getWorld().getName()).mapdata;
            if(mapnum > mapdata.maps.size())
            {
                throw new CommandException(Text.of(TextColors.RED, "The map number you passed was too big"));
            }

            MapConfigData map = mapdata.maps.get(mapnum);
            CoordConfigData newcoords = new CoordConfigData();
            newcoords.mX = target.getLocation().getBlockX();
            newcoords.mY = target.getLocation().getBlockY();
            newcoords.mZ = target.getLocation().getBlockZ();

            if(sidenum == 1)
            {
                map.mSide1Spawns.add(newcoords);
            }
            else
            {
                map.mSide2Spawns.add(newcoords);
            }

            target.sendMessage(Text.of("Added spawn to side " + sidenum));

            Warfare44.getWarfare44().saveDataConfig();

            return CommandResult.success();
        }
        else
        {
            throw new CommandException(Text.of("[W44] This command can only be run by a player!"));
        }
    }
}
