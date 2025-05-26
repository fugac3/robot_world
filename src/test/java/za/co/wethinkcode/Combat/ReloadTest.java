package za.co.wethinkcode.Combat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import za.co.wethinkcode.robots.commands.ReloadCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import static org.junit.jupiter.api.Assertions.*;

public class ReloadTest {
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
    void testReloadCommand() {
        world.getObstacles().clear();
        world.addRobot(robot);

        while (robot.getAmmo() > 0) {
            robot.fireCommand();
        }
        assertEquals(0, robot.getAmmo());

        ReloadCommand reloadCommand = new ReloadCommand();
        Response response = reloadCommand.execute(robot);

        assertEquals("OK", response.getResult());
        assertEquals(5, robot.getAmmo());
        assertEquals("RELOAD", robot.getStatus());
    }
}
