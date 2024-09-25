package pacman.view.observer;

import javafx.scene.Node;
import java.util.ArrayList;
import javafx.scene.layout.Pane;

/**
 * Abstract class representing a renderable element on the game board.
 * This class provides a framework for objects that can be rendered in the game.
 */
public abstract class GameBoardRenderable {
    protected int width;
    protected int height;
    private Pane pane;

    // Static list to keep track of all GameBoardRenderable instances
    private static ArrayList<GameBoardRenderable> gameBoardRenderables = new ArrayList<>();

    /**
     * Constructor for GameBoardRenderable
     * @param width The width of the renderable element
     * @param height The height of the renderable element
     */
    public GameBoardRenderable(int width, int height) {
        this.width = width;
        this.height = height;
        gameBoardRenderables.add(this);  // Add this instance to the static list
    }

    /**
     * Abstract method to set the Pane for this renderable.
     * Must be implemented by subclasses.
     * @param pane The Pane to set for this renderable
     */
    public abstract void setPane(Pane pane);

    /**
     * Abstract method to render this element.
     * Must be implemented by subclasses to define how the element is rendered.
     */
    public abstract void render();

    /**
     * Static method to get all GameBoardRenderable instances.
     * @return ArrayList of all GameBoardRenderable instances
     */
    public static ArrayList<GameBoardRenderable> getAllRenderables() {
        return gameBoardRenderables;
    }
}