package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class SignConfigData
{
    @Setting(value = "Coordinates")
    public CoordConfigData coords;
    @Setting(value = "match-number")
    public int matchnum = 0;
}