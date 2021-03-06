package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class CoordConfigData
{
    @Setting("X")
    public int mX;

    @Setting("Y")
    public int mY;

    @Setting("Z")
    public int mZ;

    @Setting("Rot X")
    public double mRotX;

    @Setting("Rot Y")
    public double mRotY;

    @Setting("Rot Z")
    public double mRotZ;
}
