package pacman.model.commandPattern;

import java.util.HashMap;
import java.util.Map;

/**
 * The CommandInvoker class implements the Command pattern.
 * It stores and executes commands, decoupling the sender of a request from the object that performs the request.
 */
public class CommandInvoker {
    // Map to store commands, with command names as keys and Command objects as values
    private Map<String, Command> commands = new HashMap<>();

    /**
     * Adds a new command to the invoker.
     * @param name The name of the command, used as a key to retrieve it later.
     * @param command The Command object to be stored.
     */
    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    /**
     * Executes a command by its name.
     * @param name The name of the command to be executed.
     */
    public void executeCommand(String name) {
        Command command = commands.get(name);
        if (command != null) {
            command.execute();
        }
    }
}