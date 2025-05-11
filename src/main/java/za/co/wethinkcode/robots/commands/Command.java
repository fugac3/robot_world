package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.IWorld;

/**
 * Abstract base class for all robot commands.
 * This class defines the common structure and behaviour for commands that can be executed by robots in the world.
 */

public abstract class Command {
    private String name;
    public String argument;
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

    public String getName() {
        return name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getArgument() {
        return this.argument;
    }

    public void setWorld(IWorld world) {
        this.world = world;
    }

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
                return null;
        }


    }
}

