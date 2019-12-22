package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class ClassConfigGearData
{
    @Setting(value = "primary")
    public String mPrimary;
    @Setting(value = "secondary")
    public String mSecondary;
}
