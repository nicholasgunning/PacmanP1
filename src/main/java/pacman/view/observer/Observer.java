package pacman.view.observer;

/**
 * Observer interface for the Observer design pattern.
 * Classes that implement this interface can be notified of changes in a Subject (typically GameState).
 */
public interface Observer {

    /**
     * This method is called when the observed Subject (GameState) is updated.
     * Implementing classes should define how they respond to changes in the game state.
     *
     * @param gameState The updated GameState object containing the current state of the game.
     */
    void update(GameState gameState);
}