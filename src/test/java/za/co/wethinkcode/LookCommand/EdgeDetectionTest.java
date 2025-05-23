package za.co.wethinkcode.LookCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.commands.LookCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.MountainObstacle;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EdgeDetectionTest {

    //Create controlled environment

    // Setup world and robot
    Position TOP_LEFT = new Position(-5,5);
    Position BOTTOM_RIGHT = new Position(5,-5);
    TextWorld world = TextWorld.getInstance(TOP_LEFT,BOTTOM_RIGHT);
    LookCommand lookCmd = new LookCommand();

    //Create a robot at the center
    Robot robot = new Robot("TestBot", world, new Position(0, 0));

    @Test
    public void testEdgeDetection() {
        world.addRobot(robot);
        // Call the check method
        List<Map<String, Object>> objects = new ArrayList<>();
        Direction direction = Direction.EAST;
        Position checkPos = new Position(6, 0);
        boolean blocked = lookCmd.checkForEdge(checkPos, direction, 6, objects);

        // Assertions
        assertTrue(blocked, "Edge should be detected.");

        Map<String, Object> detected = objects.getFirst();
        assertEquals("EDGE", detected.get("type"));
        assertEquals("EAST", detected.get("direction"));
        assertEquals(6, detected.get("distance"));
    }

    //Edge of world overrides obstacle when ontop of each other.
    @Test
    public void testDetectObstacleAtEdge() {
        world.getObstacles().clear();

        // Place robot and obstacle
        Position obstaclePos = new Position(6, 0);
        robot.getWorld().getObstacles().clear();
        robot.getWorld().getObstacles().add(new MountainObstacle(obstaclePos.getX(), obstaclePos.getY()));

        // Call the check method
        List<Map<String, Object>> objects = new ArrayList<>();
        Direction direction = Direction.EAST;
        Position checkPos = new Position(6, 0);
        boolean blocked = lookCmd.checkForEdge(checkPos, direction, 6, objects);

        // Assertions
        assertTrue(blocked, "Edge should be detected.");

        Map<String, Object> detected = objects.getFirst();
        assertEquals("EDGE", detected.get("type"));
        assertEquals("EAST", detected.get("direction"));
        assertEquals(6, detected.get("distance"));
    }
}
