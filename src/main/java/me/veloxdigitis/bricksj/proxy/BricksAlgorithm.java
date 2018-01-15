package me.veloxdigitis.bricksj.proxy;

import javafx.scene.paint.Color;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.config.InfoFile;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.InvalidBrick;
import me.veloxdigitis.bricksj.map.Slab;
import me.veloxdigitis.bricksj.timer.TimedOperation;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeoutException;
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
    public TimedOperation<Boolean> setMap(int size, Collection<Slab> slabs) {
        super.run();
        StringBuilder message = new StringBuilder(size + "");
        if(slabs.size() > 0)
            message.append("_").
                    append(slabs.stream().
                            map(Slab::toString).
                            collect(Collectors.joining("_")));
        TimedOperation<Boolean> time = new TimedOperation<>();
        send(message.toString());
        try {
            return time.setDataAndStop(read().equals("ok"));
        } catch (IOException e) {
            return time.setDataAndStop(false);
        } catch (TimeoutException e) {
            Logger.error("Timeout exception!");
            return time.setDataAndStop(false);
        }
    }

    @Override
    public TimedOperation<Brick> move(Brick brick) throws InvalidBrick, TimeoutException {
        return move(brick.toString());
    }

    @Override
    public TimedOperation<Brick> startMove() throws InvalidBrick, TimeoutException {
        return move("START");
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void endGame() {
        send("STOP");
        super.terminate();
    }

    private TimedOperation<Brick> move(String message) throws InvalidBrick, TimeoutException {
        TimedOperation<Brick> time = new TimedOperation<>();
        send(message);
        try {
            return time.setDataAndStop(Brick.fromString(read()));
        } catch (IOException e) {
            time.setDataAndStop(null);
            throw new InvalidBrick();
        } catch (TimeoutException e) {
            Logger.error("Timeout exception!");
            time.setDataAndStop(null);
            throw new TimeoutException();
        }
    }
}
