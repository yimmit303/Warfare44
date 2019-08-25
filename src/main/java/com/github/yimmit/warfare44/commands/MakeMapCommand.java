package com.github.yimmit.warfare44.commands;

import com.github.yimmit.warfare44.Warfare44;
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

public class MakeMapCommand implements CommandExecutor
{
    public static CommandSpec commandSpec() {
        return CommandSpec.builder()
                .description(Text.of("Create a new TDM map"))
                .permission("warfare44.command.createMap")
                .executor(new MakeMapCommand())
                .arguments(
                        GenericArguments.string(Text.of("Map name"))
                ).build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if(src instanceof Player)
        {
            String mapname = args.<String>getOne("Map name").get();

            Player target = (Player)src;

            WorldCategory worlddata = Warfare44.getWarfare44().getWorldData();
            MapConfig mapdatalist = worlddata.mWorldData.mapdata;

            MapConfigData newmapdata = new MapConfigData();
            newmapdata.mMapId = mapdatalist.maps.size();
            newmapdata.mMapName = mapname;

            mapdatalist.maps.add(newmapdata);

            Warfare44.getWarfare44().saveDataConfig();

            src.sendMessage(Text.of("Created map " + mapname + " with ID of " + newmapdata.mMapId));

            return CommandResult.success();
        }
        else
        {
            throw new CommandException(Text.of("[W44] This command can only be run by a player!"));
        }
    }
}
