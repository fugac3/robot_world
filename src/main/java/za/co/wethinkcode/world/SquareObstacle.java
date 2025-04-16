package za.co.wethinkcode.world;

public class SquareObstacle implements Obstacle{
    private int x;
    private int y;
    private final ArrayList<ArrayList<String>> obstacles = new ArrayList<>();
    private Position bottomLeftCorner;
    private Position bottomRightCorner;
    private Position topRightConner ;
    private Position topLeftCorner;

    public Position getBottomLeftCorner() {
        return bottomLeftCorner;
    }

    public Position getBottomRightCorner() {
        return bottomRightCorner;
    }

    public Position getTopRightConner() {
        return topRightConner;
    }

    public Position getTopLeftCorner() {
        return topLeftCorner;
    }

    public SquareObstacle(int x, int y) {
        this.x = x;
        this.y = y;
        this.generateObstacle();
    }

    @Override
    public int getBottomLeftX() {
        return this.x;
    }

    @Override
    public int getBottomLeftY() {
        return this.y;
    }

    @Override
    public int getSize() {
        return 5;
    }



    private void generateObstacle() {

        ArrayList<String> obstacle = new ArrayList<>();
        this.bottomLeftCorner = new Position(getBottomLeftX(), getBottomLeftY());
        this.bottomRightCorner = new Position(getBottomLeftX() + (getSize()-2), getBottomLeftY());
        this.topRightConner = new Position(getBottomLeftX() + (getSize()-2), getBottomLeftY() + (getSize()-2));
        this.topLeftCorner = new Position(getBottomLeftX(), getBottomLeftY() + (getSize()-2));

        for(Position position: getRoutes(this.bottomLeftCorner,this.topLeftCorner)){
            String xyPosition = String.format("(%d,%d)",position.getX(),position.getY());
            obstacle.add(xyPosition);
        }
        for(Position position: getRoutes(this.bottomLeftCorner,this.bottomRightCorner)){
            String xyPosition = String.format("(%d,%d)",position.getX(),position.getY());
            obstacle.add(xyPosition);
        }
        for(Position position: getRoutes(this.topLeftCorner,this.topRightConner)){
            String xyPosition = String.format("(%d,%d)",position.getX(),position.getY());
            obstacle.add(xyPosition);
        }for(Position position: getRoutes(this.bottomRightCorner,this.topRightConner)){
            String xyPosition = String.format("(%d,%d)",position.getX(),position.getY());
            obstacle.add(xyPosition);
        }

        this.obstacles.add(obstacle);

    }

    public ArrayList<ArrayList<String>> getObstacles() {
        return this.obstacles;
    }

    @Override
    public boolean blocksPosition(Position position) {

        String xyPosition = String.format("(%d,%d)",position.getX(),position.getY());
        for(ArrayList<String> squareObstacle : obstacles) {
            if (squareObstacle.contains(xyPosition)) {
                return true;
            }
        }return false;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        for(Position position : getRoutes(a,b)){
            if(blocksPosition(position)){
                return true;
            }
        }
        return false;
    }

    private ArrayList<Position> getRoutes(Position a, Position b) {
        int aXPosition = a.getX();
        int aYPosition = a.getY();
        int bXPosition = b.getX();
        int bYPosition = b.getY();
        ArrayList<Position> corPositions = new ArrayList<>();
        if(aXPosition == bXPosition && aYPosition < bYPosition){

            for(int i = 0; i <= bYPosition; i++){
                Position xYPosition = new Position(aXPosition,aYPosition+i);
                corPositions.add(xYPosition);
            }

        }
        else if(aXPosition == bXPosition && aYPosition > bYPosition){
            for(int i = 0; i >= bYPosition; i--){
                Position xYPosition = new Position(aXPosition,aYPosition+i);
                corPositions.add(xYPosition);
            }

        }
        else if(aXPosition < bXPosition && aYPosition == bYPosition){
            for(int i = 0; i <= bXPosition; i++){
                Position xYPosition = new Position(aXPosition+i,aYPosition);
                corPositions.add(xYPosition);
            }

        }
        else if(aXPosition > bXPosition && aYPosition == bYPosition){
            for(int i = 0; i >= bXPosition; i--){
                Position xYPosition = new Position(aXPosition+i,aYPosition);
                corPositions.add(xYPosition);
            }

        }

        return corPositions;
    }


}
