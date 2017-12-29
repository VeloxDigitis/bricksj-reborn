package me.veloxdigitis.bricksj.stress;

import me.veloxdigitis.bricksj.battle.BrickPlayer;

public class PlayerStressResult {

    private BrickPlayer player;
    private int min = Integer.MAX_VALUE, max = 0;

    public PlayerStressResult(BrickPlayer player) {
        this.player = player;
    }

    public BrickPlayer getPlayer() {
        return player;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public double getAverage() {
        return sum / iterations;
    }

    private int sum;
    private int iterations;

    public void registerTime(int time) {
        iterations++;
        sum += time;
        if(time < min)
            min = time;
        if(time > max)
            max = time;
    }

}
