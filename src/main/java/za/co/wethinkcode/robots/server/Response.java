package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private String result;
    private final Map<String, Object> data;
    private final Map<String, Object> state;

    public Response(String result, Map<String, Object> data, Robot robot) {
        this.result = result;
        this.data = data;
        this.state = (robot != null) ? buildState(robot) : null;
    }

    public static Map<String, Object> buildState(Robot robot) {
        if (robot == null) return null;
        Map<String, Object> currentState = new HashMap<>();
        Position pos = robot.getPosition();
        currentState.put("position", new int[]{pos.getX(), pos.getY()});
        currentState.put("direction", robot.getCurrentDirection());
        currentState.put("status", robot.getStatus());
        currentState.put("shots", robot.getAmmo());
        return currentState;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String getResult() {
        return result;
    }


}
