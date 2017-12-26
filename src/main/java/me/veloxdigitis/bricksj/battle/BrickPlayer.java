package me.veloxdigitis.bricksj.battle;

import javafx.scene.paint.Color;
import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.InvalidBrick;
import me.veloxdigitis.bricksj.timer.TimedOperation;

import java.util.List;

public interface BrickPlayer {

    TimedOperation<Boolean> setMap(int size, List<Brick> bricks);
    TimedOperation<Brick>   move(Brick brick) throws InvalidBrick;
    TimedOperation<Brick>   startMove() throws InvalidBrick;

    Color getColor();
    void endGame();

}
