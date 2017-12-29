package me.veloxdigitis.bricksj.history;

import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.champions.PlayersPair;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.InvalidBrick;

import java.util.ArrayList;
import java.util.List;

public class BattleHistory {

    private final PlayersPair players;
    private final int mapSize;
    private final List<Brick> startingBricks;
    private final List<PlayerWithBrick> history;

    public BattleHistory(PlayersPair players, int mapSize, List<Brick> startingBricks) {
        this.players = players;
        this.mapSize = mapSize;
        this.startingBricks = startingBricks;
        this.history = new ArrayList<>();
    }

    public void addToHistory(Brick brick, BrickPlayer player, int time) {
        try {
            this.history.add(new PlayerWithBrick(brick, player, time));
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
        return history.get(history.size() - 1).getPlayer();
    }

    public PlayersPair getPlayers() {
        return players;
    }

    public List<Brick> getStartingBricks() {
        return startingBricks;
    }

    @Override
    public String toString() {
        return players.toString();
    }

}
