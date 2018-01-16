package me.veloxdigitis.bricksj.battle.reason;

public class OutOfTime implements BattleEndReason {

    private int timeExceeded;

    public OutOfTime(int timeExceeded) {
        this.timeExceeded = timeExceeded;
    }

    @Override
    public String getMessage() {
        return "Time exceeded by " + timeExceeded;
    }
}
