package za.co.wethinkcode.TestLookCommand;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.commands.LookCommand;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.obstacles.BottomlessPit;
import za.co.wethinkcode.robots.obstacles.LakesObstacle;
import za.co.wethinkcode.robots.obstacles.MountainObstacle;
import za.co.wethinkcode.robots.world.TextWorld;




import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AllObstacleTypeFoundTest {
    TextWorld world = TextWorld.getInstance();
    Position robotPos = new Position(0, 0);
    RobotType type = new RobotType("bot",5,5,5);
    Robot robot = new Robot("TestBot",world,robotPos,type);
    LookCommand lookCmd = new LookCommand();

    @Test
    public void testMountainObstacleDetection() {
        // Setup world and robot
        world.addRobot(robot);

        // Place robot and obstacle
        Position obstaclePos = new Position(1, 0);
        robot.getWorld().getObstacles().clear();
        robot.getWorld().getObstacles().add(new MountainObstacle(obstaclePos.getX(), obstaclePos.getY()));

        // Call the check method for obstacles
        List<Map<String, Object>> objects = new ArrayList<>();
        Direction direction = Direction.EAST;
        Position checkPos = new Position(1, 0);
        boolean blocked = lookCmd.checkForObstacle(checkPos, direction, 1, robot, objects);

        // Assertions
        assertTrue(blocked, "Obstacle mountain should block vision");

        Map<String, Object> detected = objects.getFirst();
        assertEquals("MOUNTAIN", detected.get("type"));
        assertEquals("EAST", detected.get("direction"));
        assertEquals(1, detected.get("distance"));
    }

    @Test
    public void testLakeObstacleDetectionWithExtraObstacleInOppositeDirection() {
        // Setup world and robot
        world.addRobot(robot);

        // Place robot and obstacle
        Position obstaclePos = new Position(1, 0); // directly EAST
        Position obstaclePos2 = new Position(-1, 0); // directly WEST
        robot.getWorld().getObstacles().clear();
        robot.getWorld().getObstacles().add(new LakesObstacle(obstaclePos.getX(), obstaclePos.getY()));
        robot.getWorld().getObstacles().add(new LakesObstacle(obstaclePos2.getX(), obstaclePos2.getY()));

        // Call the check method for obstacles
        List<Map<String, Object>> objects = new ArrayList<>();
        Direction direction = Direction.EAST;
        Position checkPos = new Position(1, 0);
        boolean blocked = lookCmd.checkForObstacle(checkPos, direction, 1, robot, objects);

        // Assertions
        assertFalse(blocked, "Obstacle lake should not block vision");
        assertEquals(1, objects.size(), "One object should be detected");

        Map<String, Object> detected = objects.getFirst();
        assertEquals("LAKE", detected.get("type"));
        assertEquals("EAST", detected.get("direction"));
        assertEquals(1, detected.get("distance"));
    }

    @Test
    public void testPitObstacleDetectionWithExtraObstacleInOppositeDirection() {
        // Add robot to world
        world.addRobot(robot);

        // Place robot and obstacle
        Position obstaclePos = new Position(1, 0); // directly EAST
        Position obstaclePos2 = new Position(-1, 0); // directly WEST
        robot.getWorld().getObstacles().clear();
        robot.getWorld().getObstacles().add(new BottomlessPit(obstaclePos.getX(), obstaclePos.getY()));
        robot.getWorld().getObstacles().add(new MountainObstacle(obstaclePos2.getX(), obstaclePos2.getY()));

        // Call the check method for obstacles
        List<Map<String, Object>> objects = new ArrayList<>();
        Direction direction = Direction.EAST;
        Position checkPos = new Position(1, 0);
        boolean blocked = lookCmd.checkForObstacle(checkPos, direction, 1, robot, objects);

        // Assertions
        assertFalse(blocked, "Obstacle pit should not block vision");
        assertEquals(1, objects.size(), "One object should be detected");

        Map<String, Object> detected = objects.getFirst();
        assertEquals("BOTTOMLESS_PIT", detected.get("type"));
        assertEquals("EAST", detected.get("direction"));
        assertEquals(1, detected.get("distance"));
    }
}
