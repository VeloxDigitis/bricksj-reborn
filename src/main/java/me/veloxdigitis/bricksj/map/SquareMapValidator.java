package me.veloxdigitis.bricksj.map;

import java.util.Arrays;

public class SquareMapValidator implements MapValidator {

    private final boolean map[][];
    private final int size;

    public SquareMapValidator(boolean[][] map) {
        this.map = map;
        this.size = map[0].length;
    }

    @Override
    public boolean isValid(Brick brick) {
        return Arrays.stream(brick.getSlabs()).
                noneMatch(s -> s.getX() < 0 || s.getX() >= size ||
                                s.getY() < 0 || s.getY() >= size ||
                                map[s.getX()][s.getY()]);
    }

    @Override
    public boolean anyMove() {
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(!map[i][j])
                    if( (i < size - 1 && !map[i + 1][j]) ||
                            (j < size - 1 && !map[i][j + 1]))
                        return true;
        return false;
    }

}
