package za.co.wethinkcode.robots.combat;

import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

/**
 * Represents a bullet fired in the world.
 * The bullet travels in a specific direction until it either hits an obstacle or runs out of distance.
 */
public class Bullet {
    /** The current position of the bullet. */
    private Position position;

    /** The direction in which the bullet is moving. */
    private final Direction direction;

    /** The remaining distance the bullet can travel. */
    private int distanceLeft;

    /** The robot that will be shooting the bullet. */
    private final Robot shooter;


    /**
     * Constructs a new Bullet with a starting position, direction, and maximum travel distance.
     *
     * @param startPosition the initial position of the bullet
     * @param direction the direction in which the bullet will move
     * @param maxDistance the maximum number of steps the bullet can move
     * @param shooter the shooter who shot the bullet
     */
    public Bullet(Position startPosition, Direction direction, int maxDistance, Robot shooter) {
        this.position = startPosition;
        this.direction = direction;
        this.distanceLeft = maxDistance;
        this.shooter = shooter;
    }


    public int getDistanceLeft() {
        return distanceLeft;
    }

    public Robot getShooter() {
        return shooter;
    }

    /**
     * Moves the bullet one step in its direction.
     *
     * @return {@code true} if the bullet moved successfully, {@code false} if it has stopped
     */
    public boolean move() {
        if (distanceLeft <= 0) {
            return false; // bullet has stopped
        }

        // Move the bullet in its current direction
        switch (direction) {
            case NORTH: position = new Position(position.getX(), position.getY() + 1); break;
            case SOUTH: position = new Position(position.getX(), position.getY() - 1); break;
            case EAST:  position = new Position(position.getX() + 1, position.getY()); break;
            case WEST:  position = new Position(position.getX() - 1, position.getY()); break;
        }

        distanceLeft--;
        return true;
    }

    /**
     * Returns the current position of the bullet.
     *
     * @return the current {@link Position} of the bullet
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Checks whether the bullet has stopped moving (i.e., no distance left).
     *
     * @return {@code true} if the bullet can no longer move, {@code false} otherwise
     */
    public boolean hasStopped() {
        return distanceLeft <= 0;
    }

    // toString added for debugging purposes.
    public String toString() {
        return "Bullet{pos=" + position +
                ", dir=" + direction +
                ", distLeft=" + distanceLeft + "}";
    }
}
