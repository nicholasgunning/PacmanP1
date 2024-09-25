package pacman.model.commandPattern;

import pacman.model.entity.dynamic.player.Controllable;

/**
 * MoveUpCommand implements the Command interface.
 * It encapsulates the action of moving the player upward in the game.
 * This class is part of the Command pattern implementation.
 */
public class MoveUpCommand implements Command {
    // The player that this command will operate on
    private Controllable player;

    /**
     * Constructor for MoveUpCommand.
     * @param player The Controllable player object that will be moved.
     */
    public MoveUpCommand(Controllable player) {
        this.player = player;
    }

    /**
     * Executes the command, moving the player upward.
     * This method is called when the command is invoked.
     */
    @Override
    public void execute() {
        player.up();
    }
}