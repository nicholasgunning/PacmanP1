package pacman.view.observer;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Class responsible for displaying the "READY!" message when the game is about to start.
 * Extends GameBoardRenderable and implements Observer to update based on game state changes.
 */
public class GameReadyDisplay extends GameBoardRenderable implements Observer {
    private boolean gameReady;
    private Pane boardPane;
    private Text readyText;

    /**
     * Constructor for GameReadyDisplay
     * @param gameState The GameState object to observe for changes
     */
    public GameReadyDisplay(GameState gameState) {
        super(10, 10);  // Call to superclass constructor with arbitrary dimensions
        gameState.addObserver(this);  // Register this object as an observer of the game state
    }

    /**
     * Update method called when the observed GameState changes
     * @param gameState The updated GameState
     */
    @Override
    public void update(GameState gameState) {
        gameReady = gameState.isGameReady();  // Update local game ready state
        render();  // Re-render the display
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
     * Renders or removes the "READY!" message based on the game state
     */
    @Override
    public void render() {
        if (gameReady) {
            if (readyText == null) {
                // Create the "READY!" text if it doesn't exist yet
                readyText = new Text(170, 335, "READY!");
                readyText.setFont(javafx.scene.text.Font.font("Press Start 2P", 17));
                readyText.setFill(Color.YELLOW);
                boardPane.getChildren().add(readyText);  // Add the text to the pane
            }
        } else {
            if (readyText != null) {
                boardPane.getChildren().remove(readyText);  // Remove the text from the pane
                readyText = null;  // Reset the reference
            }
        }
    }
}