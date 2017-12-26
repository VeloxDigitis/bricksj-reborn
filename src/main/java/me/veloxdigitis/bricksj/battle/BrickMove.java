package me.veloxdigitis.bricksj.battle;

import me.veloxdigitis.bricksj.map.Brick;

public class BrickMove {

    private Brick brick;
    private int time;
    private final BattleEndReason move;

    public BrickMove(BattleEndReason move) {
        this.move = move;
    }

    public BrickMove(Brick brick, int time) {
        this(BattleEndReason.VALID);
        this.brick = brick;
        this.time = time;
    }

    public Brick getBrick() {
        return brick;
    }

    public int getTime() {
        return time;
    }

    public BattleEndReason getMove() {
        return move;
    }
}
