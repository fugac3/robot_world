package za.co.wethinkcode.TestServerCommands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robots.obstacles.SquareObstacle;
import za.co.wethinkcode.robots.obstacles.LakesObstacle;
import za.co.wethinkcode.robots.obstacles.Obstacle;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.serverCommands.DumpCommand;
import za.co.wethinkcode.robots.world.TextWorld;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DumpCommandTest {

    Position TOP_LEFT = new Position(-5,5);
    Position BOTTOM_RIGHT = new Position(5,-5);
    TextWorld world = TextWorld.getInstance(TOP_LEFT,BOTTOM_RIGHT);

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testDumpWorldStateWithObstacles() {
        Obstacle lake = new LakesObstacle(1, 1);
        world.getObstacles().clear(); // Ensure a clean state
        world.getObstacles().add(lake);

        // Execute the dump command
        DumpCommand.dumpWorldState(world);

        // Capture the output and verify key details
        String output = outputStream.toString().trim();

        assertTrue(output.contains("== Obstacles =="));
        assertTrue(output.contains("LakesObstacle"));
        assertTrue(output.contains("(1,1)"));
        assertTrue(output.contains("(7,5)"));
    }

    @Test
    void testDumpWorldStateWithNoObstacles() {
        TextWorld world = TextWorld.getInstance();
        world.getObstacles().clear();

        DumpCommand.dumpWorldState(world);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("No obstacles present in the world."));
    }
}
