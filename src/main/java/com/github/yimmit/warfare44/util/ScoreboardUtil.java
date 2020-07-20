package com.github.yimmit.warfare44.util;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class ScoreboardUtil {
    private static Objective deaths;
    private static Objective kills;
    private static Scoreboard scoreboard;

    public static void initialize() {
        Scoreboard.Builder builder = Scoreboard.builder();
        deaths = Objective.builder().displayName(Text.of("Deaths")).name("deaths").criterion(Criteria.DEATHS).build();
        kills = Objective.builder().displayName(Text.of("Kills")).name("kills").criterion(Criteria.PLAYER_KILLS).build();
        scoreboard = builder.objectives(new ArrayList<>(Arrays.asList(deaths, kills))).build();
        scoreboard.updateDisplaySlot(deaths, DisplaySlots.LIST);
        scoreboard.updateDisplaySlot(kills, DisplaySlots.SIDEBAR);
    }

    public static Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static void resetDeaths() {
        deaths.getScores().clear();
    }

    public static void resetDeaths(Player player) {
        deaths.removeScore(Text.of(player.getName()));
    }

    public void resetKills() {
        kills.getScores().clear();
    }

    public static void resetKills(Player player) {
        kills.removeScore(Text.of(player.getName()));
    }
}
