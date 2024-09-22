package pacman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pacman.model.engine.GameEngine;
import pacman.model.entity.factory.Renderable;
import pacman.view.background.BackgroundDrawer;
import pacman.view.background.StandardBackgroundDrawer;
import pacman.view.entity.EntityView;
import pacman.view.entity.EntityViewImpl;
import pacman.view.observer.Observer;
import pacman.view.observer.GameBoardRenderable;

import java.io.File;
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


    public GameWindow(GameEngine model, int width, int height) {
        this.model = model;

        pane = new Pane();
        scene = new Scene(pane, width, height);

        entityViews = new ArrayList<>();

        BackgroundDrawer backgroundDrawer = new StandardBackgroundDrawer();
        backgroundDrawer.draw(model, pane);

    }

    public Scene getScene() {
        return scene;
    }

    public void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(34),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        model.startGame();

        List<GameBoardRenderable> renderables = GameBoardRenderable.getAllRenderables();
        for (GameBoardRenderable renderable : renderables) {
            renderable.setPane(pane);
        }
    }

    private void draw() {
        model.tick();

        List<Renderable> entities = model.getRenderables();

        for (EntityView entityView : entityViews) {
            entityView.markForDelete();
        }

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

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }
        entityViews.removeIf(EntityView::isMarkedForDelete);
    }
}
