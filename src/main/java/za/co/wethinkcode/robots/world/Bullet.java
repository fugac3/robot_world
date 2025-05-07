package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;

public class Bullet {
    private Position position;
    private final Direction direction;
    private int distanceLeft;

    public Bullet(Position startPosition, Direction direction, int maxDistance) {
        this.position = startPosition;
        this.direction = direction;
        this.distanceLeft = maxDistance;
    }

    public boolean move() {
        if (distanceLeft <= 0) {
            return false; // bullet has stopped
        }

        // move bullet in the direction it's travelling
        switch (direction) {
            case NORTH: position = new Position(position.getX(), position.getY() + 1); break;
            case SOUTH: position = new Position(position.getX(), position.getY() - 1); break;
            case EAST: position = new Position(position.getX() + 1, position.getY()); break;
            case WEST: position = new Position(position.getX() - 1, position.getY()); break;
        }

        distanceLeft--;
        return true;
    }

    public Position getPosition() {
        return position;
    }

    public boolean hasStopped() {
        return distanceLeft <= 0;
    }
}
