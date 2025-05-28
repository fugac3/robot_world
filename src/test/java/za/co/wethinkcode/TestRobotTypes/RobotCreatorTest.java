package za.co.wethinkcode.TestRobotTypes;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robotTypes.*;

import static org.junit.jupiter.api.Assertions.*;

public class RobotCreatorTest {

    @Test
    public void testCreateCannonRobot() {
        RobotType robot = RobotCreator.createRobotType("tank");
        assertNotNull(robot);
        assertTrue(robot instanceof TankRobot);
        assertEquals("Tank", robot.getTypeName());
    }

    @Test
    public void testCreateHeavyRobot() {
        RobotType robot = RobotCreator.createRobotType("heavy");
        assertNotNull(robot);
        assertTrue(robot instanceof HeavyRobot);
        assertEquals("Heavy", robot.getTypeName());
    }

    @Test
    public void testCreateBasicRobot() {
        RobotType robot = RobotCreator.createRobotType("Basic");
        assertNotNull(robot);
        assertTrue(robot instanceof BasicRobot);
        assertEquals("Basic", robot.getTypeName());
    }

    @Test
    public void testCreateSniperRobot() {
        RobotType robot = RobotCreator.createRobotType("Sniper");
        assertNotNull(robot);
        assertTrue(robot instanceof SniperRobot);
        assertEquals("Sniper", robot.getTypeName());
    }

    @Test
    public void testCreateScoutRobot() {
        RobotType robot = RobotCreator.createRobotType("Scout");
        assertNotNull(robot);
        assertTrue(robot instanceof ScoutRobot);
        assertEquals("Scout", robot.getTypeName());
    }

    @Test
    public void testCreateRobotCaseInsensitive() {
        RobotType robot1 = RobotCreator.createRobotType("Basic");
        RobotType robot2 = RobotCreator.createRobotType("scOut");
        RobotType robot3 = RobotCreator.createRobotType("heavY");

        assertEquals("Basic", robot1.getTypeName());
        assertEquals("Scout", robot2.getTypeName());
        assertEquals("Heavy", robot3.getTypeName());
    }

    @Test
    public void testCreateUnknownRobotType() {
        RobotType robot = RobotCreator.createRobotType("unknown");
        assertNull(robot);
    }

}
