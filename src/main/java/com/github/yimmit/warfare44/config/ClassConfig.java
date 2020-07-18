package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.HashMap;

@ConfigSerializable
public class ClassConfig
{
    // Used to keep track of what armor and accessories to give a player depending on the country and class
    @Setting(value = "country")
    public HashMap<String, ClassConfigData> mCountryClassList = new HashMap<>(); // In the form { "German" : GermanClassData, ... }

    // Used to see which weapons are available to each class, what rank they need, their cost, and any ammo overrides
    @Setting(value = "weapon-data")
    public HashMap<String, ClassWeaponData> mClassWeaponData = new HashMap<>(); // In the form { "Assault" : AssaultWeaponData, ... }

    // Used to hold a list of all secondary weapon names, since these are available to all classes at all levels no extra data is stored
    @Setting(value = "secondaries-data")
    public ArrayList<String> mSecondariesData = new ArrayList<>(); // In the form [ "Colt", "Luger", ... ]
}
