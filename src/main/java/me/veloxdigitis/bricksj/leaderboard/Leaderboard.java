package me.veloxdigitis.bricksj.leaderboard;

import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.stats.Time;

import java.util.List;
import java.util.stream.Collectors;

public class Leaderboard {

    private final List<Stats> players;

    public Leaderboard(List<BattleHistory> history, List<BrickPlayer> players) {
        this.players = players.stream().map(p -> getPlayerStats(p, history)).collect(Collectors.toList());
    }

    private Stats getPlayerStats(BrickPlayer player, List<BattleHistory> history) {
        List<BattleHistory> playerGames = history.stream().
                filter(b -> b.getPlayers().contains(player)).
                collect(Collectors.toList());
        long games = playerGames.size();
        long wins = playerGames.stream().filter(p -> p.getWinner() == player).count();
        long loses = games - wins;

        int max = playerGames.stream().map(Time::new).mapToInt(t -> t.getMax(player)).max().orElse(-1);

        return new Stats(player, wins / (double)games, wins, loses, max);
    }

    public List<Stats> getPlayersWithStats() {
        return players;
    }
}
