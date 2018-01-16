package me.veloxdigitis.bricksj.battle.reason;

public class UnknownReason implements BattleEndReason {
    @Override
    public String getMessage() {
        return "Unknown reason";
    }
}
