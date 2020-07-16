package com.github.yimmit.warfare44.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class Configcategory
{
    @Setting(value = "death-match")
    public W44config CONFIG = new W44config();
    @Setting(value = "classes")
    public ClassConfig CLASSES = new ClassConfig();

}
