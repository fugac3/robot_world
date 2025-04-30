package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import za.co.wethinkcode.robots.commands.RobotsCommand;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.TextWorld;

class ListRobotsTest {

    @Test
    void testReturnWorldInfo() {
        TextWorld world = new TextWorld();

        Robot robot1 = new Robot("Robot1", world);
        robot1.updatePosition(2); // move robot1 to (0,2)
        Robot robot2 = new Robot("Robot2", world);

        RobotsCommand listRobots = new RobotsCommand();
        boolean result = listRobots.execute(robot1);

        assertTrue(result); //make sure it has executed
        assertTrue(robot1.getResponse().contains("Robot1"));
        assertTrue(robot1.getResponse().contains("Robot2"));
    }
}
