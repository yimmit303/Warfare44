package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashSet;

@ConfigSerializable
public class SignConfig
{
    @Setting(value = "sign-locations")
    public HashSet<SignConfigData> mSignData = new HashSet<>();
}
