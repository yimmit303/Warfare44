package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;

@ConfigSerializable
public class ClassConfig
{
    @Setting(value = "country")
    public HashMap<String, ClassConfigData> mCountryClassList = new HashMap<>(); // In the form { "Germany" : GermanClassData ...}

}
