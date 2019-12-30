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
    @Setting(value = "head")
    public String mHead;
    @Setting(value = "chest")
    public String mChest;
    @Setting(value = "legs")
    public String mLegs;
    @Setting(value = "boots")
    public String mBoots;
}
