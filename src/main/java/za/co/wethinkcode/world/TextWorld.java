package za.co.wethinkcode.world;

import za.co.wethinkcode.Position;

import java.util.List;

public class TextWorld implements World {


    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        return null;
    }

    @Override
    public void updateDirection(boolean turnRight) {

    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public Direction getCurrentDirection() {
        return null;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        return false;
    }

    @Override
    public boolean isAtEdge() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public List<Obstacle> getObstacles() {
        return List.of();
    }

    @Override
    public void showObstacles() {

    }
}
