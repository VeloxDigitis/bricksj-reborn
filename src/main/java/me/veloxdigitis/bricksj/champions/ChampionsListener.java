package me.veloxdigitis.bricksj.champions;

import me.veloxdigitis.bricksj.battle.Battle;
import me.veloxdigitis.bricksj.history.BattleHistory;

import java.util.List;

public interface ChampionsListener {

    void start(List<PlayersPair> games);
    void game(Battle game);
    void end(List<BattleHistory> history);
}
