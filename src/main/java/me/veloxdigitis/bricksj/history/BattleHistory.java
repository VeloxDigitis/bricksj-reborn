package me.veloxdigitis.bricksj.history;

import me.veloxdigitis.bricksj.battle.BattleEndReason;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.champions.PlayersPair;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.InvalidBrick;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BattleHistory {

    private final PlayersPair players;
    private final int mapSize;
    private final List<Brick> startingBricks;
    private final List<PlayerWithBrick> history;
    private BattleEndReason reason = BattleEndReason.UNKNOWN;
    private BrickPlayer winner;


    public BattleHistory(PlayersPair players, int mapSize, List<Brick> startingBricks) {
        this.players = players;
        this.mapSize = mapSize;
        this.startingBricks = startingBricks;
        this.history = new ArrayList<>();
    }

    public void addToHistory(Brick brick, BrickPlayer player, int time) {
        try {
            this.history.add(new PlayerWithBrick(brick, player, time));
            this.winner = player;
        } catch (InvalidBrick invalidBrick) {
            Logger.error("Couldn't add " + brick + " to history (invalid move)");
        }
    }

    public List<PlayerWithBrick> getHistory() {
        return history;
    }

    public int getMapSize() {
        return mapSize;
    }

    public double length() {
        return history.size();
    }

    public BrickPlayer getWinner() {
        return winner;
    }

    public PlayersPair getPlayers() {
        return players;
    }

    public List<Brick> getStartingBricks() {
        return startingBricks;
    }

    public void end(BattleEndReason reason, BrickPlayer winner) {
        this.reason = reason;
        this.winner = winner;
    }

    public BattleEndReason getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return players.toString();
    }
}
