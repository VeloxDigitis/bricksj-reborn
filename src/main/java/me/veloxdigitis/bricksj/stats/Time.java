package me.veloxdigitis.bricksj.stats;

import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.history.PlayerWithBrick;

import java.util.function.Function;
import java.util.stream.IntStream;

public class Time {

    private final BattleHistory history;

    public Time(BattleHistory history) {
        this.history = history;
    }

    public int getMax(BrickPlayer player) {
        return (int) getTime(player, a -> (double) a.max().orElse(0));
    }

    public int getMin(BrickPlayer player) {
        return (int) getTime(player, a -> (double) a.min().orElse(0));
    }

    public double getAverage(BrickPlayer player) {
        return getTime(player, a -> a.average().orElse(0));
    }

    private double getTime(BrickPlayer player, Function<IntStream, Double> function) {
        return function.apply(
                history.getHistory().stream().
                        filter(m -> m.getPlayer() == player).
                        mapToInt(PlayerWithBrick::getTime));
    }
}
