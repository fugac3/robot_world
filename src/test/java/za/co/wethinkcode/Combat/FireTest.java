package za.co.wethinkcode.Combat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import za.co.wethinkcode.robots.commands.FireCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the FireCommand functionality.
 *
 * Verifies correct behavior when firing with and without ammo,
 * ensuring response codes and messages are as expected.
 */
public class FireTest {
    private Robot robot;
    private RobotType type;
    private TextWorld world;

    /**
     * Sets up a controlled environment and robot before each test.
     */
    @BeforeEach
    void setUp() {
        //Create controlled environment
        world = TextWorld.getInstance();
        world.getObstacles().clear();

        //Create a robot at the center
        type = new RobotType("bot", 5, 5, 5);
        robot = new Robot("TestfireBot", world, new Position(0, 0), type);
        world.addRobot(robot);
    }

    /**
     * Tests firing when the robot has ammo.
     * Expects ammo to decrease and a valid response ("Hit" or "Miss").
     */
    @Test
    void testFireCommandWithAmmo() {
        int initialAmmo = robot.getAmmo();
        FireCommand fireCommand = new FireCommand();
        Response response = fireCommand.execute(robot);

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().get("message").toString().matches("Hit|Miss"));
        assertEquals(initialAmmo - 1, robot.getAmmo());
    }

    /**
     * Tests firing when the robot has no ammo.
     * Expects an "ERROR" result and "No ammo" message.
     */
    @Test
    void testFireCommandNoAmmo() {
        while (robot.getAmmo() > 0) {
            int initialAmmo = robot.getAmmo();
            FireCommand fireCommand = new FireCommand();
            fireCommand.execute(robot);
        }

        FireCommand fireCommand = new FireCommand();
        Response response = fireCommand.execute(robot);

        assertEquals("ERROR", response.getResult());
        assertEquals("No ammo", response.getData().get("message"));
    }

    @Test
    void testFireUntilNoAmmo() {
        FireCommand fireCommand = new FireCommand();
        int shots = robot.getAmmo();
        for (int i = 0; i < shots; i++)  {
            Response response = fireCommand.execute(robot);
            assertEquals("OK", response.getResult());
        }
        Response response = fireCommand.execute(robot);
        assertEquals("ERROR", response.getResult());
        assertEquals(0, robot.getAmmo());
    }

    @Test
    void testFireHitsAnotherRobot() {
        Robot target = new Robot("TargetBot", world, new Position(1, 0), type);
        world.addRobot(target);
        robot.setPosition(new Position(0, 0));
        robot.turnRight();
        FireCommand fireCommand = new FireCommand();
        Response response = fireCommand.execute(robot);
        assertEquals("OK", response.getResult());
        assertTrue(target.getCurrentShieldStrength() < target.getMaxShieldStrength());
    }

    @Test
    void testFireDoesNotHitSelf() {
        int initialShield = robot.getCurrentShieldStrength();
        FireCommand fireCommand = new FireCommand();
        fireCommand.execute(robot);
        assertEquals(initialShield, robot.getCurrentShieldStrength());
    }

    @Test
    void testStatusAfterFire() {
        robot.setStatus("BUSY");
        FireCommand fireCommand =  new FireCommand();
        fireCommand.execute(robot);
        assertEquals("NORMAL", robot.getStatus());
    }
}
