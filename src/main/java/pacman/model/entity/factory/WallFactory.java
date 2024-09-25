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

/**
 * Factory class for creating Wall entities.
 */
public class WallFactory implements EntityFactory {

    // Resizing factor for the wall image. Ensure this matches MazeCreator.RESIZING_FACTOR.
    private static final int RESIZING_FACTOR = 16;

    /**
     * Creates a Wall entity based on the provided type and coordinates.
     *
     * @param type the type of wall to create
     * @param x the x-coordinate for the wall's position
     * @param y the y-coordinate for the wall's position
     * @return a Renderable Wall entity or null if an error occurs
     */
    @Override
    public Renderable createEntity(char type, int x, int y) {
        try {
            // Get the image path for the specified wall type.
            String imagePath = getWallImagePath(type);
            // Load the wall image from the resources directory.
            Image wallImage = new Image(new FileInputStream(imagePath));
            // Create a position vector for the wall.
            Vector2D position = new Vector2D(x, y); // Note: x and y are already multiplied by RESIZING_FACTOR in MazeCreator
            // Create a bounding box for the wall.
            BoundingBox boundingBox = new BoundingBoxImpl(position, RESIZING_FACTOR, RESIZING_FACTOR);
            // Create and return a new StaticEntity representing the wall.
            StaticEntity wall = new StaticEntityImpl(boundingBox, Renderable.Layer.BACKGROUND, wallImage);

            return wall;
        } catch (FileNotFoundException e) {
            // Print an error message if the wall image file is not found.
            System.out.println("Error loading wall image: " + e.getMessage());
        }
        // Return null if wall creation fails.
        return null;
    }

    /**
     * Gets the image path for the specified wall type.
     *
     * @param type the type of wall
     * @return the file path to the wall image
     */
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