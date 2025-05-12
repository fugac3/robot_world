package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * The QuitCommand class represents a command that shuts down the server.
 * When executed, this command returns a response indicating that the server is shutting down.
 */
public class QuitCommand extends Command {

    /**
     * Constructs a new QuitCommand.
     * The "quit" command is used to signal the server shutdown.
     */
    public QuitCommand() {
        super("quit");
    }

    /**
     * Executes the quit command. This method returns a response indicating that the server is shutting down.
     * The actual shutdown logic is likely handled elsewhere in the system.
     *
     * @param robot the robot that triggered the quit command (although the robot doesn't interact with the quit command)
     * @return a {@link Response} object indicating the shutdown message
     */
    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Server shutting down.");

        // Returning a response indicating that the server will exit
        return new Response("EXIT", data, null);
    }
}
