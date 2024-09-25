package pacman.model.commandPattern;

import pacman.model.entity.dynamic.player.Controllable;

/**
 * MoveRightCommand implements the Command interface.
 * It encapsulates the action of moving the player to the right in the game.
 * This class is part of the Command pattern implementation.
 */
public class MoveRightCommand implements Command {
    // The player that this command will operate on
    private Controllable player;

    /**
     * Constructor for MoveRightCommand.
     * @param player The Controllable player object that will be moved.
     */
    public MoveRightCommand(Controllable player) {
        this.player = player;
    }

    /**
     * Executes the command, moving the player to the right.
     * This method is called when the command is invoked.
     */
    @Override
    public void execute() {
        player.right();
    }
}
