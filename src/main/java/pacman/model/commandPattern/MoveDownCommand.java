package pacman.model.commandPattern;

import pacman.model.entity.dynamic.player.Controllable;

public class MoveDownCommand implements Command {
    private Controllable player;

    public MoveDownCommand(Controllable player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.down();
    }
}
