package me.veloxdigitis.bricksj.stress;

public enum StressLevel {

    LOW(99), MEDIUM(501), HIGH(999);

    private int mapSize;

    StressLevel(int mapSize) {
        this.mapSize = mapSize;
    }

    public int getMapSize() {
        return mapSize;
    }

}
