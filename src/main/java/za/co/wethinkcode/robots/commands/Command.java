package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;

public abstract class Command {
    private String name;
    public String argument;

    public abstract boolean execute(Robot target);

    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    public String getName() {                                                                           //<2>
        return name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getArgument() {
        return this.argument;
    }

    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");

        switch (args[0]){
            case "shutdown":
            case "off":
                return new ShutdownCommand();
            case "help":
                return new HelpCommand();
            case "forward":
                return new ForwardCommand(args[1]);
            case "sprint":
                return new SprintCommand(args[1]);
            case "back":
                return new BackCommand(args[1]);
            case "right":
                return new RightCommand();
            case "left":
                return new LeftCommand();
            case "replay":
                if (args.length > 1) {
                    return new ReplayCommand(args[1]);
                } else {
                    return new ReplayCommand();
                }
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}

