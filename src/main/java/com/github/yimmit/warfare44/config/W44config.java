package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class W44config
{
    @Setting(value = "players-per-match")
    public int players_per_match = 10;
    @Setting(value = "time-limit")
    public int time_limit = 900;
    @Setting(value = "points-to-win")
    public int points_to_win = 100;
}
