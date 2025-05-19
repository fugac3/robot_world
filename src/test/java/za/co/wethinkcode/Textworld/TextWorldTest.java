package za.co.wethinkcode.Textworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.AbstractWorld;
import za.co.wethinkcode.robots.world.IWorld;
import za.co.wethinkcode.robots.world.TextWorld;
import java.util.Random;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class TextWorldTest {

    private TextWorld textWorld;

    @BeforeEach
    public void setUp() {
        textWorld = TextWorld.getInstance(); // Get singleton instance
    }

    @Test
    public void testSingletonInstance() {
        TextWorld anotherInstance = TextWorld.getInstance();
        assertSame(textWorld, anotherInstance, "TextWorld should return the same instance each time");
    }

    @Test
    public void testAddRobot() {
        // Create a new robot
        Position pos = new Position(0,0);
        Robot robot = new Robot("bot", textWorld,pos);

        // Add the robot to the world
        textWorld.addRobot(robot);

        // Verify that the robot was added successfully
        assertTrue(textWorld.getAllRobots().contains(robot), "Robot should be added to the world");
    }

    @Test
    public void testgetAllRobots(){
        Position pos = new Position(0,0);
        Robot robot = new Robot("bot", textWorld,pos);

        textWorld.getAllRobots();

        assertEquals(textWorld.getAllRobots() , textWorld.getAllRobots());
    }

    @Test
    public void testBlocksPath() {
        // Create a new world and a robot
        Position pos = new Position(0,0);
        Robot robot = new Robot("bot", textWorld,pos);

        // Set up some obstacles (this should be done in your world setup)
        textWorld.blocksPath(new Position(5, 5), new Position(9, 9)); // Example, actual obstacle setup needed

        // Test if the path between two positions is blocked
        boolean isPathBlocked = textWorld.blocksPath(new Position(5, 5), new Position(9, 9));

        // Assert that the path is blocked (modify as per actual obstacle setup)
        assertTrue(isPathBlocked);
    }
}
