package za.co.wethinkcode.robots.commands;

import java.util.List;

public enum Direction {
    NORTH,EAST,SOUTH,WEST;

    private static final List<Direction> ROTATIONAL_DIRECTIONS =
        List.of(NORTH, EAST, SOUTH, WEST);

//    W = index[3] = (3 + 1) % 4 = 0 back to N
    public Direction turnRight() {
        int index = ROTATIONAL_DIRECTIONS.indexOf(this);
        return ROTATIONAL_DIRECTIONS.get((index + 1) % ROTATIONAL_DIRECTIONS.size());
    }

    public Direction turnLeft() {
        int index = ROTATIONAL_DIRECTIONS.indexOf(this);
        return ROTATIONAL_DIRECTIONS.get((index - 1 + ROTATIONAL_DIRECTIONS.size()) % ROTATIONAL_DIRECTIONS.size());
    }
}
