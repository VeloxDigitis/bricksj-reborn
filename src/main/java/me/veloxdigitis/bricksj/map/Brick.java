package me.veloxdigitis.bricksj.map;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Brick {

    private static final char JOINER = '_';
    private final Slab[] slabs;

    public Brick(Slab a, Slab b) throws InvalidBrick {
        if(!a.isNeighor(b))
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
}
