package me.veloxdigitis.bricksj.battle;

import me.veloxdigitis.bricksj.battle.reason.BattleEndReason;
import me.veloxdigitis.bricksj.map.Brick;

public interface BattleListener {

    void start(int mapSize, BrickPlayer a, BrickPlayer b);
    void placed(Brick brick);
    void end(BrickPlayer winner, BattleEndReason reason);

}
