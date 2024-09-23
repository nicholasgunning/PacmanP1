package pacman.view.observer;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ScoreDisplay extends GameBoardRenderable implements Observer {
    private int displayedScore = 0;
    private Pane boardPane;


    static {
        javafx.scene.text.Font font = null;
        try {
            File fontFile = new File("src/main/resources/maze/PressStart2P-Regular.ttf");
            font = javafx.scene.text.Font.loadFont(new FileInputStream(fontFile), 20);
        } catch (FileNotFoundException e) {
            System.out.println("Font file not found");
        }
    }

    public ScoreDisplay(GameState gameState) {
        super(10, 10);
        gameState.addObserver(this);
    }

    @Override
    public void setPane(Pane pane) {
        boardPane = pane;
    }


    @Override
    public void update(GameState gameState) {
        int newScore = gameState.getTotalScore();
        if (newScore != displayedScore) {
            displayedScore = newScore;
            render();
        }
    }



    @Override
    public void render() {
        boardPane.getChildren().removeIf(node -> node instanceof Text);
        Text scoreText = new Text(10, 30, "Score: " + displayedScore);
        scoreText.setFont(javafx.scene.text.Font.font("Press Start 2P", 20));
        scoreText.setFill(javafx.scene.paint.Color.WHITE);
        boardPane.getChildren().add(scoreText);
    }
}
