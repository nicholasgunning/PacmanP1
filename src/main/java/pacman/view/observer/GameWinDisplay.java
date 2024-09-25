package pacman.view.observer;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GameWinDisplay extends GameBoardRenderable implements Observer {
    private Pane boardPane;
    private boolean gameWon = false;
    private Text gameWonText;

    public GameWinDisplay(GameState gameState) {
        super(10, 10);
        gameState.addObserver(this);
    }



    @Override
    public void setPane(Pane pane) {
        this.boardPane = pane;
    }

    @Override
    public void update(GameState gameState) {
        gameWon = gameState.isGameWon();
        render();
    }

    @Override
    public void render() {
        if (gameWon) {
            if (gameWonText == null) {
                gameWonText = new Text(150, 335, "GAME WON");
                gameWonText.setFont(javafx.scene.text.Font.font("Press Start 2P", 17));
                gameWonText.setFill(javafx.scene.paint.Color.GREEN);
                boardPane.getChildren().add(gameWonText);
            }
        }

    }


}
