package me.veloxdigitis.bricksj.proxy;

import javafx.scene.paint.Color;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.config.InfoFile;
import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.InvalidBrick;
import me.veloxdigitis.bricksj.timer.TimedOperation;

import java.rmi.MarshalException;
import java.util.List;
import java.util.stream.Collectors;

public class BricksAlgorithm extends StandardIOAlgorithm implements BrickPlayer {

    private final String name;
    private final Color color;

    public BricksAlgorithm(InfoFile infoFile) {
        super(infoFile.getFilePath().getParent(), infoFile.getRunCommand());
        this.name = infoFile.getPlayerName();
        this.color = Color.color(Math.random(), Math.random(), Math.random());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public TimedOperation<Boolean> setMap(int size, List<Brick> bricks) {
        super.run();
        StringBuilder message = new StringBuilder(size + "");
        if(bricks.size() > 0)
            message.append("_").
                    append(bricks.stream().
                            map(Brick::toString).
                            collect(Collectors.joining("_")));
        TimedOperation<Boolean> time = new TimedOperation<>();
        send(message.toString());
        return time.setDataAndStop(read().equals("ok"));
    }

    @Override
    public TimedOperation<Brick> move(Brick brick) throws InvalidBrick {
        return move(brick.toString());
    }

    @Override
    public TimedOperation<Brick> startMove() throws InvalidBrick {
        return move("START");
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void endGame() {
        super.terminate();
    }

    private TimedOperation<Brick> move(String message) throws InvalidBrick {
        TimedOperation<Brick> time = new TimedOperation<>();
        send(message);
        return time.setDataAndStop(Brick.fromString(read()));
    }
}
