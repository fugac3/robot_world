package za.co.wethinkcode.TestCommands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.LaunchCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.world.TextWorld;

import static org.junit.jupiter.api.Assertions.*;

public class LaunchTest {
    @Test
    public void testCreateLaunchCommand() {
        Command command = Command.create("launch bot Robo");
        assertNotNull(command);
        assertTrue(command instanceof LaunchCommand);
        assertEquals("bot robo", command.getArgument().toLowerCase());
    }

    @Test
    public void testCreateThrowsOnMissingLaunchName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Command.create("launch ");
        });
        assertTrue(exception.getMessage().contains("Launch command needs a name"));
    }
}
