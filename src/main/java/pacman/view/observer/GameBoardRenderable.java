package pacman.view.observer;

import javafx.scene.Node;

import java.util.ArrayList;

import javafx.scene.layout.Pane;



public abstract class GameBoardRenderable {
    int width;
    int height;
    private Pane pane;


    private static ArrayList<GameBoardRenderable> gameBoardRenderables = new ArrayList<>();

    public GameBoardRenderable(int width, int height) {
        this.width = width;
        this.height = height;
        gameBoardRenderables.add(this);
    }


    public abstract void setPane(Pane pane);


    // Abstract method that must be implemented by subclasses
    public abstract void render();

    // Method to access all renderable objects
    public static ArrayList<GameBoardRenderable> getAllRenderables() {
        return gameBoardRenderables;
    }

}
