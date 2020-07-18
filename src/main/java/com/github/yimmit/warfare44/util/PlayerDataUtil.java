package com.github.yimmit.warfare44.util;

import com.github.yimmit.warfare44.Warfare44;
import com.github.yimmit.warfare44.userdata.PlayerData;

import java.util.HashMap;
import java.util.UUID;

import static com.github.yimmit.warfare44.Warfare44.getWarfare44;

public class PlayerDataUtil
{
    public static void initializeNewPlayer(UUID id)
    {
        HashMap<UUID, PlayerData> data = getWarfare44().getUserData().PLAYERLIST;
        PlayerData new_data = new PlayerData();
        new_data.mLastLogged = java.time.LocalDate.now().toString();

        data.put(id, new_data);
        Warfare44.getWarfare44().saveUserData();
    }

    public static void addExperience(UUID id, String class_name, int xp_amount)
    {

    }

}
