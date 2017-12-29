package me.veloxdigitis.bricksj.champions;

import me.veloxdigitis.bricksj.battle.Battle;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.map.RandomBricks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Champions implements Runnable {

    private final Map<BrickPlayer, Points> players;
    private final int mapSize;
    private final int randomBricks;
    private final int initTime;
    private final int moveTime;
    private final GameSelector selector;
    private final ChampionsListener listener;

    private final List<BattleHistory> history = new ArrayList<>();

    public Champions(List<BrickPlayer> players, int mapSize, int randomBricks, int initTime, int moveTime, GameSelector selector, ChampionsListener listener) {
        this.players = players.stream().collect(Collectors.toMap(p -> p, p -> new Points(0)));
        this.mapSize = mapSize;
        this.randomBricks = randomBricks;
        this.initTime = initTime;
        this.moveTime = moveTime;
        this.selector = selector;
        this.listener = listener;
    }

    @Override
    public void run() {
        List<PlayersPair> games = selector.getGames();
        listener.start(games);
        games.
            stream().
            map(pair -> new Battle(pair, mapSize, new RandomBricks(randomBricks, mapSize).getBricks(), initTime, moveTime, Collections.emptyList())).
            forEach(game -> {
                try {
                    listener.game(game);
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
        listener.end(history);
    }

}
