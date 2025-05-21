package za.co.wethinkcode.robots.robot;

public class RobotType {

    private final String typeName; //type of robot
    private final int maxShieldStrength; //number of hits shield can take
    private final int maxShots; //number of shots before reloading
    private final int shootingRange; //distance that can be shot

    public RobotType(String typeName, int maxShieldStrength, int maxShots, int shootingRange){
        this.typeName = typeName;
        this.maxShieldStrength = maxShieldStrength;
        this.maxShots = maxShots;
        this.shootingRange = shootingRange;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getMaxShieldStrength() {
        return maxShieldStrength;
    }

    public int getMaxShots() {
        return maxShots;
    }

    public int getShootingRange() {
        return shootingRange;
    }
}
