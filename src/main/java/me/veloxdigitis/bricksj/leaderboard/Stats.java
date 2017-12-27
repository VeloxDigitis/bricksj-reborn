package me.veloxdigitis.bricksj.leaderboard;

public class Stats {

    private String playerName;
    private final double winRatio;
    private final long wins;
    private final long loses;

    public Stats(String playerName, double winRatio, long wins, long loses) {
        this.playerName = playerName;
        this.winRatio = winRatio;
        this.wins = wins;
        this.loses = loses;
    }

    public String getPlayerName() {
        return playerName;
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
