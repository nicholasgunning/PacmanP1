package pacman;

import javafx.application.Application;
import javafx.stage.Stage;
import pacman.model.engine.GameEngine;
import pacman.model.engine.GameEngineImpl;
import pacman.view.GameWindow;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameEngine model = GameEngineImpl.getInstance("src/main/resources/config.json");
        GameWindow window = new GameWindow(model, 448, 576);

        primaryStage.setTitle("Pac-Man");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:    model.moveUp(); break;
                case DOWN:  model.moveDown(); break;
                case LEFT:  model.moveLeft(); break;
                case RIGHT: model.moveRight(); break;
            }
        });

        window.run();
    }



}