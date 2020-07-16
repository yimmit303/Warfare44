package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;

@ConfigSerializable
public class ClassConfigData
{
    @Setting(value = "classes")
    public HashMap<String, ClassConfigGearData> mClassList = new HashMap<>(); // In the form {"Assault" : AssaultData, "Recon" : ReconData ...}


}
