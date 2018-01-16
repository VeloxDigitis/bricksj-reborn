package me.veloxdigitis.bricksj.leaderboard;

import me.veloxdigitis.bricksj.battle.BrickPlayer;

public class Stats {

    private final BrickPlayer player;
    private final double winRatio;
    private final long wins;
    private final long winsByNoMove;
    private final long invalidGames;
    private final long loses;
    private final int maxTime;

    public Stats(BrickPlayer player, double winRatio, long winsByNoMove, long invalidGames, long wins, long loses, int maxTime) {
        this.player = player;
        this.winRatio = winRatio;
        this.winsByNoMove = winsByNoMove;
        this.invalidGames = invalidGames;
        this.wins = wins;
        this.loses = loses;
        this.maxTime = maxTime;
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

    public int getMaxTime() {
        return maxTime;
    }

    public long getWinsByNoMove() {
        return winsByNoMove;
    }

    public long getInvalidGames() {
        return invalidGames;
    }
}
