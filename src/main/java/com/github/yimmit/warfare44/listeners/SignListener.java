package com.github.yimmit.warfare44.listeners;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.config.SignConfig;
import com.github.yimmit.warfare44.util.SignUtil;
import org.slf4j.Logger;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.text.Text;

public class SignListener
{
    @Listener
    public void onSignChange(ChangeSignEvent event)
    {
//        Sign target = event.getTargetTile();
//        ListValue<Text> signtext = event.getText().lines();
//
//        Logger log = Warfare44.getWarfare44().getLogger();
//
//        if(!signtext.get(1).isEmpty() || !signtext.get(2).isEmpty() || !signtext.get(3).isEmpty()) {return;} //This sign has multiple lines therefore not the right format
//
//        String potentialtext = signtext.get(0).toPlain();
//        if(!potentialtext.matches(":Match [1-9][0-9]*:"))
//        {
//            return;
//        }
//        if(!Warfare44.getWarfare44().getSigncoords().signs_in_world.containsKey(target.getWorld().getName())) //If this world has no section in the config add one
//        {
//            Warfare44.getWarfare44().getSigncoords().signs_in_world.put(target.getWorld().getName(), new SignConfig());
//            Warfare44.getWarfare44().saveSignConfig();
//        }
//        SignUtil.setLines(target, Text.of("Line The First"), Text.of("Line The Second"), Text.of("Line 3"), Text.of("Line 4"));
//        SignUtil.registerSign(target, target.getWorld().getName());
//        log.info("Sign registered");
//        SignUtil.updateSigns(target.getWorld().getName());
    }

}
