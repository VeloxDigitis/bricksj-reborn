package me.veloxdigitis.bricksj.map;

public class InvalidBrick extends Exception {

    private Brick brick;

    public InvalidBrick(Brick brick) {
        super("Invalid brick!");
        this.brick = brick;
    }

    public Brick getBrick() {
        return brick;
    }
}
