//package za.co.wethinkcode.robots.commands;
//import za.co.wethinkcode.robots.robot.Robot;
//
//public class StateCommand extends Command{
//    public StateCommand() {super("state");}
//
//    @Override
//    public boolean execute(Robot target){
//        target.setStatus(
//                "state: {\n" +
//                "position: " + target.getPosition() + "\n" +
//                "direction: " + target.getCurrentDirection() + "\n" +
//                "shields: 3" + "\n" +
//                "shots: 5" + "\n" +
//                "status: NORMAL" + "\n}"
//        );
////        target.setStatus("OK");
//        return true;
//    };
//}
