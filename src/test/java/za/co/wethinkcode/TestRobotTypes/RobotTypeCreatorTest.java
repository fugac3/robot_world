package za.co.wethinkcode.TestRobotTypes;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robotTypes.*;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTypeCreatorTest {

    @Test
    public void testCreateCannonRobot() {
        RobotType robot = RobotTypeCreator.createRobotType("cannon");
        assertNotNull(robot);
        assertTrue(robot instanceof CannonRobot);
        assertEquals("Cannon", robot.getTypeName());
    }

    @Test
    public void testCreateBunkerRobot() {
        RobotType robot = RobotTypeCreator.createRobotType("bunker");
        assertNotNull(robot);
        assertTrue(robot instanceof BunkerRobot);
        assertEquals("Bunker", robot.getTypeName());
    }

    @Test
    public void testCreateStormcallerRobot() {
        RobotType robot = RobotTypeCreator.createRobotType("stormcaller");
        assertNotNull(robot);
        assertTrue(robot instanceof StormcallerRobot);
        assertEquals("Stormcaller", robot.getTypeName());
    }

    @Test
    public void testCreateWaspRobot() {
        RobotType robot = RobotTypeCreator.createRobotType("wasp");
        assertNotNull(robot);
        assertTrue(robot instanceof WaspRobot);
        assertEquals("Wasp", robot.getTypeName());
    }

    @Test
    public void testCreateWhiplashRobot() {
        RobotType robot = RobotTypeCreator.createRobotType("whiplash");
        assertNotNull(robot);
        assertTrue(robot instanceof WhiplashRobot);
        assertEquals("Whiplash", robot.getTypeName());
    }

    @Test
    public void testCreateRobotCaseInsensitive() {
        RobotType robot1 = RobotTypeCreator.createRobotType("CANNON");
        RobotType robot2 = RobotTypeCreator.createRobotType("Cannon");
        RobotType robot3 = RobotTypeCreator.createRobotType("cannon");

        assertEquals("Cannon", robot1.getTypeName());
        assertEquals("Cannon", robot2.getTypeName());
        assertEquals("Cannon", robot3.getTypeName());
    }

    @Test
    public void testCreateUnknownRobotType() {
        RobotType robot = RobotTypeCreator.createRobotType("unknown");
        assertNull(robot);
    }

}
