package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

public abstract class Command {
    private String name;
    public String argument;

    public abstract Response execute(Robot robot);
//    public abstract boolean execute(Robot target);

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
        if (instruction == null || instruction.isBlank()) {
            throw new IllegalArgumentException("Empty command received.");
        }

//        String[] args = instruction..trim().split(" ",2);
        String[] args = instruction.trim().split("\\s+");

        System.out.println("DEBUG: instruction = '" + instruction + "'");
        System.out.println("DEBUG: args[0] = '" + args[0] + "'");
        if (args.length > 1) {
            System.out.println("DEBUG: args[1] = '" + args[1] + "'");
        }

        switch (args[0]) {
            case "launch":
                if (args.length < 2 || args[1].isBlank()) {
                    throw new IllegalArgumentException("Launch command needs a robot name from cmd class.");
                }
                return new LaunchCommand(args[1]);
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
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }


    }
}

