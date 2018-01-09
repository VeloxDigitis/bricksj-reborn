package me.veloxdigitis.bricksj.champions;

import me.veloxdigitis.bricksj.Speaker;
import me.veloxdigitis.bricksj.battle.Battle;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.logger.BricksLogger;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.map.RandomBricks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Champions extends Speaker<ChampionsListener> implements Runnable {

    private final Map<BrickPlayer, Points> players;
    private final int mapSize;
    private final int randomBricks;
    private final int initTime;
    private final int moveTime;
    private final GameSelector selector;

    private final List<BattleHistory> history = new ArrayList<>();

    public Champions(List<BrickPlayer> players, int mapSize, int randomBricks, int initTime, int moveTime, GameSelector selector, List<ChampionsListener> listeners) {
        super(listeners);
        this.players = players.stream().collect(Collectors.toMap(p -> p, p -> new Points(0)));
        this.mapSize = mapSize;
        this.randomBricks = randomBricks;
        this.initTime = initTime;
        this.moveTime = moveTime;
        this.selector = selector;
    }

    @Override
    public void run() {
        List<PlayersPair> games = selector.getGames();
        invokeListeners(l -> l.start(games));
        games.
            stream().
            map(pair -> new Battle(pair, mapSize,
                    new RandomBricks(randomBricks, mapSize).getBricks(),
                    initTime, moveTime,
                    Collections.singletonList(BricksLogger.getInstance()))).
            forEach(game -> {
                try {
                    invokeListeners(l -> l.game(game));
                    Thread thread = new Thread(game);
                    thread.setDaemon(true);
                    thread.start();
                    thread.join();
                    history.add(game.getHistory());         //Save to history
                    players.get(game.getWinner()).add();    //Add points
                } catch (InterruptedException e) {
                    Logger.error("Interrupt exception");
                }
            });
        invokeListeners(l -> l.end(history));
    }



}
