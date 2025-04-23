package za.co.wethinkcode.robots.world;

public class LakesObsticle extends SquareObstacle{
    public LakesObsticle(int bottomLeftX, int bottomLeftY) {
        super(bottomLeftX, bottomLeftY);
    }

    public String getType(){
        return "Lake";
    }
}
