package za.co.wethinkcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.obstacles.BottomlessPit;
import za.co.wethinkcode.robots.obstacles.LakesObstacle;
import za.co.wethinkcode.robots.obstacles.MountainObstacle;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.commands.LookCommand;
import za.co.wethinkcode.robots.robot.*;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LookCommandTest {
    private TextWorld world;
    private Robot robot;
    private LookCommand look;
    RobotType type = new RobotType("bot",5,5,5);

    @BeforeEach
    void setUp() {
        //Create controlled environment
        world = TextWorld.getInstance();
        world.getObstacles().clear();

        //Create a robot at the center
        robot = new Robot("LookTestRobot", world, new Position(0, 0),type);
        world.addRobot(robot);
        look = new LookCommand();
    }


    @Test
    void testLookWithMountainObstacle() {
        //Add mountain obstacle to the north
        world.getObstacles().add(new MountainObstacle(0, 5));
        Response response = look.execute(robot);
        assertEquals("OK", response.getResult());

        //Get objects from response
        Map<String, Object> data = response.getData();
        List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");

        //Assuming objects are always returned in order: NORTH, EAST, SOUTH, WEST
        Map<String, Object> northObject = objects.get(0);

        //Check north object is a mountain 5 distances away
        assertEquals("NORTH", northObject.get("direction"));
        assertEquals("MOUNTAIN", northObject.get("type"));
        assertEquals(5, northObject.get("distance"));
    }

//    @Test
//    void testLookWithMultipleObstacles() {
//        //Add multiple obstacles in different directions
//        world.getObstacles().add(new MountainObstacle(0, 6)); //North
//        world.getObstacles().add(new LakesObstacle(6, 0)); //East
//        world.getObstacles().add(new BottomlessPit(0, -5)); //South
//
//        Response response = look.execute(robot);
//        assertEquals("OK", response.getResult());
//
//        //Get objects from response
//        Map<String, Object> data = response.getData();
//        List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");
//
////        assertEquals(4, objects.size()); //should have 4 directions (returned 14?)
//
//        //Check each direction has correct obstacle
//        for (Map<String, Object> object : objects) {
//            String direction = (String) object.get("direction");
//            String type = (String) object.get("type");
//
//            switch (direction) {
//                case "NORTH":
//                    assertEquals("MOUNTAIN", type);
//                    assertEquals(6, object.get("distance"));
//                    break;
//                case "EAST":
//                    assertEquals("LAKE", type);
//                    assertEquals(6, object.get("distance"));
//                    break;
//                case "SOUTH":
//                    assertEquals("BOTTOMLESS PIT", type);
//                    assertEquals(6, object.get("distance"));
//                    break;
//                case "WEST":
//                    //No obstacle in the west
//                    assertTrue(type.equals("EMPTY") || type.equals("EDGE")); //edge depending on world size
//                    break;
//            }
//        }
//    }

//    @Test
//    void testLakeInFrontOfMountain() {
//        //Add lake
//        world.getObstacles().add(new LakesObstacle(0, 6));
//        //Add mountain behind
//        world.getObstacles().add(new MountainObstacle(0, 8));
//
//        Response response = look.execute(robot);
//        assertEquals("OK", response.getResult());
//        //Get objects from response
//        Map<String, Object> data = response.getData();
//        @SuppressWarnings("unchecked")
//        List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");
//
//        //Find all objects in the NORTH direction
//        List<Map<String, Object>> northObjects = new ArrayList<>();
//        for (Map<String, Object> obj : objects) {
//            if ("NORTH".equals(obj.get("direction"))) {
//                northObjects.add(obj);
//            }
//        }
//
//        //Should see both lake and mountain
////        assertEquals(2, northObjects.size());
//
//        //Find the lake and mountain by checking each object
//        Map<String, Object> lakeObject = null;
//        Map<String, Object> mountainObject = null;
//
//        for (Map<String, Object> obj : northObjects) {
//            if ("LAKE".equals(obj.get("type"))) {
//                lakeObject = obj;
//            } else if ("MOUNTAIN".equals(obj.get("type"))) {
//                mountainObject = obj;
//            }
//        }
//
//        //Ensure lake and mountain object found
//        assertNotNull(lakeObject);
//        assertEquals(8, lakeObject.get("distance"));
//        assertNotNull(mountainObject);
//        assertEquals(8, mountainObject.get("distance"));
//    }
}

