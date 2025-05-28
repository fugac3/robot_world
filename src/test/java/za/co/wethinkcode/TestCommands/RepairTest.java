package za.co.wethinkcode.TestCommands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import za.co.wethinkcode.robots.commands.RepairCommand;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;
import za.co.wethinkcode.robots.robot.Position;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RepairCommand.
 * </p>
 * These tests verify the behavior of the repair command under different robot states:
 * - When the robot is already repairing
 * - When the robot's shield is at maximum strength
 * - When the robot is eligible to start repairing
 */
public class RepairTest {
    private Robot robot;
    private RepairCommand repairCommand;

    /**
     * Sets up a new Robot and RepairCommand before each test.
     * The robot is initialized with a default type and placed at position (0, 0).
     */
    @BeforeEach
    void setUp() {
        RobotType type = new RobotType("bot", 5, 5, 5);
        robot = new Robot("Testbot", TextWorld.getInstance(), new Position(0, 0), type);
        repairCommand = new RepairCommand();
    }

    /**
     * Tests that executing the repair command while the robot is already repairing
     * returns a FAILED result with the message "Repair in progress".
     */
    @Test
    void testAlreadyRepairing() {
        robot.setIsRepairing(true);
        Response response = repairCommand.execute(robot);
        assertEquals("FAILED", response.getResult());
        assertEquals("Repair in progress", response.getData().get("message"));
    }

    /**
     * Tests that executing the repair command when the robot's shield is already at max strength
     * returns a FAILED result with the message "Shield already at max strength".
     */
    @Test
    void testShieldAtMax() {
        robot.setIsRepairing(false);
        robot.setCurrentShieldStrength(robot.getMaxShieldStrength());
        Response response = repairCommand.execute(robot);
        assertEquals("FAILED", response.getResult());
        assertEquals("Shield already at max strength", response.getData().get("message"));
    }

    /**
     * Tests that executing the repair command when the robot is not repairing and the shield is not at max
     * returns an OK result with the message "Repair started".
     */
    @Test
    void testRepairStarted() {
        robot.setIsRepairing(false);
        robot.setCurrentShieldStrength(robot.getMaxShieldStrength() - 1);
        // Simulate that repair can start
        robot.setCanRepair(true);
        Response response = repairCommand.execute(robot);
        assertEquals("OK", response.getResult());
        assertEquals("Repair started", response.getData().get("message"));
    }
}
