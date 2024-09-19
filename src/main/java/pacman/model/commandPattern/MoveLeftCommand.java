package pacman.model.commandPattern;
import pacman.model.entity.dynamic.player.Controllable;

public class MoveLeftCommand implements Command {
    private Controllable player;

    public MoveLeftCommand(Controllable player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.left();
    }
}
