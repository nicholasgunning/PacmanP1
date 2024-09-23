package pacman.view.observer;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameOverDisplay extends GameBoardRenderable implements Observer {
    private boolean gameOver;
    private Pane boardPane;
    private Text gameOverText;

    public GameOverDisplay(GameState gameState) {
        super(10, 10);
        gameState.addObserver(this);
    }

    @Override
    public void update(GameState gameState) {
        gameOver = gameState.isGameOver();
        render();
    }

    @Override
    public void setPane(Pane pane) {
        boardPane = pane;
        render();
    }

    @Override
    public void render() {
        if (gameOver) {
            if (gameOverText == null) {
                gameOverText = new Text(150, 335, "GAME OVER");
                gameOverText.setFont(javafx.scene.text.Font.font("Press Start 2P", 17));
                gameOverText.setFill(javafx.scene.paint.Color.RED);
                boardPane.getChildren().add(gameOverText);
            }
        }
    }
}
