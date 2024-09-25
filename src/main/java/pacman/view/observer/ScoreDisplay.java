package pacman.view.observer;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class responsible for displaying the current score in the game.
 * Extends GameBoardRenderable and implements Observer to update based on game state changes.
 */
public class ScoreDisplay extends GameBoardRenderable implements Observer {
    private int displayedScore = 0;
    private Pane boardPane;

    /**
     * Constructor for ScoreDisplay
     * @param gameState The GameState object to observe for changes
     */
    public ScoreDisplay(GameState gameState) {
        super(10, 10);  // Call to superclass constructor with arbitrary dimensions
        gameState.addObserver(this);  // Register this object as an observer of the game state
    }

    /**
     * Sets the Pane where this display will be rendered
     * @param pane The Pane to set for this renderable
     */
    @Override
    public void setPane(Pane pane) {
        boardPane = pane;
    }

    /**
     * Update method called when the observed GameState changes
     * @param gameState The updated GameState
     */
    @Override
    public void update(GameState gameState) {
        int newScore = gameState.getTotalScore();
        if (newScore != displayedScore) {
            displayedScore = newScore;
            render();  // Re-render only if the score has changed
        }
    }

    /**
     * Renders the score display
     */
    @Override
    public void render() {
        // Remove all existing Text nodes (previous score displays)
        boardPane.getChildren().removeIf(node -> node instanceof Text);

        // Create and style the new score text
        Text scoreText = new Text(10, 30, "Score: " + displayedScore);
        scoreText.setFont(javafx.scene.text.Font.font("Press Start 2P", 20));
        scoreText.setFill(javafx.scene.paint.Color.WHITE);

        // Add the new score text to the pane
        boardPane.getChildren().add(scoreText);
    }
}