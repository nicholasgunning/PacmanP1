package pacman.model.entity.staticentity.collectable;


import javafx.scene.image.Image;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.staticentity.StaticEntityImpl;
import pacman.view.observer.GameState;
import pacman.view.observer.ScoreDisplay;

/**
 * Represents the Pellet in Pac-Man game
 */
public class Pellet extends StaticEntityImpl implements Collectable {

    private final int points;
    private boolean isCollectable;
    private GameState gameState;
    private ScoreDisplay scoreDisplay;

    public Pellet(BoundingBox boundingBox, Layer layer, Image image, int points) {
        super(boundingBox, layer, image);
        this.points = points;
        this.isCollectable = true;
        gameState = new GameState();
        scoreDisplay = new ScoreDisplay(gameState);
    }

    @Override
    public void collect() {
        int newScore = gameState.getTotalScore() + points;
        gameState.setTotalScore(newScore);
        this.isCollectable = false;
        setLayer(Layer.INVISIBLE);
    }

    @Override
    public void reset() {
        this.isCollectable = true;
        setLayer(Layer.FOREGROUND);
    }

    @Override
    public boolean isCollectable() {
        return this.isCollectable;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public int getPoints() {
        return this.points;
    }
}
