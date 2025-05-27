package za.co.wethinkcode.robots.robotTypes;

public class RobotCreator {

    public static RobotType createRobotType(String robotType){
        if (robotType == null){
            return null;
        }
        switch (robotType.toLowerCase()){
            case "basic":
                return new BasicRobot();
            case "tank":
                return new TankRobot();
            case "scout":
                return new ScoutRobot();
            case "sniper":
                return new SniperRobot();
            case "heavy":
                return new HeavyRobot();
            default:
                return null;
        }
    }

}
