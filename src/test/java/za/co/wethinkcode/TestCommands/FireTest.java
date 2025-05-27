package za.co.wethinkcode.TestCommands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.FireCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FireTest {
    TextWorld world = TextWorld.getInstance();
    RobotType type = new RobotType("bot",5,5,5);
    Robot robot = new Robot("Reloader", world, new Position(0, 0),type);

    @Test
    void testFireCommandWithAmmo() {
        TextWorld world = TextWorld.getInstance();
        world.getObstacles().clear();
        world.addRobot(robot);

        int initialAmmo = robot.getAmmo();
        FireCommand fireCommand = new FireCommand();
        Response response = fireCommand.execute(robot);

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().get("message").toString().matches("Hit|Miss"));
        assertEquals(initialAmmo - 1, robot.getAmmo());
    }
}
