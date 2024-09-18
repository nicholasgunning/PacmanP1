package pacman.model.entity.factory;

import javafx.scene.image.Image;
import pacman.model.entity.factory.Renderable;
import pacman.model.entity.staticentity.StaticEntity;
import pacman.model.entity.staticentity.StaticEntityImpl;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.maze.RenderableType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class WallFactory implements EntityFactory {

    private static final int RESIZING_FACTOR = 16; // Make sure this matches your MazeCreator.RESIZING_FACTOR

    @Override
    public Renderable createEntity(char type, int x, int y) {
        try {
            String imagePath = getWallImagePath(type);
            Image wallImage = new Image(new FileInputStream(imagePath));
            Vector2D position = new Vector2D(x, y); // Note: x and y are already multiplied by RESIZING_FACTOR in MazeCreator
            BoundingBox boundingBox = new BoundingBoxImpl(position, RESIZING_FACTOR, RESIZING_FACTOR);
            StaticEntity wall = new StaticEntityImpl(boundingBox, Renderable.Layer.BACKGROUND, wallImage);

            System.out.println("Wall added at (" + x / RESIZING_FACTOR + ", " + y / RESIZING_FACTOR + ")");

            return wall;
        } catch (FileNotFoundException e) {
            System.out.println("Error loading wall image: " + e.getMessage());
        }
        return null; // Return null if wall creation fails
    }

    private String getWallImagePath(char type) {
        switch (type) {
            case RenderableType.HORIZONTAL_WALL:
                return "src/main/resources/maze/walls/horizontal.png";
            case RenderableType.VERTICAL_WALL:
                return "src/main/resources/maze/walls/vertical.png";
            case RenderableType.UP_LEFT_WALL:
                return "src/main/resources/maze/walls/upLeft.png";
            case RenderableType.UP_RIGHT_WALL:
                return "src/main/resources/maze/walls/upRight.png";
            case RenderableType.DOWN_LEFT_WALL:
                return "src/main/resources/maze/walls/downLeft.png";
            case RenderableType.DOWN_RIGHT_WALL:
                return "src/main/resources/maze/walls/downRight.png";
            default:
                return "src/main/resources/maze/walls/horizontal.png";
        }
    }
}