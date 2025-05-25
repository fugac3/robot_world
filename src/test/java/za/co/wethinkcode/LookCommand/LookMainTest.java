package za.co.wethinkcode.LookCommand;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.LookCommand;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.MountainObstacle;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LookMainTest {

    //Create controlled environment

    // Setup world and robot
    Position TOP_LEFT = new Position(-5,5);
    Position BOTTOM_RIGHT = new Position(5,-5);
    TextWorld world = TextWorld.getInstance(TOP_LEFT,BOTTOM_RIGHT);
    LookCommand lookCmd = new LookCommand();

    @Test
    public void testLookCommandExecuteDetectsObstacleAndRobot() {
        world.reset(false);


        Position robotPos = new Position(0, 0);
        Position robot2Pos = new Position(1, 0);
        Position obstaclePos = new Position(2, 0);

        Robot robot = new Robot("Looker", world, robotPos);
        Robot other = new Robot("Target", world, robot2Pos);

        robot.getWorld().setVisibilityConstraint(10);

        world.addRobot(robot);
        world.addRobot(other);
        world.getObstacles().clear();
        world.getObstacles().add(new MountainObstacle(obstaclePos.getX(), obstaclePos.getY()));

        Response responseMap = lookCmd.execute(robot);
        Map<String, Object> data = responseMap.getData();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");

        assertNotNull(objects);
        assertFalse(objects.isEmpty());

        assertNotNull(objects);
        assertEquals(5, objects.size()); // robot and obstacle should be detected

        boolean foundNorthEmpty = objects.stream().anyMatch(obj ->
                obj.get("direction").equals("NORTH") && obj.get("type").equals("EDGE")
        );
        assertTrue(foundNorthEmpty, "Expected an EDGE object in the NORTH direction");

        boolean foundSouthEmpty = objects.stream().anyMatch(obj ->
                obj.get("direction").equals("SOUTH") && obj.get("type").equals("EDGE")
        );
        assertTrue(foundSouthEmpty, "Expected an EDGE object in the SOUTH direction");

        boolean foundWestEmpty = objects.stream().anyMatch(obj ->
                obj.get("direction").equals("WEST") && obj.get("type").equals("EDGE")
        );
        assertTrue(foundWestEmpty, "Expected an EDGE object in the WEST direction");

        long objectCount = objects.stream().filter(obj ->
                obj.get("type").equals("EDGE")
        ).count();

        assertEquals(3, objectCount, "Expected 5 objects");

//        Map<String, Object> first = objects.get(0);
//        assertEquals("ROBOT", first.get("type"));
//        assertEquals("EAST", first.get("direction"));
//
//        Map<String, Object> second = objects.get(1);
//        assertEquals("MOUNTAIN", second.get("type"));
//        assertEquals("EAST", second.get("direction"));

//        Map<String, Object> seen = objects.get(0);
//        assertEquals("ROBOT", seen.get("type"));
//        assertEquals("EAST", seen.get("direction"));
//        assertEquals(1, seen.get("distance"));
    }


}
