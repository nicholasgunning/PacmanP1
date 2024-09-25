package pacman.model.commandPattern;

import pacman.model.entity.dynamic.player.Controllable;

/**
 * MoveDownCommand implements the Command interface.
 * It represents a command to move the player downward in the game.
 * This class is part of the Command pattern implementation.
 */
public class MoveDownCommand implements Command {
    // The player that this command will operate on
    private Controllable player;

    /**
     * Constructor for MoveDownCommand.
     * @param player The Controllable player object that will be moved.
     */
    public MoveDownCommand(Controllable player) {
        this.player = player;
    }

    /**
     * Executes the command, moving the player downward.
     * This method is called when the command is invoked.
     */
    @Override
    public void execute() {
        player.down();
    }
}
