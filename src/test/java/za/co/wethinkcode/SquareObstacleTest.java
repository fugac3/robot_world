//package za.co.wethinkcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.world.Obstacle;
import za.co.wethinkcode.robots.world.SquareObstacle;
import za.co.wethinkcode.robots.world.TextWorld;



public class SquareObstacleTest {
    private SquareObstacle squareObstacle;
    @BeforeEach
    public void setUp(){
        squareObstacle = new SquareObstacle(5,5);

    }

    @Test
    public void getBottomLeftXTest(){
        SquareObstacle bottomLeftX = new SquareObstacle(5, 5);

        squareObstacle.getBottomLeftX();

        assertEquals(squareObstacle.getBottomLeftX(),squareObstacle.getBottomLeftX());



    }

    @Test
    public  void getBottomLeftTest(){
        SquareObstacle bottomLeftY = new SquareObstacle(9,9);

        squareObstacle.getBottomLeftY();

        assertEquals(squareObstacle.getBottomLeftY(),squareObstacle.getBottomLeftY());
    }

    @Test
    public  void getTopRightX(){
        SquareObstacle topRight = new SquareObstacle(-200,200);

        squareObstacle.getTopRightX();

        assertEquals(squareObstacle.getTopRightX(),squareObstacle.getTopRightX());
    }

    @Test
    public void getTopRightY() {
        SquareObstacle toRightY = new SquareObstacle(-200, 200);

        squareObstacle.getTopRightY();

        assertEquals(squareObstacle.getTopRightY(),squareObstacle.getTopRightY());
    }

    @Test
    public void getSize(){
        SquareObstacle size = new SquareObstacle(-200,100);

        squareObstacle.getSize();

        assertEquals(squareObstacle.getSize(),squareObstacle.getSize());
    }

    @Test
    public void blocksPosition(){
        SquareObstacle block = new SquareObstacle(-200,100);

        squareObstacle.blocksPath(new Position(5,5),new Position(9,9));

        boolean boolBlockPosition = squareObstacle.blocksPath(new Position(5,5),new Position(9,9));

        assertTrue(boolBlockPosition);
    }

    @Test
    public void


}
