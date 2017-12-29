package me.veloxdigitis.bricksj.map;

import java.util.ArrayList;
import java.util.List;

public class RandomBricks {

    private final List<Brick> bricks = new ArrayList<>();

    public RandomBricks(int randomBricks, int mapSize) {
        for(int i = 0; i < randomBricks; i++)
            bricks.add(Brick.getRandom(mapSize));
    }

    public List<Brick> getBricks() {
        return bricks;
    }
}
