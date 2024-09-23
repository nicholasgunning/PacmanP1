package pacman.view.observer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LivesDisplay extends GameBoardRenderable implements Observer {
    private int displayedLives = 3;
    private Pane boardPane;
    private List<Image> lifeImages;

    public LivesDisplay(GameState gameState) {
        super(10, 10);
        gameState.addObserver(this);
        setupImages();
    }

    private void setupImages() {
        lifeImages = new ArrayList<>();
        try {
            Image lifeImage = new Image(new FileInputStream("src/main/resources/maze/pacman/playerRight.png"));
            lifeImages.add(lifeImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameState gameState) {

        int newLives = gameState.getTotalLives();
        if (newLives != displayedLives) {
            displayedLives = newLives;
            render();
        }
    }

    @Override
    public void setPane(Pane pane) {
        boardPane = pane;
        render();
    }

    @Override
    public void render() {
        boardPane.getChildren().removeIf(node -> node instanceof ImageView);
        for (int i = 0; i < displayedLives; i++) {
            ImageView imageView = new ImageView(lifeImages.get(0));
            imageView.setX(10 + i * 30);
            imageView.setY(540);
            boardPane.getChildren().add(imageView);
        }
    }
}