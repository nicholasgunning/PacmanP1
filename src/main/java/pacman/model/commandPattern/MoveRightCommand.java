package pacman.model.commandPattern;

import pacman.model.entity.dynamic.player.Controllable;

public class MoveRightCommand implements Command {
    private Controllable player;

    public MoveRightCommand(Controllable player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.right();
    }
}
