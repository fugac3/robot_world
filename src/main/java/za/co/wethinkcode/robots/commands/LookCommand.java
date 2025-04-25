package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;

import java.util.Map;
import za.co.wethinkcode.robots.world.Artefact;
import za.co.wethinkcode.robots.world.IWorld;
//look command
public class LookCommand extends Command {
    public LookCommand() {
        super("look");
    }

    @Override
    public boolean execute(Robot target) {
        // Perform the look around
        Map<IWorld.Direction, Artefact> view = target.lookAround();
        boolean obstacleDetected = false;

        // Check if there's any obstacle in the view
        for (Map.Entry<IWorld.Direction, Artefact> entry : view.entrySet()) {
            if (entry.getValue() == Artefact.OBSTACLE) {
                obstacleDetected = true;
                break;
            }
        }

        if (obstacleDetected) {
            target.setStatus("Obstacle detected in the world.");
        } else {
            target.setStatus("No obstacles in the world.");
        }

        return true;
    }
}

