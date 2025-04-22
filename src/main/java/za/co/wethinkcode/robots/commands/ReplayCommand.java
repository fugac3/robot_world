package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReplayCommand extends Command {

    public ReplayCommand(String argument) {
        super("replay", argument);
    }

    public ReplayCommand() {
        super("replay");
    }

    @Override
    public boolean execute(Robot target) {
        List<String> recorded = new ArrayList<>(target.getCommands());
        List<String> toReplay = new ArrayList<>();
        int count = 0;

        String arg = getArgument().toLowerCase().trim();
        boolean reversed = arg.contains("reversed"); // Check if "reversed" is in the argument
        arg = arg.replace("reversed", "").trim(); // Remove "reversed" from the argument

        if (arg.matches("\\d+-\\d+")) { // Handle range like "4-2"
            String[] parts = arg.split("-");
            int n = Integer.parseInt(parts[0]);  // inclusive (4th command from the last)
            int m = Integer.parseInt(parts[1]);  // exclusive (2nd command from the last)

            List<String> filtered = filterMovementCommands(recorded);

            int size = filtered.size();
            int start = size - n;  // Start from the nth last command (inclusive)
            int end = size - m;    // End at the mth last command (exclusive)

            // Ensure the range is valid (start < end)
            if (start < 0) start = 0;
            if (end > size) end = size;

            // Make sure that start < end, otherwise, don't replay anything.
            if (start < end) {
                toReplay = new ArrayList<>(filtered.subList(start, end));
            } else {
                toReplay.clear(); // Empty list if the range is invalid
            }
        }
        else if (arg.matches("\\d+")) { // Handle single number like "1"
            int n = Integer.parseInt(arg);
            int start = Math.max(0, recorded.size() - n);
            toReplay = new ArrayList<>(recorded.subList(start, recorded.size()));
        } else { // No argument, replay all commands
            toReplay = new ArrayList<>(recorded);
        }

        // If reversed, reverse the list of commands
        if (reversed) {
            Collections.reverse(toReplay);
        }
        // Execute replayed commands
        for (String line : toReplay) {
            Command cmd = create(line.trim());

            cmd.execute(target);

            System.out.println(target);

            count++;
        }

        target.setStatus("replayed " + count + " commands.");
        return true;
    }

    private List<String> filterMovementCommands(List<String> commands) {
        List<String> filtered = new ArrayList<>();
        for (String cmd : commands) {
            String commandName = cmd.split("\\|")[0].toLowerCase();
            if (commandName.startsWith("forward") ||
                    commandName.startsWith("back") ||
                    commandName.startsWith("left") ||
                    commandName.startsWith("right") ||
                    commandName.startsWith("sprint")) {
                filtered.add(cmd);
            }
        }
        return filtered;
    }

    @Override
    public String toString() {
        return "replay";
    }
}
