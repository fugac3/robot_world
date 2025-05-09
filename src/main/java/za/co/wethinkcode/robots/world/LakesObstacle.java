package za.co.wethinkcode.robots.world;

public class LakesObstacle extends RectangleObstacle{
    public LakesObstacle(int bottomLeftX, int bottomLeftY) {
        super(bottomLeftX, bottomLeftY);
    }

    public String getType(){
        return "Lake";
    }
}
