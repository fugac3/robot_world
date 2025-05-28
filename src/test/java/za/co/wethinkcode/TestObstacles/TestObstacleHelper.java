package za.co.wethinkcode.TestObstacles;

import za.co.wethinkcode.robots.obstacles.Obstacle;

//all tests obstacles are configured to Length: 7 height 5
public class TestObstacleHelper extends Obstacle {
    public TestObstacleHelper(int x, int y) {
        super(x, y);
    }

    @Override
    public String getType() {
        return "test";
    }
}
