//package za.co.wethinkcode.robots.commands;
//
//import za.co.wethinkcode.robots.robot.Robot;
//import za.co.wethinkcode.robots.server.Response;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class StateCommand extends Command {
//    public StateCommand() {
//        super("state");
//    }
//
//    @Override
//    public Response execute(Robot robot) {
//        Map<String, String> stateInfo = new HashMap<>();
//
//        stateInfo.put("Position",robot.getPosition().toString());
//        stateInfo.put("Direction", robot.getCurrentDirection().toString());
//
//        return new Response("Ok", "robot State.",Map.of(robot.getName(), stateInfo));
//    }
//}
