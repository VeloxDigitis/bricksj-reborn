package me.veloxdigitis.bricksj.battle.reason;

public class DeadlockReason implements BattleEndReason{
    @Override
    public String getMessage() {
        return "Deadlock protection";
    }
}
