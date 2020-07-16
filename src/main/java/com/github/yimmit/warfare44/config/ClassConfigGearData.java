package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class ClassConfigGearData
{
    @Setting(value = "primary")
    public String mPrimary;
    @Setting(value = "primaryammo")
    public String mPrimaryAmmoOverride = "";
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

    // Modulus gear
    @Setting(value = "upperface")
    public String mUpperface = "";
    @Setting(value = "lowerface")
    public String mLowerface = "";
    @Setting(value = "gloves")
    public String mGloves = "";
    @Setting(value = "arms")
    public String mArms = "";
    @Setting(value = "accessory")
    public String mAccessory = "";
    @Setting(value = "backpack")
    public String mBackpack = "";
    @Setting(value = "vest")
    public String mVest = "";

}
