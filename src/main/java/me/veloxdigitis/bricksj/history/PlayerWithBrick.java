package me.veloxdigitis.bricksj.history;

import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.InvalidBrick;
import me.veloxdigitis.bricksj.battle.BrickPlayer;

public class PlayerWithBrick extends Brick{

    private final BrickPlayer player;
    private int time;

    public PlayerWithBrick(Brick brick, BrickPlayer player, int time) throws InvalidBrick {
        super(brick.getSlabs()[0], brick.getSlabs()[1]);

        this.player = player;
        this.time = time;
    }

    public BrickPlayer getPlayer() {
        return player;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", super.toString(), player.toString());
    }
}
