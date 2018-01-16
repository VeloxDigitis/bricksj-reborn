package me.veloxdigitis.bricksj.battle.reason;

public class NoMoreMoves implements BattleEndReason {

    @Override
    public String getMessage() {
        return "No more moves";
    }

}
