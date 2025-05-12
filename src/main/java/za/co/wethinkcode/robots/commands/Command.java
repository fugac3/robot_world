package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.IWorld;

/**
 * Abstract class that represents a command in the robot simulation.
 * All specific commands (e.g., launching a robot, moving it, firing, etc.)
 * will extend this class and implement the `execute()` method.
 */
public abstract class Command {
    /** The name of the command, typically used for identification. */
    private String name;

    /** The argument or parameter for the command (e.g., number of steps, robot name). */
    public String argument;

    /** The world context in which the command is executed. */
    private IWorld world;

    /**
     * Executes the command on the given robot.
     *
     * @param robot the {@link Robot} on which the command is executed
     * @return the result of executing the command in the form of a {@link Response}
     */
    public abstract Response execute(Robot robot);

    /**
     * Constructs a new command with the given name.
     *
     * @param name the name of the command (e.g., "forward", "launch")
     */
    public Command(String name) {
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    /**
     * Constructs a new command with the given name and argument.
     *
     * @param name the name of the command (e.g., "forward", "launch")
     * @param argument the argument for the command (e.g., number of steps)
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
     * Creates a command instance based on the input instruction string.
     * The instruction is parsed and the corresponding command is returned.
     *
     * @param instruction the instruction string to parse (e.g., "launch robotName")
     * @return the created {@link Command} instance
     * @throws IllegalArgumentException if the instruction is invalid or incomplete
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
