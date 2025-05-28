package za.co.wethinkcode.TestServerCommands;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.server.ClientHandler;
import za.co.wethinkcode.robots.world.TextWorld;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShutdownCommandTest {
    Position TOP_LEFT = new Position(-5,5);
    Position BOTTOM_RIGHT = new Position(5,-5);
    TextWorld world = TextWorld.getInstance(TOP_LEFT,BOTTOM_RIGHT);
    RobotType type = new RobotType("bot",5,5,5);
    Robot robot = new Robot("TestBot1", world, new Position(0, 0),type);


    @Test
    void testShutdownServer() throws IOException {
        // Mock server socket
        ServerSocket mockServerSocket = Mockito.mock(ServerSocket.class);
        Mockito.when(mockServerSocket.isClosed()).thenReturn(false);

        // Mock client handlers
        ClientHandler handler1 = Mockito.mock(ClientHandler.class);
        ClientHandler handler2 = Mockito.mock(ClientHandler.class);
        List<ClientHandler> handlers = Arrays.asList(handler1, handler2);

        // Clear TextWorld before test to simulate existing robots
        world.addRobot(robot);
        assertFalse(TextWorld.getInstance().getAllRobots().isEmpty(), "Should contain robots before shutdown");

        // Execute shutdown
        ShutdownCommand.shutdownServer(mockServerSocket, handlers);

        // Verify all clients were disconnected
        Mockito.verify(handler1).disconnect();
        Mockito.verify(handler2).disconnect();

        // Verify socket is closed if not already
        Mockito.verify(mockServerSocket).close();

        // Verify all robots are cleared
        assertTrue(TextWorld.getInstance().getAllRobots().isEmpty(), "Should be empty after shutdown");
    }
}
