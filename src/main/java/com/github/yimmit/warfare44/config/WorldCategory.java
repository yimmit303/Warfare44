package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;

@ConfigSerializable
public class WorldCategory
{
    @Setting(value = "World data")
    public HashMap<String, WorldConfigData> world_data = new HashMap<>();
}
