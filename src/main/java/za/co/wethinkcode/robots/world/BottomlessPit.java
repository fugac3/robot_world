package za.co.wethinkcode.robots.world;

public class BottomlessPit extends SquareObstacle{
    public BottomlessPit(int bottomLeftX, int bottomLeftY) {
        super(bottomLeftX, bottomLeftY);
    }

    public String getType(){
        return "bottomless pits";
    }


//    public String bottomLessPits(){
//        return "bottomL"
//    }
}
