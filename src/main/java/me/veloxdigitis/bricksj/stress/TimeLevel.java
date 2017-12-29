package me.veloxdigitis.bricksj.stress;

public enum TimeLevel {

    FAST(10), NORMAL(1000), LONG(10000);

    private int iterations;

    TimeLevel(int iterations) {
        this.iterations = iterations;
    }

    public int getIterations() {
        return iterations;
    }
}
