package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;

public class WorldConfig {
    public final Position topLeft;
    public final Position bottomRight;
    public final int maxObstacles;

    public WorldConfig(int length, int height, int maxObstacles) {
        int divLength = length/2;
        int divHeight = height/2;
        this.topLeft = new Position(-divLength, divLength);
        this.bottomRight = new Position(divHeight, -divHeight);
        this.maxObstacles = maxObstacles;
    }
}
