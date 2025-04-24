package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import za.co.wethinkcode.robots.commands.DumpCommand;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.SquareObstacle;
import za.co.wethinkcode.robots.world.TextWorld;

class DumpTest {

    @Test
    void testReturnWorldInfo() {
        TextWorld world = new TextWorld();

        // add obstacles to world
        world.getObstacles().add(new SquareObstacle(5, 5));
        world.getObstacles().add(new SquareObstacle(-10, -10));

        // Create robots and move them
        Robot robot1 = new Robot("Robot1", world);
        robot1.updatePosition(3); // move to (0,3)

        Robot robot2 = new Robot("Robot2", world);
        robot2.updatePosition(2); // move to (0,2)

        DumpCommand dump = new DumpCommand();
        boolean result = dump.execute(robot1);

        assertTrue(result); //make sure it has executed
        //Ensure result mention everything in world
        assertTrue(robot1.getStatus().contains("Robot1"));
        assertTrue(robot1.getStatus().contains("Robot2"));
        assertTrue(robot1.getStatus().contains("(5,5)"));
        assertTrue(robot1.getStatus().contains("(-10,-10)"));
    }
}
