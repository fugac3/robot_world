package za.co.wethinkcode.TestServerCommands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.serverCommands.RobotList;
import za.co.wethinkcode.robots.serverCommands.RobotsCommand;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RobotsCommandTest {
    Position TOP_LEFT = new Position(-5,5);
    Position BOTTOM_RIGHT = new Position(5,-5);
    TextWorld world = TextWorld.getInstance(TOP_LEFT,BOTTOM_RIGHT);
    RobotType type = new RobotType("bot",5,5,5);

    @BeforeEach
    void setUp() {
        // Setup world and robot
        world.clearRobots();
        //Create a robot at the center

    }

    @AfterEach
    void tearDown() {
        // Clean up after test
        world.clearRobots();
    }

    @Test
    void testFormatRobotListWithOneRobot() {
        // Setup a robot
        Robot robot = new Robot("TestBot1", world, new Position(0, 0),type);
        world.addRobot(robot);

        // Retrieve robot info and format it
        List<Map<String, Object>> robotList = RobotList.getAllRobotsInfo();
        String output = RobotsCommand.formatRobotList(robotList);

        // Basic assertions on output content
        assertTrue(output.contains("== All Active Robots =="));
        assertTrue(output.contains("TestBot1"));
        assertTrue(output.contains(robot.getCurrentDirection().toString()));
        assertTrue(output.contains(robot.getStatus()));
        assertTrue(output.contains(String.valueOf(robot.getCurrentShieldStrength())));
    }

    @Test
    void testFormatRobotListWithNoRobots() {
        List<Map<String, Object>> emptyList = RobotList.getAllRobotsInfo();
        String output = RobotsCommand.formatRobotList(emptyList);

        assertEquals("No robots found.", output.trim());
    }
}
