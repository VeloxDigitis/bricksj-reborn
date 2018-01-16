package me.veloxdigitis.bricksj.stress;

import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.InvalidBrick;
import me.veloxdigitis.bricksj.map.ParseException;
import me.veloxdigitis.bricksj.map.RandomSlabs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class StressTest implements Runnable {

    private final List<BrickPlayer> players;
    private final int mapSize;
    private final int iterations;
    private final StressListener listener;
    private final int randomBricks;

    private StressTest(List<BrickPlayer> players, int mapSize, int iterations, int randomBricks, StressListener listener) {
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
        player.setMap(mapSize, new RandomSlabs(randomBricks, mapSize).getSlabs());
        try {
            player.startMove();
            for (int i = 0; i < iterations; i++) {
                listener.iteration(i);
                result.registerTime(player.move(Brick.getRandom(mapSize)).getTime());
            }
            player.endGame();
        } catch (InvalidBrick invalidBrick) {
            Logger.error("Invalid move " + player + "!");
        } catch (TimeoutException e) {
            Logger.error("Out of time " + player + "!");
        } catch (ParseException e) {
            Logger.error("Parse error " + player + "!");
        }
        return result;
    }

}
