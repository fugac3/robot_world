package za.co.wethinkcode.TestCommands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.LaunchHelper;

import static org.junit.jupiter.api.Assertions.*;

public class LaunchTest {
    @Test
    public void testCreateLaunchCommand() {
        Command command = Command.create("launch bot Robo");
        assertNotNull(command);
        assertTrue(command instanceof LaunchHelper);
        assertEquals("bot robo", command.getArgument().toLowerCase());
    }

//    @Test
//    public void testCreateThrowsOnMissingLaunchName() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            Command.create("launch ");
//        });
//        assertTrue(exception.getMessage().contains("Launch command needs a name"));
//    }
}
