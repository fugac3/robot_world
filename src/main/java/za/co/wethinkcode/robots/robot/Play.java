package za.co.wethinkcode.robots.robot;

import za.co.wethinkcode.robots.commands.Command;

import java.util.Scanner;



public class Play {
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Robot robot;

        // Create the first robot
        String name1 = getInput("What do you want to name your first robot?");
        robot = new Robot(name1);
        System.out.println("Hello Kiddo! Your First Bot: " + robot.getName());


        // Show obstacles in the world
        robot.getWorld().showObstacles();

        Command command;
        boolean shouldContinue = true;
        do {
            // Get instructions for bot1
            String instructionForBot1 = getInput(robot.getName() + "> What must I do next?").strip().toLowerCase();

            try {
                // Execute the instructions for bot1
                command = Command.create(instructionForBot1);
                shouldContinue = robot.handleCommand(command);


            } catch (IllegalArgumentException e) {
                robot.setStatus("Sorry, I did not understand instructions from '" + robot.getName() + "': " + instructionForBot1);

            }

            // Print the status of the robots after processing commands
            System.out.println(robot);


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
