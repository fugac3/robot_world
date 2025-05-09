//package za.co.wethinkcode;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import za.co.wethinkcode.robots.commands.LookCommand;
//import za.co.wethinkcode.robots.robot.Robot;
//import za.co.wethinkcode.robots.world.SquareObstacle;
//import za.co.wethinkcode.robots.world.TextWorld;
//
//public class LookTest {
//
//    @Test
//    void testLookCommand() {
//        TextWorld world = new TextWorld();
//        world.getObstacles().add(new SquareObstacle(5, 5)); //add obstacle at 5,5
//        Robot robot = new Robot("Robo", world);
//        LookCommand look = new LookCommand(); //create look command
//
//        //make sure command was successfully run
//        boolean result = look.execute(robot);
//        assertTrue(result);
//
//        assertEquals("I see a square obstacle at [5,5]", robot.getStatus());
//        assertNotNull(robot.getStatus()); //status should not be null
//    }
//}
