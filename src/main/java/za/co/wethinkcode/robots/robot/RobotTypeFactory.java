package za.co.wethinkcode.robots.robot;

public class RobotTypeFactory {

    public static RobotType createRobotType(String robotType){
        if (robotType == null){
            return null;
        }
        switch (robotType.toLowerCase()){
            case "cannon":
                return new CannonRobot();
            case "bunker":
                return new BunkerRobot();
            case "stormcaller":
                return new StormcallerRobot();
            case "wasp":
                return new WaspRobot();
            case "whiplash":
                return new WhiplashRobot();
            default:
                return null;
        }
    }

}
