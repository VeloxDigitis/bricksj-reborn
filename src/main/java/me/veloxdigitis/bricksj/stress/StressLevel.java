package me.veloxdigitis.bricksj.stress;

public enum StressLevel {

    LOW(99, 200), MEDIUM(501, 500), HIGH(999, 2000);

    private final int mapSize;
    private final int randomBricks;

    StressLevel(int mapSize, int randomBricks) {
        this.mapSize = mapSize;
        this.randomBricks = randomBricks;
    }

    public int getMapSize() {
        return mapSize;
    }

    public int getRandomBricks() {
        return randomBricks;
    }
}
