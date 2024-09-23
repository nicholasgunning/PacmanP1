package pacman.view.observer;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameReadyDisplay extends GameBoardRenderable implements Observer {
    private boolean gameReady;
    private Pane boardPane;
    private Text readyText;

    public GameReadyDisplay(GameState gameState) {
        super(10, 10);
        gameState.addObserver(this);
    }

    @Override
    public void update(GameState gameState) {
        gameReady = gameState.isGameReady();
        render();
    }

    @Override
    public void setPane(Pane pane) {
        boardPane = pane;
    }

    @Override
    public void render() {
        if (gameReady) {
            if (readyText == null) {
                readyText = new Text(170, 335, "READY!");
                readyText.setFont(javafx.scene.text.Font.font("Press Start 2P", 17));
                readyText.setFill(Color.YELLOW);
                boardPane.getChildren().add(readyText);
            }
        } else {
            if (readyText != null) {
                boardPane.getChildren().remove(readyText);
                readyText = null;  // Reset the reference
            }
        }
    }
}