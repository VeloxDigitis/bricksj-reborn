package me.veloxdigitis.bricksj.champions;

import me.veloxdigitis.bricksj.battle.BrickPlayer;

import java.util.function.Consumer;

public class PlayersPair {

    private BrickPlayer a;
    private BrickPlayer b;

    public PlayersPair(BrickPlayer a, BrickPlayer b) {
        this.a = a;
        this.b = b;
    }

    public BrickPlayer get() {
        return a;
    }

    public BrickPlayer getOponent() {
        return b;
    }

    public void swap() {
        BrickPlayer temp = a;
        this.a = b;
        this.b = temp;
    }

    public void perform(Consumer<BrickPlayer> consumer) {
        consumer.accept(a);
        consumer.accept(b);
    }

    @Override
    public String toString() {
        return a + " vs " + b;
    }
}
