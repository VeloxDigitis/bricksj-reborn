package me.veloxdigitis.bricksj.battle;

import me.veloxdigitis.bricksj.Speaker;
import me.veloxdigitis.bricksj.champions.PlayersPair;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.map.*;
import me.veloxdigitis.bricksj.timer.TimedOperation;

import java.util.*;
import java.util.concurrent.TimeoutException;

public class Battle extends Speaker<BattleListener> implements Runnable{

    private final PlayersPair players;

    private final int mapSize;
    private final Collection<Slab> startingSlabs;
    private final int initTime;
    private final int moveTime;
    private boolean map[][];

    private final BattleHistory history;

    private BrickPlayer winner;

    public Battle(PlayersPair players, int mapSize, Collection<Slab> startingSlabs, int initTime, int moveTime, List<BattleListener> listeners) {
        super(listeners);
        this.players = players;
        this.mapSize = mapSize;
        this.startingSlabs = startingSlabs;
        this.initTime = initTime;
        this.moveTime = moveTime;
        this.history = new BattleHistory(players, mapSize, startingSlabs);
        this.winner = players.getPlayer();
    }

    @Override
    public void run() {
        PlayersPair players = this.players;

        Optional<BrickPlayer> mapPlayer = initMap();
        if(mapPlayer.isPresent()) {
            end(BattleEndReason.OUT_OF_TIME, players.getPlayer() == mapPlayer.get() ? players.getOpponent() : players.getPlayer());
            return;
        }

        MapValidator validator = new SquareMapValidator(map);
        Brick lastMove = null;

        while(validator.anyMove()) {
            BrickMove move = move(players.getPlayer(), lastMove, validator);
            if(move.getMove() == BattleEndReason.UNKNOWN) {
                history.addToHistory(move.getBrick(), players.getPlayer(), move.getTime());
                players = players.swap();
                lastMove = move.getBrick();
                if(validator.isValid(lastMove)) {
                    put(lastMove);
                    Brick finalLastMove = lastMove;
                    invokeListeners(l -> l.placed(finalLastMove));
                }
            } else {
                end(move.getMove(), players.getOpponent());
                return;
            }
        }

        end(BattleEndReason.NO_MOVES, players.getOpponent());
    }

    public BrickPlayer getWinner() {
        return winner;
    }

    public BattleHistory getHistory() {
        return history;
    }

    private void end(BattleEndReason reason, BrickPlayer winner) {
        this.winner = winner;
        history.end(reason, winner);
        players.perform(BrickPlayer::endGame);
        invokeListeners(l -> l.end(players.getPlayer(), reason));
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
        } catch (TimeoutException e) {
            return new BrickMove(BattleEndReason.OUT_OF_TIME);
        }
    }

    private Optional<BrickPlayer> initMap() {
        this.map = new boolean[mapSize][mapSize];
        startingSlabs.forEach(this::put);

        return players.get().stream().filter(a -> initMap(a, mapSize, startingSlabs)).findFirst();
    }

    private boolean initMap(BrickPlayer player, int mapSize, Collection<Slab> startingSlabs) {
        TimedOperation<Boolean> operation = player.setMap(mapSize, startingSlabs);
        return !operation.getData() || operation.getTime() > initTime;
    }

    private void put(Brick b) {
        Arrays.stream(b.getSlabs()).forEach(this::put);
    }

    private void put(Slab s) {
       map[s.getX()][s.getY()] = true;
    }

    @Override
    public String toString() {
        return players.toString();
    }

}
