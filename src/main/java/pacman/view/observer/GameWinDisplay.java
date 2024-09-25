package pacman.view.observer;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Class responsible for displaying the "GAME WON" message when the player wins the game.
 * Extends GameBoardRenderable and implements Observer to update based on game state changes.
 */
public class GameWinDisplay extends GameBoardRenderable implements Observer {
    private Pane boardPane;
    private boolean gameWon = false;
    private Text gameWonText;

    /**
     * Constructor for GameWinDisplay
     * @param gameState The GameState object to observe for changes
     */
    public GameWinDisplay(GameState gameState) {
        super(10, 10);  // Call to superclass constructor with arbitrary dimensions
        gameState.addObserver(this);  // Register this object as an observer of the game state
    }

    /**
     * Sets the Pane where this display will be rendered
     * @param pane The Pane to set for this renderable
     */
    @Override
    public void setPane(Pane pane) {
        this.boardPane = pane;
    }

    /**
     * Update method called when the observed GameState changes
     * @param gameState The updated GameState
     */
    @Override
    public void update(GameState gameState) {
        gameWon = gameState.isGameWon();  // Update local game won state
        render();  // Re-render the display
    }

    /**
     * Renders the "GAME WON" message if the game is won
     */
    @Override
    public void render() {
        if (gameWon) {
            if (gameWonText == null) {
                // Create the "GAME WON" text if it doesn't exist yet
                gameWonText = new Text(150, 335, "GAME WON");
                gameWonText.setFont(javafx.scene.text.Font.font("Press Start 2P", 17));
                gameWonText.setFill(javafx.scene.paint.Color.GREEN);
                boardPane.getChildren().add(gameWonText);  // Add the text to the pane
            }
        }
    }
}