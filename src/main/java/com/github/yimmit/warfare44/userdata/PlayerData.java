package com.github.yimmit.warfare44.userdata;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;

@ConfigSerializable
public class PlayerData
{
    @Setting(value = "last-logged")
    String mLastLogged = "";    // In the form yyyy/mm/dd
}
