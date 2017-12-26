package me.veloxdigitis.bricksj.map;

public class Slab {

    private static final char JOINER = 'x';
    private final int x, y;

    public Slab(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isNeighbor(Slab slab) {
        return Math.abs(slab.x - x) == 1 || Math.abs(slab.y - y) == 1;
    }

    public static Slab fromString(String message) {
        String[] elements = message.split(JOINER + "");
        return new Slab(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]));
    }

    @Override
    public String toString() {
        return String.format("%d%c%d", x, JOINER, y);
    }
}
