package za.co.wethinkcode.TestObstacles;

import za.co.wethinkcode.robots.world.Obstacle;

public class TestObstacle extends Obstacle {
    public TestObstacle(int x, int y) {
        super(x, y);
    }

    @Override
    public String getType() {
        return "test";
    }
}
