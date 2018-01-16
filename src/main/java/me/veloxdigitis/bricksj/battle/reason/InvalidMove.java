package me.veloxdigitis.bricksj.battle.reason;

import me.veloxdigitis.bricksj.map.Brick;

public class InvalidMove implements BattleEndReason {

    private Brick brick;

    public InvalidMove(Brick brick) {
        this.brick = brick;
    }

    @Override
    public String getMessage() {
        return "Invalid move on " + brick.toString();
    }

    public Brick getBrick() {
        return brick;
    }
}
