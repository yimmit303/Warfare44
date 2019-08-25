package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class WorldCategory
{
    @Setting(value = "Sign data")
    public SignConfig signdata = new SignConfig();
    @Setting(value = "Map data")
    public MapConfig mapdata = new MapConfig();
}
