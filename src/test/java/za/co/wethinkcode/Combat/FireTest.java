package za.co.wethinkcode.Combat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import za.co.wethinkcode.robots.commands.FireCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;
import za.co.wethinkcode.robots.commands.LookCommand;

import static org.junit.jupiter.api.Assertions.*;

public class FireTest {
    private Robot robot;
    private RobotType type;
    private TextWorld world;

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


    @Test
    void testFireCommandWithAmmo() {
        int initialAmmo = robot.getAmmo();
        FireCommand fireCommand = new FireCommand();
        Response response = fireCommand.execute(robot);

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().get("message").toString().matches("Hit|Miss"));
        assertEquals(initialAmmo - 1, robot.getAmmo());
    }

    @Test
    void testFireCommandNoAmmo() {
        while (robot.getAmmo() > 0) {
            int initialAmmo = robot.getAmmo();
            FireCommand fireCommand = new FireCommand();
            fireCommand.execute(robot);
        }

        FireCommand fireCommand = new FireCommand();
        Response response = fireCommand.execute(robot);

        assertEquals("FAILED", response.getResult());
        assertEquals("Miss", response.getData().get("message"));
    }
}
