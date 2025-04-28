//package za.co.wethinkcode.robots.commands;
//
//import za.co.wethinkcode.robots.robot.Robot;
//import za.co.wethinkcode.robots.server.Request;
//import za.co.wethinkcode.robots.server.Response;
//
//public class SprintCommand extends Command {
//
//    public SprintCommand(String argument) {
//        super("sprint", argument);
//    }
//
//
//    @Override
//    public Response execute(Robot robot) {
//        int nrSteps = Integer.parseInt(getArgument());
//
//        for (int i = nrSteps; i >= 1; i--) {
//            if (robot.updatePosition(i)) {
//                robot.setStatus("Moved forward by " + i + " steps.");
//                System.out.println(robot);
//            } else {
//                robot.setStatus("Sorry, I cannot go outside my safe zone.");
//                System.out.println(robot.getStatus());
//                break;
//            }
//        }
//        return new Response("OK", "Robot sprinted successfully.", robot.getState());
//
//    }
//
//
//}
