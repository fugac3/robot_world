package za.co.wethinkcode.robots.robot;

public class RobotType {

    private String typeName;
    private int maxShieldStrength;
    private int maxShots;
    private int shootingRange;

    public RobotType(String typeName, int maxShieldStrength, int maxShots, int shootingRange){
        this.typeName = typeName;
        this.maxShieldStrength = maxShieldStrength;
        this.maxShots = maxShots;
        this.shootingRange = shootingRange;
    }
}
