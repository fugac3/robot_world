//package za.co.wethinkcode.robots.commands;
//
//import za.co.wethinkcode.robots.robot.Robot;
//import za.co.wethinkcode.robots.server.Response;
//import za.co.wethinkcode.robots.world.IWorld;
//import za.co.wethinkcode.robots.world.Obstacle;
//
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class DumpCommand extends Command {
//    public DumpCommand() {
//        super("dump");
//    }
//
//    @Override
//    public Response execute(Robot robot) {
//        Map<String, Object> data = new HashMap<>();
//        data.put("robot", robot.getName());
//        data.put("position", robot.getPosition());
//        data.put("direction", robot.getCurrentDirection().toString());
//
//        IWorld world = robot.getWorld();
//        if (world != null) {
//            List<String> obstacleDescriptions = world.getObstacles().stream()
//                    .map(Obstacle::toString)
//                    .collect(Collectors.toList());
//            data.put("obstacles", obstacleDescriptions);
//        } else {
//            data.put("obstacles", "World not available.");
//        }
//
//        System.out.println("=========================");
//        return new Response("OK", "dump complete",data);
//    }
//}
