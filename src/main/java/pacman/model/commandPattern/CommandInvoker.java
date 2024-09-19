package pacman.model.commandPattern;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {
    private Map<String, Command> commands = new HashMap<>();

    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    public void executeCommand(String name) {
        Command command = commands.get(name);
        if (command != null) {
            command.execute();
        }
    }
}
