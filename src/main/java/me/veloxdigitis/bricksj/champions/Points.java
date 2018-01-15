package me.veloxdigitis.bricksj.champions;

class Points {

    private int points;

    public Points(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void add() {
        this.points++;
    }

    @Override
    public String toString() {
        return points + " points";
    }
}
