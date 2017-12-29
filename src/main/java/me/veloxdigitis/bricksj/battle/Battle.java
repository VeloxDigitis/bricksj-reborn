package me.veloxdigitis.bricksj.battle;

import me.veloxdigitis.bricksj.champions.PlayersPair;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.InvalidBrick;
import me.veloxdigitis.bricksj.map.MapValidator;
import me.veloxdigitis.bricksj.map.SquareMapValidator;
import me.veloxdigitis.bricksj.timer.TimedOperation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Battle implements Runnable{

    private final PlayersPair players;

    private final int mapSize;
    private final List<Brick> startingBricks;
    private final int initTime;
    private final int moveTime;
    private boolean map[][];

    private final BattleHistory history;

    private final List<BattleListener> listeners;

    public Battle(PlayersPair players, int mapSize, List<Brick> startingBricks, int initTime, int moveTime, List<BattleListener> listeners) {
        this.players = players;
        this.mapSize = mapSize;
        this.startingBricks = startingBricks;
        this.initTime = initTime;
        this.moveTime = moveTime;
        this.listeners = listeners;
        this.history = new BattleHistory(players, mapSize, startingBricks);
    }

    @Override
    public void run() {
        Optional<BrickPlayer> mapPlayer = initMap();
        if(mapPlayer.isPresent()) {
            if(players.get() != mapPlayer.get())
                players.swap();
            end(BattleEndReason.OUT_OF_TIME);
            return;
        }

        MapValidator validator = new SquareMapValidator(map);
        Brick lastMove = null;

        while(validator.anyMove()) {
            BrickMove move = move(players.get(), lastMove, validator);
            history.addToHistory(move.getBrick(), players.get(), move.getTime());
            players.swap();
            if(move.getMove() == BattleEndReason.VALID) {
                lastMove = move.getBrick();
                if(validator.isValid(lastMove)) {
                    put(lastMove);
                    Brick finalLastMove = lastMove;
                    invokeListeners(l -> l.placed(finalLastMove));
                }
            } else {
                end(move.getMove());
                return;
            }
        }

        end(BattleEndReason.NO_MOVES);
    }

    public BrickPlayer getWinner() {
        return players.get();
    }

    public BattleHistory getHistory() {
        return history;
    }

    private void end(BattleEndReason reason) {
        players.perform(BrickPlayer::endGame);
        invokeListeners(l -> l.end(players.get(), reason));
    }

    private void invokeListeners(Consumer<BattleListener> consumer) {
        listeners.forEach(consumer);
    }

    private BrickMove move(BrickPlayer player, Brick lastMove, MapValidator validator) {
        try {
            TimedOperation<Brick> timedBrick = lastMove == null ? player.startMove() : player.move(lastMove);
            if(timedBrick.getTime() > moveTime)
                return new BrickMove(BattleEndReason.OUT_OF_TIME);
            else if(validator.isValid(timedBrick.getData()))
                return new BrickMove(timedBrick.getData(), timedBrick.getTime());
            else
                return new BrickMove(BattleEndReason.INVALID_MOVE);
        } catch (InvalidBrick invalidBrick) {
            return new BrickMove(BattleEndReason.INVALID_MOVE);
        }
    }

    private Optional<BrickPlayer> initMap() {
        this.map = new boolean[mapSize][mapSize];
        startingBricks.forEach(this::put);

        if(players.get().setMap(mapSize, startingBricks).getTime() > initTime)
            return Optional.of(players.getOpponent());
        if(players.getOpponent().setMap(mapSize, startingBricks).getTime() > initTime)
            return Optional.of(players.get());
        return Optional.empty();
    }

    private void put(Brick b) {
        Arrays.stream(b.getSlabs()).
                forEach(s -> map[s.getX()][s.getY()] = true);
    }

    @Override
    public String toString() {
        return players.toString();
    }

}
