package pacman.view.observer;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Class responsible for displaying the "GAME OVER" message when the game ends.
 * Extends GameBoardRenderable and implements Observer to update based on game state changes.
 */
public class GameOverDisplay extends GameBoardRenderable implements Observer {
    private boolean gameOver;
    private Pane boardPane;
    private Text gameOverText;

    /**
     * Constructor for GameOverDisplay
     * @param gameState The GameState object to observe for changes
     */
    public GameOverDisplay(GameState gameState) {
        super(10, 10);  // Call to superclass constructor with arbitrary dimensions
        gameState.addObserver(this);  // Register this object as an observer of the game state
    }

    /**
     * Update method called when the observed GameState changes
     * @param gameState The updated GameState
     */
    @Override
    public void update(GameState gameState) {
        gameOver = gameState.isGameOver();  // Update local game over state
        render();  // Re-render the display
    }

    /**
     * Sets the Pane where this display will be rendered
     * @param pane The Pane to set for this renderable
     */
    @Override
    public void setPane(Pane pane) {
        boardPane = pane;
        render();  // Initial render after setting the pane
    }

    /**
     * Renders the "GAME OVER" message if the game is over
     */
    @Override
    public void render() {
        if (gameOver) {
            if (gameOverText == null) {
                // Create the "GAME OVER" text if it doesn't exist yet
                gameOverText = new Text(150, 335, "GAME OVER");
                gameOverText.setFont(javafx.scene.text.Font.font("Press Start 2P", 17));
                gameOverText.setFill(javafx.scene.paint.Color.RED);
                boardPane.getChildren().add(gameOverText);  // Add the text to the pane
            }
        }
    }
}