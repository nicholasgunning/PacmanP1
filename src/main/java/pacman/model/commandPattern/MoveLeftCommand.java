package pacman.model.commandPattern;
import pacman.model.entity.dynamic.player.Controllable;

/**
 * MoveLeftCommand implements the Command interface.
 * It encapsulates the action of moving the player to the left in the game.
 * This class is part of the Command pattern implementation.
 */
public class MoveLeftCommand implements Command {
    // The player that this command will operate on
    private Controllable player;

    /**
     * Constructor for MoveLeftCommand.
     * @param player The Controllable player object that will be moved.
     */
    public MoveLeftCommand(Controllable player) {
        this.player = player;
    }

    /**
     * Executes the command, moving the player to the left.
     * This method is called when the command is invoked.
     */
    @Override
    public void execute() {
        player.left();
    }
}