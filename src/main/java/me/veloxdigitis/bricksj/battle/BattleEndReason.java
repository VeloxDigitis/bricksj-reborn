package me.veloxdigitis.bricksj.battle;

public enum BattleEndReason {

    NO_MOVES("No moves"),
    OUT_OF_TIME("Out of time"),
    UNKNOWN("Unknown reason"),
    INVALID_MOVE("Invalid move!");

    private final String message;

    BattleEndReason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
