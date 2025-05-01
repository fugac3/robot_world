package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private String result;
    private String data;

    private Map<String, Object> state = new HashMap<>();

    public Response(String result, String data, Robot robot) {
        this.result = result;
        this.data = data;
//        if(robot.isNull){
//
//        }
        this.state = getState(robot);
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public Map<String, Object> getState(Robot robot) {
        Map<String, Object> currentState = new HashMap<>();

        Position pos = robot.getPosition();
        currentState.put("position", new int[]{pos.getX(), pos.getY()});
        currentState.put("direction", robot.getCurrentDirection().toString());
        currentState.put("status", robot.getStatus());
        return currentState;
    }





    public String getData() {
        return data;
    }

    public String getResult() {
        return result;
    }


}
