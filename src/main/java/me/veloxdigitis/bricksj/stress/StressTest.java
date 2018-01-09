package me.veloxdigitis.bricksj.stress;

import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.InvalidBrick;
import me.veloxdigitis.bricksj.map.RandomBricks;

import java.util.ArrayList;
import java.util.List;

public class StressTest implements Runnable {

    private final List<BrickPlayer> players;
    private final int mapSize;
    private final int iterations;
    private final StressListener listener;
    private final int randomBricks;

    public StressTest(List<BrickPlayer> players, int mapSize, int iterations, int randomBricks, StressListener listener) {
        this.players = players;
        this.mapSize = mapSize;
        this.iterations = iterations;
        this.listener = listener;
        this.randomBricks = randomBricks;
    }

    public StressTest(List<BrickPlayer> players, StressLevel stressLevel, TimeLevel timeLevel, StressListener listener) {
        this(players, stressLevel.getMapSize(), timeLevel.getIterations(), stressLevel.getRandomBricks(), listener);
    }


    @Override
    public void run() {
        List<PlayerStressResult> results = new ArrayList<>();
        players.forEach(p -> results.add(checkPlayer(p)));
        listener.end(results);
    }

    private PlayerStressResult checkPlayer(BrickPlayer player) {
        PlayerStressResult result = new PlayerStressResult(player);
        listener.player(player);
        player.setMap(mapSize, new RandomBricks(randomBricks, mapSize).getBricks());
        try {
            player.startMove();
            for (int i = 0; i < iterations; i++) {
                listener.iteration(i);
                result.registerTime(player.move(Brick.getRandom(mapSize)).getTime());
            }
            player.endGame();
        } catch (InvalidBrick invalidBrick) {
            Logger.error("Invalid move " + player + "!");
        }
        return result;
    }

}
