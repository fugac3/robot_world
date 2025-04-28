package za.co.wethinkcode.robots.server;

import java.util.Map;

public class Response {
    private String result;
    private String message;
    private Map<String, Object> state;

    public Response(String result, String message, Map<String, Object> state) {
        this.result = result;
        this.message = message;
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }

    public Map<String, Object> getState() {
        return state;
    }

}
