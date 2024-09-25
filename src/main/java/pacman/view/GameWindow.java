package pacman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pacman.model.engine.GameEngine;
import pacman.model.entity.factory.Renderable;
import pacman.view.background.BackgroundDrawer;
import pacman.view.background.StandardBackgroundDrawer;
import pacman.view.entity.EntityView;
import pacman.view.entity.EntityViewImpl;
import pacman.view.observer.GameBoardRenderable;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for managing the Pac-Man Game View
 */
public class GameWindow {

    private final Scene scene;
    private final Pane pane;
    private final GameEngine model;
    private final List<EntityView> entityViews;

    /**
     * Constructor for GameWindow
     * @param model The game engine
     * @param width The width of the game window
     * @param height The height of the game window
     */
    public GameWindow(GameEngine model, int width, int height) {
        this.model = model;
        pane = new Pane();
        scene = new Scene(pane, width, height);
        entityViews = new ArrayList<>();

        // Draw the background
        BackgroundDrawer backgroundDrawer = new StandardBackgroundDrawer();
        backgroundDrawer.draw(model, pane);
    }

    /**
     * Get the JavaFX Scene
     * @return The Scene object
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Start the game loop
     */
    public void run() {
        // Set up a timeline to call the draw method every 34 milliseconds (approx. 30 fps)
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(34),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        model.startGame();

        // Set up renderables
        List<GameBoardRenderable> renderables = GameBoardRenderable.getAllRenderables();
        for (GameBoardRenderable renderable : renderables) {
            renderable.setPane(pane);
        }
    }

    /**
     * Update the game view
     */
    private void draw() {
        model.tick();

        List<Renderable> entities = model.getRenderables();

        // Mark all existing entity views for deletion
        for (EntityView entityView : entityViews) {
            entityView.markForDelete();
        }

        // Update existing views and create new ones as needed
        for (Renderable entity : entities) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update();
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        // Remove views that are no longer needed
        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }
        entityViews.removeIf(EntityView::isMarkedForDelete);
    }
}