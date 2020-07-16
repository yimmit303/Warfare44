package com.github.yimmit.warfare44.util;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.config.SignConfig;
import com.github.yimmit.warfare44.config.SignConfigData;
import com.github.yimmit.warfare44.config.WorldCategory;
import org.slf4j.Logger;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class SignUtil
{
    public static void registerSign(Sign sign, String worldname)
    {
        //TODO: Completly rewrite
//        WorldCategory worlddata = Warfare44.getWarfare44().getWorldData();
//        SignConfig signdatalist = worlddata.world_data.get(worldname).signdata;
//        SignConfigData newsigndata = new SignConfigData();
//
//        newsigndata.coords.mX = sign.getLocation().getBlockX();
//        newsigndata.coords.mY = sign.getLocation().getBlockY();
//        newsigndata.coords.mZ = sign.getLocation().getBlockZ();
//
//        Logger log = Warfare44.getWarfare44().getLogger();
//        log.info("X: " + newsigndata.coords.mX + " Y: " + newsigndata.coords.mY + " Z: " + newsigndata.coords.mZ);
//
//        coordlist.coords.add(newsigndata);
//        signcoords.signs_in_world.put(worldname, coordlist);
//        log.info(coordlist.toString());
//        Warfare44.getWarfare44().saveDataConfig();
    }

    public static void updateSigns(String worldname)
    {

        //TODO: Update
//        WorldCategory signcoords = Warfare44.getWarfare44().getSigncoords();
//        SignConfig coordlist = signcoords.signs_in_world.get(worldname);
//
//        if(!Warfare44.getWarfare44().getGame().getServer().getWorld(worldname).isPresent()){return;}
//
//        World world = Warfare44.getWarfare44().getGame().getServer().getWorld(worldname).get();
//        Logger log = Warfare44.getWarfare44().getLogger();
//
//        for(SignConfigData signdata : coordlist.coords)
//        {
//            log.info("X: " + signdata.mX + " Y: " + signdata.mY + " Z: " + signdata.mZ);
//            Location<World> location = world.getLocation(signdata.mX, signdata.mY, signdata.mZ);
//            if(location.hasTileEntity())
//            {
//                log.info("This location has a tile entity");
//                if(location.getTileEntity().isPresent())
//                {
//                    log.info("There is a tile entity present");
//                    Sign target = (Sign)location.getTileEntity().get();
//                    log.info("This is the text on the first line of the sign");
//                    log.info(target.lines().get(0).toPlain());
//                    setLines(location.getTileEntity().get(), Text.of("Line The First"), Text.of("Line The Second"), Text.of("Line 3"), Text.of("Line 4"));
//
//                }
//            }
//
//        }


    }

    public static boolean setLines(TileEntity entity, Text line0, Text line1, Text line2, Text line3) {
        SignData sign = entity.get(SignData.class).get();
        if (line0!=null) sign = sign.set(sign.getValue(Keys.SIGN_LINES).get().set(0, line0));
        if (line1!=null) sign = sign.set(sign.getValue(Keys.SIGN_LINES).get().set(1, line1));
        if (line2!=null) sign = sign.set(sign.getValue(Keys.SIGN_LINES).get().set(2, line2));
        if (line3!=null) sign = sign.set(sign.getValue(Keys.SIGN_LINES).get().set(3, line3));
        entity.offer(sign);
        return true;
    }
}
