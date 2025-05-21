package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.ForwardCommand;
import za.co.wethinkcode.robots.commands.LaunchCommand;
import za.co.wethinkcode.robots.commands.QuitCommand;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.TextWorld;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    @Test
    public void testCreateLaunchCommand() {
        Command command = Command.create("launch Robo");
        assertNotNull(command);
        assertTrue(command instanceof LaunchCommand);
        assertEquals("robo", command.getArgument().toLowerCase());
    }

    @Test
    public void testCreateForwardCommand() {
        Command command = Command.create("forward 10");
        assertNotNull(command);
        assertTrue(command instanceof ForwardCommand);
        assertEquals("10", command.getArgument());
    }

    @Test
    public void testCreateQuitCommand() {
        Command command = Command.create("quit");
        assertNotNull(command);
        assertTrue(command instanceof
                QuitCommand);
    }

    @Test
    public void testInvalidCommandReturnsNull() {
        Command command = Command.create("dance 5");
        assertNull(command);
    }

    @Test
    public void testCreateThrowsOnEmptySteps() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Command.create("forward ");
        });
        assertEquals("Could not parse arguments: Steps cannot be null.", exception.getMessage());
    }

    @Test
    public void testCreateThrowsOnMissingLaunchName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Command.create("launch ");
        });
        assertTrue(exception.getMessage().contains("Launch command needs a name"));
    }




}
