package pacman.view.observer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for displaying the number of lives the player has left.
 * Extends GameBoardRenderable and implements Observer to update based on game state changes.
 */
public class LivesDisplay extends GameBoardRenderable implements Observer {
    private int displayedLives = 3;
    private Pane boardPane;
    private List<Image> lifeImages;

    /**
     * Constructor for LivesDisplay
     * @param gameState The GameState object to observe for changes
     */
    public LivesDisplay(GameState gameState) {
        super(10, 10);  // Call to superclass constructor with arbitrary dimensions
        gameState.addObserver(this);  // Register this object as an observer of the game state
        setupImages();  // Load the images for displaying lives
    }

    /**
     * Loads the image used to represent a life
     */
    private void setupImages() {
        lifeImages = new ArrayList<>();
        try {
            Image lifeImage = new Image(new FileInputStream("src/main/resources/maze/pacman/playerRight.png"));
            lifeImages.add(lifeImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update method called when the observed GameState changes
     * @param gameState The updated GameState
     */
    @Override
    public void update(GameState gameState) {
        int newLives = gameState.getTotalLives();
        if (newLives != displayedLives) {
            displayedLives = newLives;
            render();  // Re-render only if the number of lives has changed
        }
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
     * Renders the lives display
     */
    @Override
    public void render() {
        // Remove all existing life images
        boardPane.getChildren().removeIf(node -> node instanceof ImageView);

        // Add new life images based on the current number of lives
        for (int i = 0; i < displayedLives; i++) {
            ImageView imageView = new ImageView(lifeImages.get(0));
            imageView.setX(10 + i * 30);  // Position each life image horizontally
            imageView.setY(540);  // Set vertical position
            boardPane.getChildren().add(imageView);
        }
    }
}