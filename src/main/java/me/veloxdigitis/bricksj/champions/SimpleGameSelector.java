package me.veloxdigitis.bricksj.champions;

import me.veloxdigitis.bricksj.battle.BrickPlayer;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleGameSelector implements GameSelector{

    private final List<BrickPlayer> players;

    public SimpleGameSelector(List<BrickPlayer> players) {
        this.players = players;
    }

    @Override
    public List<PlayersPair> getGames() {
        return this.players.stream().
                map(p -> new AbstractMap.SimpleEntry<>(p,
                        this.players.stream().filter(o -> o != p).
                                map(o -> new PlayersPair(p, o)).
                                collect(Collectors.toList()))).
                flatMap(p -> p.getValue().stream()).
                collect(Collectors.toList());
    }

}
