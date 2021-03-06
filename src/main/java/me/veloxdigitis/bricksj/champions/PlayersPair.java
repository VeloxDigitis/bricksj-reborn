package me.veloxdigitis.bricksj.champions;

import me.veloxdigitis.bricksj.battle.BrickPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PlayersPair {

    private final BrickPlayer a;
    private final BrickPlayer b;

    public PlayersPair(BrickPlayer a, BrickPlayer b) {
        this.a = a;
        this.b = b;
    }

    public BrickPlayer getPlayer() {
        return a;
    }

    public BrickPlayer getOpponent() {
        return b;
    }

    public List<BrickPlayer> get() {
        return Arrays.asList(a, b);
    }

    public PlayersPair swap() {
        return new PlayersPair(b, a);
    }

    public void perform(Consumer<BrickPlayer> consumer) {
        consumer.accept(a);
        consumer.accept(b);
    }

    public List<BrickPlayer> toList() {
        return List.of(a, b);
    }

    @Override
    public String toString() {
        return a + " vs " + b;
    }

    public boolean contains(BrickPlayer player) {
        return a == player || b == player;
    }
}
