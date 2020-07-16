package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;

@ConfigSerializable
public class MapConfigData
{
    @Setting(value = "Map id")
    public int mMapId;

    @Setting(value = "Map Name")
    public String mMapName = "";

    @Setting(value = "Side 1 Country")
    public String mSide1Country = "";

    @Setting(value = "Side 2 Country")
    public String mSide2Country = "";

    @Setting(value = "Side 1 spawns")
    public ArrayList<CoordConfigData> mSide1Spawns = new ArrayList<>();

    @Setting(value = "Side 2 spawns")
    public ArrayList<CoordConfigData> mSide2Spawns = new ArrayList<>();


}
