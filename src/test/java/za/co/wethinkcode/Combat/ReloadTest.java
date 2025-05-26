package za.co.wethinkcode.Combat;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.world.TextWorld;
import za.co.wethinkcode.robots.commands.FireCommand;
import za.co.wethinkcode.robots.commands.ReloadCommand;

public class ReloadTest {
    private Robot robot;
    private TextWorld world;

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
