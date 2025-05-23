package za.co.wethinkcode.TestObstacles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestObstacleDetection {

    private TextWorld world;
    private Robot testRobot;

    @BeforeEach
    public void setup() {
        world = TextWorld.getInstance();
        world.reset(false); // clears all robots and obstacles

        Position robotPos = new Position(0, 0);
        testRobot = new Robot("TestBot",world,robotPos);
        world.addRobot(testRobot);
    }

    @Test
    public void testObstacleIsDetectedAhead() {
        // Place an obstacle directly in front of the robot
        TestObstacleHelper obstacle = new TestObstacleHelper(1, 0);
        world.getObstacles().add(obstacle);

        Position obstaclePos = new Position(1, 0);

        boolean result = world.blocksPath(testRobot.getPosition(),obstaclePos);
        assertTrue(result, "Obstacle should be detected in the path.");
    }

//    @Test
//    public void testNoObstacleDetected() {
//        // No obstacles in world
//        Position start = new Position(0, 0);
//        Direction direction = Direction.EAST;
//        int distance = 5;
//
//        boolean result = world.checkForObstacle(start, direction, distance, testRobot, new ArrayList<>());
//        assertFalse(result, "No obstacle should be detected.");
//    }
}
