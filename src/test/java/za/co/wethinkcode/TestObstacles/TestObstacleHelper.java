package za.co.wethinkcode.TestObstacles;

import za.co.wethinkcode.robots.world.Obstacle;

//all tests are configured to Length: 7 height 5
public class TestObstacleHelper extends Obstacle {
    public TestObstacleHelper(int x, int y) {
        super(x, y);
    }

    @Override
    public String getType() {
        return "test";
    }
}
