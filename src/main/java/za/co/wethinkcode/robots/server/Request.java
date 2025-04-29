package za.co.wethinkcode.robots.server;

import java.util.Map;

public class Request {
    private String command;
    private Map<String, Object> arguments;

    // Required for GSON
    public Request() {}

    public Request(String command, Map<String, Object> arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }
}
