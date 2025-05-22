package za.co.wethinkcode.LookCommand;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.LookCommand;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.world.BottomlessPit;
import za.co.wethinkcode.robots.world.LakesObstacle;
import za.co.wethinkcode.robots.world.MountainObstacle;
import za.co.wethinkcode.robots.world.TextWorld;




import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AllObstacleTypeFoundTest {

    @Test
    public void testMountainObstacleDetection() {
        // Setup world and robot
        TextWorld world = TextWorld.getInstance();
        world.reset(false);
        Position robotPos = new Position(0, 0);
        Robot robot = new Robot("TestBot",world,robotPos);
        world.addRobot(robot);
        LookCommand lookCmd = new LookCommand();


        // Place robot and obstacle
        Position obstaclePos = new Position(1, 0);
        robot.getWorld().getObstacles().clear();
        robot.getWorld().getObstacles().add(new MountainObstacle(obstaclePos.getX(), obstaclePos.getY()));

        // Call the check method
        List<Map<String, Object>> objects = new ArrayList<>();
        Direction direction = Direction.EAST;
        Position checkPos = new Position(1, 0);
        boolean blocked = lookCmd.checkForObstacle(checkPos, direction, 1, robot, objects);

        // Assertions
        assertTrue(blocked, "Obstacle should block vision");

        Map<String, Object> detected = objects.getFirst();
        assertEquals("MOUNTAIN", detected.get("type"));
        assertEquals("EAST", detected.get("direction"));
        assertEquals(1, detected.get("distance"));
    }

    @Test
    public void testLakeObstacleDetectionWithExtraObstacleInOppositeDirection() {
        // Setup world and robot
        TextWorld world = TextWorld.getInstance();
        world.reset(false);
        Position robotPos = new Position(0, 0);
        Robot robot = new Robot("TestBot",world,robotPos);
        world.addRobot(robot);
        LookCommand lookCmd = new LookCommand();


        // Place robot and obstacle
        Position obstaclePos = new Position(1, 0); // directly EAST
        Position obstaclePos2 = new Position(-1, 0); // directly WEST
        robot.getWorld().getObstacles().clear();
        robot.getWorld().getObstacles().add(new LakesObstacle(obstaclePos.getX(), obstaclePos.getY()));
        robot.getWorld().getObstacles().add(new LakesObstacle(obstaclePos2.getX(), obstaclePos2.getY()));

        // Call the check method
        List<Map<String, Object>> objects = new ArrayList<>();
        Direction direction = Direction.EAST;
        Position checkPos = new Position(1, 0);
        boolean blocked = lookCmd.checkForObstacle(checkPos, direction, 1, robot, objects);

        // Assertions
        assertFalse(blocked, "Obstacle should not block vision");
        assertEquals(1, objects.size(), "One object should be detected");

        Map<String, Object> detected = objects.getFirst();
        assertEquals("LAKE", detected.get("type"));
        assertEquals("EAST", detected.get("direction"));
        assertEquals(1, detected.get("distance"));
    }

    @Test
    public void testPitObstacleDetectionWithExtraObstacleInOppositeDirection() {
        // Setup world and robot
        TextWorld world = TextWorld.getInstance();
        world.reset(false);
        Position robotPos = new Position(0, 0);
        Robot robot = new Robot("TestBot",world,robotPos);
        world.addRobot(robot);
        LookCommand lookCmd = new LookCommand();


        // Place robot and obstacle
        Position obstaclePos = new Position(1, 0); // directly EAST
        Position obstaclePos2 = new Position(-1, 0); // directly WEST
        robot.getWorld().getObstacles().clear();
        robot.getWorld().getObstacles().add(new BottomlessPit(obstaclePos.getX(), obstaclePos.getY()));
        robot.getWorld().getObstacles().add(new BottomlessPit(obstaclePos2.getX(), obstaclePos2.getY()));

        // Call the check method
        List<Map<String, Object>> objects = new ArrayList<>();
        Direction direction = Direction.EAST;
        Position checkPos = new Position(1, 0);
        boolean blocked = lookCmd.checkForObstacle(checkPos, direction, 1, robot, objects);

        // Assertions
        assertFalse(blocked, "Obstacle should not block vision");
        assertEquals(1, objects.size(), "One object should be detected");

        Map<String, Object> detected = objects.getFirst();
        assertEquals("BOTTOMLESS_PIT", detected.get("type"));
        assertEquals("EAST", detected.get("direction"));
        assertEquals(1, detected.get("distance"));
    }
}
