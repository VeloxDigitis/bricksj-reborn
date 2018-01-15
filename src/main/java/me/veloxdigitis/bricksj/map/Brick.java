package me.veloxdigitis.bricksj.map;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Brick {

    private static final char JOINER = '_';
    private final Slab[] slabs;

    protected Brick(Slab a, Slab b) throws InvalidBrick {
        if(!a.isNeighbor(b))
            throw new InvalidBrick();

        this.slabs = new Slab[]{a, b};
    }

    public Slab[] getSlabs() {
        return slabs;
    }

    @Override
    public String toString() {
        return Arrays.stream(slabs).
                map(Slab::toString).
                collect(Collectors.joining(JOINER + ""));
    }

    public static Brick fromString(String message) throws InvalidBrick {
        String[] elements = message.split(JOINER + "");
        return new Brick(Slab.fromString(elements[0]), Slab.fromString(elements[1]));
    }

    private static final Random random = new Random();

    public static Brick getRandom(int mapSize) {
        Slab main = Slab.getRandom(mapSize, mapSize);
        try {
            return new Brick(main, random.nextBoolean() ?
                    new Slab(main.getX(), main.getY() - 1) :
                    new Slab(main.getX() - 1, main.getY())
            );
        } catch (InvalidBrick invalidBrick) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Brick && Arrays.equals(slabs, ((Brick) o).slabs);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(slabs);
    }
}
