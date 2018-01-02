package me.veloxdigitis.bricksj.leaderboard;

import me.veloxdigitis.bricksj.battle.BrickPlayer;

public class Stats {

    private final BrickPlayer player;
    private final double winRatio;
    private final long wins;
    private final long loses;

    public Stats(BrickPlayer player, double winRatio, long wins, long loses) {
        this.player = player;
        this.winRatio = winRatio;
        this.wins = wins;
        this.loses = loses;
    }

    public BrickPlayer getPlayer() {
        return player;
    }

    public double getWinRatio() {
        return winRatio;
    }

    public long getWins() {
        return wins;
    }

    public long getLoses() {
        return loses;
    }
}
