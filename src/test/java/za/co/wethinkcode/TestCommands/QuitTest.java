package za.co.wethinkcode.TestCommands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.QuitCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.world.TextWorld;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuitTest {
    TextWorld world = TextWorld.getInstance();
    RobotType type = new RobotType("bot",5,5,5);
    Robot robot = new Robot("Reloader", world, new Position(0, 0),type);

    @Test
    public void testCreateQuitCommand() {
        Command command = Command.create("quit");
        assertNotNull(command);
        assertTrue(command instanceof
                QuitCommand);
    }
}
