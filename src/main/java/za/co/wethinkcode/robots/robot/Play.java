package za.co.wethinkcode.robots.robot;

import za.co.wethinkcode.robots.commands.Command;

import java.util.Scanner;



//DONT TOUCH........



public class Play {
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Robot robot1, robot2;

        // Create the first robot
        String name1 = getInput("What do you want to name your first robot?");
        robot1 = new Robot(name1);
        System.out.println("Hello Kiddo! Your First Bot: " + robot1.getName());

        // Create the second robot
        String name2 = getInput("What do you want to name your second robot?");
        robot2 = new Robot(name2);
        System.out.println("Hello Kiddo! Your Second Bot: " + robot2.getName());

        // Show obstacles in the world
        robot1.getWorld().showObstacles();

        Command command;
        boolean shouldContinue = true;
        do {
            // Get instructions for bot1
            String instructionForBot1 = getInput(robot1.getName() + "> What must I do next?").strip().toLowerCase();
            // Get instructions for bot2
            String instructionForBot2 = getInput(robot2.getName() + "> What must I do next?").strip().toLowerCase();

            try {
                // Execute the instructions for bot1
                command = Command.create(instructionForBot1);
                shouldContinue = robot1.handleCommand(command);

                // Execute the instructions for bot2
                command = Command.create(instructionForBot2);
                shouldContinue = robot2.handleCommand(command);

            } catch (IllegalArgumentException e) {
                robot1.setStatus("Sorry, I did not understand instructions from '" + robot1.getName() + "': " + instructionForBot1);
                robot2.setStatus("Sorry, I did not understand instructions from '" + robot2.getName() + "': " + instructionForBot2);
            }

            // Print the status of both robots after processing commands
            System.out.println(robot1);
            System.out.println(robot2);

        } while (shouldContinue);
    }

    private static String getInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
}
