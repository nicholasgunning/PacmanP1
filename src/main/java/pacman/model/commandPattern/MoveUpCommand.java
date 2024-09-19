package pacman.model.commandPattern;

import pacman.model.entity.dynamic.player.Controllable;

public class MoveUpCommand implements Command {
    private Controllable player;

    public MoveUpCommand(Controllable player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.up();
    }
}
