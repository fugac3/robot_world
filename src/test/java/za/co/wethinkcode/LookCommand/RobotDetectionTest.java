package za.co.wethinkcode.LookCommand;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.RobotTypes.RobotType;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.commands.LookCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RobotDetectionTest {
    TextWorld world = TextWorld.getInstance();
    Position robotPos1 = new Position(0, 0);
    Position robotPos2 = new Position(1, 0);
    RobotType type = new RobotType("bot",5,5,5);
    Robot robot1 = new Robot("TestBot1",world,robotPos1,type);
    Robot robot2 = new Robot("TestBot2",world,robotPos2,type);
    LookCommand lookCmd = new LookCommand();

    @Test
    public void testRobotDetection() {
        world.reset(false);
        // Setup world and robot
        world.addRobot(robot1);
        world.addRobot(robot2);

        // Call the check method for obstacles
        List<Map<String, Object>> objects = new ArrayList<>();
        Direction direction = Direction.EAST;
        Position checkPos = new Position(1, 0);
        boolean found = lookCmd.checkForOtherRobot(checkPos, direction, 1, robot1, objects);

        // Assertions
        assertTrue(found, "Robot should be detected");

        Map<String, Object> detected = objects.get(0);
        assertEquals("ROBOT", detected.get("type"));
        assertEquals("EAST", detected.get("direction"));
        assertEquals(1, detected.get("distance"));
    }
}
