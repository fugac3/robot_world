package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;

public class WorldConfig {
    public final Position topLeft;
    public final Position bottomRight;
    public final int maxObstacles;

    public WorldConfig(int tlX, int tlY, int brX, int brY, int maxObstacles) {
        this.topLeft = new Position(tlX, tlY);
        this.bottomRight = new Position(brX, brY);
        this.maxObstacles = maxObstacles;
    }
}
