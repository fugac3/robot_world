package za.co.wethinkcode.robots.Combat;

import za.co.wethinkcode.robots.robot.Robot;

//helper class for getting info on the robot that was hit
public class HitResult {
    public final boolean hit;
    public final Robot hitRobot;

    public HitResult(boolean hit, Robot hitRobot) {
        this.hit = hit;
        this.hitRobot = hitRobot;
    }
}
