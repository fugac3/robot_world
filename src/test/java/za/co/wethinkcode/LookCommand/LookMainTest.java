package za.co.wethinkcode.LookCommand;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.RobotTypes.RobotType;
import za.co.wethinkcode.robots.commands.LookCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.Obstacles.MountainObstacle;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LookMainTest {

    //Create controlled environment

    // Setup world and robot
    Position TOP_LEFT = new Position(-5,5);
    Position BOTTOM_RIGHT = new Position(5,-5);
    TextWorld world = TextWorld.getInstance(TOP_LEFT,BOTTOM_RIGHT);
    RobotType type = new RobotType("bot",5,5,5);
    LookCommand lookCmd = new LookCommand();

    @Test
    public void testLookCommandExecuteDetectsObstacleAndRobot() {
        world.reset(false);


        Position robotPos = new Position(0, 0);
        Position robot2Pos = new Position(1, 0);
        Position obstaclePos = new Position(2, 0);

        Robot robot = new Robot("Looker", world, robotPos,type);
        Robot other = new Robot("Target", world, robot2Pos,type);

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

        long edgeCount = objects.stream().filter(obj ->
                obj.get("type").equals("EDGE")).count();

        long robotCount = objects.stream().filter(obj ->
                obj.get("type").equals("ROBOT")).count();

        long mountainObstacleCount = objects.stream().filter(obj ->
                obj.get("type").equals("MOUNTAIN")).count();

        assertEquals(3, edgeCount, "Expected 3 edges");

        assertEquals(1, robotCount, "Expected 1 robot");

        assertEquals(1, mountainObstacleCount, "Expected 1 mountain obstacle");
    }


}
