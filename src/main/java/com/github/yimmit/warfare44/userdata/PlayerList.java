package com.github.yimmit.warfare44.userdata;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@ConfigSerializable
public class PlayerList
{
    @Setting(value = "player-list")
    public HashMap<UUID, PlayerList> PLAYERLIST = new HashMap<>();
}
