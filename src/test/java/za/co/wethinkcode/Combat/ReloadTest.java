package za.co.wethinkcode.Combat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import za.co.wethinkcode.robots.commands.ReloadCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ReloadCommand} functionality.
 *
 * Verifies correct behavior for reloading with empty, full, and dead robots,
 * ensuring response codes, ammo, and status are as expected.
 */
public class ReloadTest {
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
     * Tests the reload command after ammo is depleted.
     * Expects ammo to be restored and status set to "RELOAD".
     */
//    @Test
//    void testReloadCommand() {
//        world.getObstacles().clear();
//        world.addRobot(robot);
//
//        while (robot.getAmmo() > 0) {
//            FireCommand(robot);
//        }
//        assertEquals(0, robot.getAmmo());
//
//        ReloadCommand reloadCommand = new ReloadCommand();
//        Response response = reloadCommand.execute(robot);
//
//        assertEquals("OK", response.getResult());
//        assertEquals(5, robot.getAmmo());
//        assertEquals("RELOAD", robot.getStatus());
//    }

    /**
     * Tests reloading when ammo is already full.
     * Expects ammo to remain at max and status to "RELOAD".
     */
//    @Test
//    void testReloadWhenAmmoFull() {
//        robot.setStatus("NORMAL");
//        ReloadCommand reloadCommand = new ReloadCommand();
//        Response response = reloadCommand.execute(robot);
//
//        assertEquals("FAILED", response.getResult());
//        assertEquals(robot.getMaxAmmo(), robot.getAmmo());
//        assertEquals("RELOAD", robot.getStatus());
//    }

    /**
     * Tests that reloading does not affect the robot's shield.
     */
    @Test
    void testReloadDoesNotAffectShield() {
        int initialShield = robot.getCurrentShieldStrength();

        ReloadCommand reloadCommand = new ReloadCommand();
        reloadCommand.execute(robot);

        assertEquals(initialShield, robot.getCurrentShieldStrength());
    }

    /**
     * Tests multiple reloads in succession.
     * Expects ammo to remain at max and status set to "RELOAD".
     */
//    @Test
//    void testMultipleReloads() {
//        robot.fireCommand();
//        ReloadCommand reloadCommand = new ReloadCommand();
//        reloadCommand.execute(robot);
//        reloadCommand.execute(robot);
//
//        assertEquals(robot.getMaxAmmo(), robot.getAmmo());
//        assertEquals("RELOAD", robot.getStatus());
//    }

    /**
     * Tests reloading when the robot is dead.
     * Expects a "FAILED" result and no change to ammo.
     */
    @Test
    void testReloadWhenDead() {
        robot.setStatus("DEAD");
        ReloadCommand reloadCommand = new ReloadCommand();
        Response response = reloadCommand.execute(robot);

        assertEquals("FAILED", response.getResult());
    }
}
