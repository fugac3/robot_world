package za.co.wethinkcode.robots.serverCommands;

import java.util.List;
import java.util.Map;

public class RobotsCommand {
    public static String formatRobotList(List<Map<String, Object>> robots) {
        if (robots == null || robots.isEmpty()) {
            return "No robots found.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("== All Active Robots ==\n");
        sb.append(String.format("%-6s %-6s %-9s %-10s %-8s %-6s %-10s\n",
                "name", "type", "position", "direction", "shields", "shots", "status"));

        for (Map<String, Object> r : robots) {
            String name = (String) r.get("name");
            String type = (String) r.get("type");
            int[] pos = (int[]) r.get("position");
            String posStr = String.format("(%1d,%1d)", pos[0], pos[1]);
            String dir = r.get("direction").toString();
            int shields = (int) r.get("shields");
            int shots = (int) r.get("shots");
            String status = r.get("status").toString();

            sb.append(String.format("%-6s %-6s %-9s %-10s %-8d %-6d %-10s\n",
                    name, type, posStr, dir, shields, shots, status));
        }

        return sb.toString();
    }
}
