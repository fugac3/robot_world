package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.RobotTypes.RobotType;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.RobotTypes.RobotTypeFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * The LaunchCommand class represents the action of launching a robot in the world.
 * The robot is identified by its name, and the command ensures that the name is provided before the launch.
 */
public class LaunchCommand extends Command {
    private final String robotName;
    private final String robotTypeName;
    private Robot robot;

    /**
     * Constructs a new LaunchCommand with the given robot name.
     *
     * @param robotName the name of the robot to launch
     */
    public LaunchCommand(String robotTypeName,String robotName) {
        super("launch", robotTypeName + " " + robotName);
        this.robotName = robotName;
        this.robotTypeName = robotTypeName;
    }

    /**
     * Executes the launch command, ensuring that the robot name is valid.
     * If the robot name is not provided, it returns an error message.
     * Otherwise, it returns a success message indicating the command was executed.
     *
     * @param robot the robot executing the command (currently not used)
     * @return a {@link Response} object indicating the result of the command execution
     */
    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();

        // Check if robot name is null or empty
        if (robotName == null || robotName.trim().isEmpty()) {
            data.put("message", "Launch command needs a name.");
        }

        // Check if robot type is null or empty
        if (robotTypeName == null || robotTypeName.trim().isEmpty()) {
            data.put("message", "Launch command needs a robot type.");
            return new Response("ERROR", data, null);
        }

        // Check if robot type is valid
        RobotType type = RobotTypeFactory.createRobotType(robotTypeName);
        if (type == null) {
            data.put("message", "Unknown robot type: " + robotTypeName);
            return new Response("ERROR", data, null);
        }

        // Assuming further robot launch logic will be handled later
        // For now, returning a successful response
        data.put("message", "Launch successful for " + type.getTypeName() + " robot: " + robotName);
        return new Response("OK", data, null);
    }

    /**
     * Gets the name of the robot to be launched.
     *
     * @return the name of the robot
     */
    public String getRobotName() {
        return robotName;
    }

    /**
     * Gets the type of robot to be launched.
     *
     * @return the type of the robot
     */
    public String getRobotTypeName() {return robotTypeName;}
}
