package me.veloxdigitis.bricksj.map;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RandomSlabs {

    private final Set<Slab> slabs = new HashSet<>();

    public RandomSlabs(int randomSlabs, int mapSize) {
        for(int i = 0; i < randomSlabs; i++)
            slabs.add(Slab.getRandom(mapSize, mapSize));
    }

    public Collection<Slab> getSlabs() {
        return slabs;
    }

}
