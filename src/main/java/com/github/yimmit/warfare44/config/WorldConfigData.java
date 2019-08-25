package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class WorldConfigData
{
    @Setting
    public SignConfig signdata = new SignConfig();
    @Setting
    public MapConfig mapdata = new MapConfig();

}
