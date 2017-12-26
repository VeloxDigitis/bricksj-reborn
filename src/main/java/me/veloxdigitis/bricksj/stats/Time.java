package me.veloxdigitis.bricksj.stats;

import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.history.PlayerWithBrick;
import me.veloxdigitis.bricksj.map.Brick;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Time {

    private final BattleHistory history;

    public Time(BattleHistory history) {
        this.history = history;
    }

    public int getMax(BrickPlayer player) {
        return (int) getTime(player, a -> (double) a.max().getAsInt());
    }

    public int getMin(BrickPlayer player) {
        return (int) getTime(player, a -> (double) a.min().getAsInt());
    }

    public double getAverage(BrickPlayer player) {
        return getTime(player, a -> a.average().orElse(-1));
    }

    private double getTime(BrickPlayer player, Function<IntStream, Double> function) {
        return function.apply(
                history.getHistory().stream().
                        filter(m -> m.getPlayer() == player).
                        mapToInt(PlayerWithBrick::getTime));
    }
}
