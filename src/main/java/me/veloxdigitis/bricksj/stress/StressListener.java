package me.veloxdigitis.bricksj.stress;

import me.veloxdigitis.bricksj.battle.BrickPlayer;

import java.util.List;

public interface StressListener {

    void iteration(int iteration);
    void player(BrickPlayer player);
    void end(List<PlayerStressResult> results);
}
