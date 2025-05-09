package za.co.wethinkcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.commands.ForwardCommand;
import za.co.wethinkcode.robots.commands.BackCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.MountainObstacle;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
        private TextWorld world;
        private Robot robot;

        @BeforeEach
        void setUp(){
            //Creating a controlled environment with no obstacles and defined position to make testing easier
            world = TextWorld.getInstance();
            world.getObstacles().clear(); //get rid of all obstacles in world
            robot = new Robot("Robo", world, new Position(0,0));
            robot.setStatus("NORMAL");
        }


}