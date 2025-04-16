package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Position;
import za.co.wethinkcode.maze.EmptyMaze;
import za.co.wethinkcode.world.World;
import za.co.wethinkcode.world.TextWorld;

import static org.junit.jupiter.api.Assertions.*;

public class WorldTest {

    @Test
    void updatePosition() {
        IWorld world = new TextWorld(new EmptyMaze());
        assertEquals(World.CENTRE, world.getPosition());
        world.updatePosition(100);
        Position newPosition = new Position(World.CENTRE.getX(), World.CENTRE.getY() + 100);
        assertEquals(newPosition, world.getPosition());
    }

    @Test
    void updateDirectionRight() {
        World world = new TextWorld(new EmptyMaze());
        world.updateDirection(true);
        assertEquals(World.Direction.RIGHT, world.getCurrentDirection());
        world.updatePosition(100);
        Position newPosition = new Position(World.CENTRE.getX() + 100, World.CENTRE.getY());
        assertEquals(newPosition, world.getPosition());
    }

    @Test
    void updateDirectionLeft() {
        World world = new TextWorld(new EmptyMaze());
        world.updateDirection(false);
        assertEquals(World.Direction.LEFT, world.getCurrentDirection());
        world.updatePosition(100);
        Position newPosition = new Position(World.CENTRE.getX() - 100, World.CENTRE.getY());
        assertEquals(newPosition, world.getPosition());
    }

    @Test
    void isNewPositionAllowed() {
        World world = new TextWorld(new EmptyMaze());
        assertTrue(world.isNewPositionAllowed(new Position(100,0)));
        assertTrue(world.isNewPositionAllowed(new Position(100,100)));
        assertFalse(world.isNewPositionAllowed(new Position(201,0)));
        assertFalse(world.isNewPositionAllowed(new Position(-201,0)));
    }

    @Test
    void isAtEdge() {
        World world = new TextWorld(new EmptyMaze());
        assertFalse(world.isAtEdge());
        assertEquals(World.UpdateResponse.SUCCESS,world.updatePosition(200));
        assertTrue(world.isAtEdge());
    }

    @Test
    void reset() {
        World world = new TextWorld(new EmptyMaze());
        world.updatePosition(100);
        world.updateDirection(true);
        assertEquals(World.Direction.RIGHT, world.getCurrentDirection());
        world.reset();
        assertEquals(World.Direction.UP, world.getCurrentDirection());
        assertEquals(World.CENTRE, world.getPosition());
    }
}

