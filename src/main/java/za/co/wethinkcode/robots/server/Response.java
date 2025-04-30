package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private String result;
    private String message;
    private String status;
    private Map<String, Object> state = new HashMap<>();

    public Response(String result, String message, Robot robot) {
        this.status = "Ready";
        this.result = result;
        this.message = message;
        this.state = getState(robot);
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public Map<String, Object> getState(Robot robot) {
        Map<String, Object> currentState = new HashMap<>();

        Position pos = robot.getPosition();
        currentState.put("position", new int[]{pos.getX(), pos.getY()});
//        Map<String, Integer> position = new HashMap<>();
//        position.put("x", robot.getPosition().getX());
//        position.put("y", robot.getPosition().getY());        // Store position as a map of x and y
        currentState.put("direction", robot.getCurrentDirection().toString());
        currentState.put("status", getStatus());
//        currentState.put("name", this.name);
        return currentState;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }


}
