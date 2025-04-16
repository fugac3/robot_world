package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.maze.EmptyMaze;
import za.co.wethinkcode.maze.Maze;

import static org.junit.jupiter.api.Assertions.*;

class EmptyMazeTest {

    @Test
    void testEmptyMazeIsEmpty() {
        Maze maze = new EmptyMaze();
        assertEquals(0, maze.getObstacles().size());
    }

}
