package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.IWorld;

/**
 * Abstract base class for all robot commands.
 * This class defines the common structure and behaviour for commands that can be executed by robots in the world.
 */

public abstract class Command {
    /** The name of the command, typically used for identification. */
    private String name;

    /** The argument or parameter for the command (e.g., number of steps, robot name). */
    public String argument;

    /** The world context in which the command is executed. */
    private IWorld world;

    /**
     * Executes the command on the specified robot.
     *
     * @param robot The robot that will execute the command
     * @return A response indicating the result of the command execution
     */
    public abstract Response execute(Robot robot);

    /**
     * Creates a new command with the specified name and no arguments.
     *
     * @param name The name of the command
     */
    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    /**
     * Creates a new command with the specified name and argument.
     *
     * @param name The name of the command
     * @param argument The argument for the command
     */
    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    /**
     * Returns the name of the command.
     *
     * @return the name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the command.
     *
     * @param newName the new name for the command
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Returns the argument associated with the command.
     *
     * @return the command's argument
     */
    public String getArgument() {
        return this.argument;
    }

    /**
     * Sets the world context for this command.
     *
     * @param world the world in which this command will be executed
     */
    public void setWorld(IWorld world) {
        this.world = world;
    }

    /**
     * Returns the world context in which this command is executed.
     *
     * @return the world associated with this command
     */
    public IWorld getWorld() {
        return world;
    }

    /**
     * Factory method to create a command from an instruction string.
     *
     * @param instruction The instruction string (e.g., "forward 10")
     * @return A command object corresponding to the instruction, or null if the command is not recognized
     * @throws IllegalArgumentException If the instruction is empty or invalid
     */
    public static Command create(String instruction) {
        if (instruction == null || instruction.isBlank()) {
            throw new IllegalArgumentException("Empty command received.");
        }

        String[] args = instruction.trim().split("\\s+");

        // Switch-case to create different commands based on the instruction
        switch (args[0]) {
            case "launch":
                if (args.length < 2 || args[1].isBlank()) {
                    throw new IllegalArgumentException("Launch command needs a name.");
                }
                return new LaunchCommand(args[1].trim());

            case "robots":
                return new RobotsCommand();

            case "quit":
                return new QuitCommand();

            case "forward":
                if (args.length < 2 || args[1].isBlank()) {
                    throw new IllegalArgumentException("Forward command needs steps.");
                }
                return new ForwardCommand(args[1]);

            case "back":
                if (args.length < 2 || args[1].isBlank()) {
                    throw new IllegalArgumentException("Back command needs steps.");
                }
                return new BackCommand(args[1]);

            case "right":
                return new RightCommand();

            case "left":
                return new LeftCommand();

            case "fire":
                return new FireCommand();

            case "reload":
                return new ReloadCommand();

            case "dump":
                return new DumpCommand();

            case "look":
                return new LookCommand();

            case "state":
                return new StateCommand();

            default:
                return null;  // Unknown command
        }
    }
}
