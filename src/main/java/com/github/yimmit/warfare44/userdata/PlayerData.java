package com.github.yimmit.warfare44.userdata;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;

@ConfigSerializable
public class PlayerData
{
    @Setting(value = "last-logged")
    public String mLastLogged;    // In the form yyyy/mm/dd
    @Setting(value = "assault-rank")
    public int mAssaultRank = 1;
    @Setting(value = "recon-rank")
    public int mReconRank = 1;
    @Setting(value = "medic-rank")
    public int mMedicRank = 1;
    @Setting(value = "support-rank")
    public int mSupportRank = 1;
    @Setting(value = "engineer-rank")
    public int mEngineerRank = 1;
    @Setting(value = "overall-rank")
    public int mOverallRank = 1;
}
