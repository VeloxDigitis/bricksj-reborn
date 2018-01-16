package me.veloxdigitis.bricksj.logger;

import me.veloxdigitis.bricksj.battle.Battle;
import me.veloxdigitis.bricksj.battle.BattleListener;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.battle.reason.BattleEndReason;
import me.veloxdigitis.bricksj.champions.ChampionsListener;
import me.veloxdigitis.bricksj.champions.PlayersPair;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.map.Brick;

import java.util.List;

public class BricksLogger implements ChampionsListener, BattleListener{

    private static BricksLogger instance;

    public static BricksLogger getInstance() {
        if(instance == null)
            instance = new BricksLogger();
        return instance;
    }

    @Override
    public void start(int mapSize, BrickPlayer a, BrickPlayer b) {
        Logger.info(String.format("Started game: %s vs %s (%dx%d)", a, b, mapSize, mapSize));
    }

    @Override
    public void placed(Brick brick) {
    }

    @Override
    public void end(BrickPlayer winner, BattleEndReason reason) {
        Logger.info(String.format("%s won this game by %s", winner, reason.getMessage()));
    }

    @Override
    public void start(List<PlayersPair> games) {
        Logger.info("Started new champions");
        Logger.info("Games list:");
        games.forEach(g -> Logger.info(g.toString()));
    }

    @Override
    public void game(Battle game) {
    }

    @Override
    public void end(List<BattleHistory> history) {
        Logger.info("Ended champions");
    }

}
