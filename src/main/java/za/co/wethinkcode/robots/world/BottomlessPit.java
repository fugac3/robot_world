package za.co.wethinkcode.robots.world;

public class BottomlessPit extends RectangleObstacle{
    public BottomlessPit(int bottomLeftX, int bottomLeftY) {
        super(bottomLeftX, bottomLeftY);
    }

    public String getType(){
        return "bottomless pits";
    }
}
