package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;

@ConfigSerializable
public class MapConfig
{
    @Setting(value = "map-data")
    public ArrayList<MapConfigData> maps = new ArrayList<>();
}
