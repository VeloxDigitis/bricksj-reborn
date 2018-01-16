package me.veloxdigitis.bricksj.battle;

import javafx.scene.paint.Color;
import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.InvalidBrick;
import me.veloxdigitis.bricksj.map.ParseException;
import me.veloxdigitis.bricksj.map.Slab;
import me.veloxdigitis.bricksj.timer.TimedOperation;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

public interface BrickPlayer {

    TimedOperation<Boolean> setMap(int size, Collection<Slab> slabs);
    TimedOperation<Brick>   move(Brick brick) throws ParseException, InvalidBrick, TimeoutException;
    TimedOperation<Brick>   startMove() throws ParseException, InvalidBrick, TimeoutException;

    Color getColor();
    void endGame();

}
