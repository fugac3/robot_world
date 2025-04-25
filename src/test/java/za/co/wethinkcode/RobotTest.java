package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.*;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.TextWorld;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @Test
    void testInitialPosition() {
        TextWorld world = new TextWorld();
        Robot robot = new Robot("CrashTestDummy", world);
        assertEquals(Robot.CENTRE, robot.getPosition());
        assertEquals(Direction.NORTH, robot.getCurrentDirection());
        assertEquals("CrashTestDummy", robot.getName());
        assertEquals(world, robot.getWorld());
        assertEquals("Ready", robot.getResponse());
    }

    @Test
    void testUpdatePosition() {
        TextWorld world = new TextWorld();
        Robot robot = new Robot("CrashTestDummy", world);

        boolean result = robot.updatePosition(10);
        assertTrue(result); //movement should be successful
        assertEquals(new Position(0, 10), robot.getPosition());
        assertEquals("Moved forward by 10 steps.", robot.getStatus());
    }

    @Test
    void testUpdatePositionFail() {
        TextWorld world = new TextWorld();
        Robot robot = new Robot("CrashTestDummy", world);

        boolean result = robot.updatePosition(1000);
        assertFalse(result); //movement should fail/out of bounds
        assertEquals(new Position(0, 0), robot.getPosition());
        assertEquals("Sorry, I cannot go outside my safe zone.", robot.getResponse());
    }

    @Test
    void testTurns() {
        TextWorld world = new TextWorld();
        Robot robot = new Robot("CrashTestDummy", world);

        robot.turnLeft();
        assertEquals(Direction.WEST, robot.getCurrentDirection());
        robot.turnRight();
        assertEquals(Direction.NORTH, robot.getCurrentDirection());
        robot.turnRight();
        assertEquals(Direction.EAST, robot.getCurrentDirection());
    }

    @Test
    void dump() {
        TextWorld world = new TextWorld();
        Robot robot = new Robot("CrashTestDummy", world);
        assertEquals("[0,0] CrashTestDummy> Ready", robot.toString());
    }

    @Test
    void quit() { //all robots should disconnect not one!
        TextWorld world = new TextWorld();
        Robot robot = new Robot("CrashTestDummy", world);
        ShutdownCommand command = new ShutdownCommand();
        assertFalse(robot.handleCommand(command));
    }
}