package pacman.model.entity.factory;

import javafx.scene.image.Image;
import pacman.model.entity.factory.Renderable;
import pacman.model.entity.staticentity.collectable.Pellet;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

import java.io.FileInputStream;

/**
 * Factory class for creating Pellet entities.
 */
public class PelletFactory implements EntityFactory {

    // Resizing factor for the pellet image. Ensure this matches MazeCreator.RESIZING_FACTOR.
    private static final int RESIZING_FACTOR = 16;
    // Points awarded for collecting a pellet. This value is configurable.
    private static final int PELLET_POINTS = 100;

    /**
     * Creates a Pellet entity based on the provided type and coordinates.
     *
     * @param type the type of entity to create (not used in this implementation)
     * @param x the x-coordinate for the pellet's position
     * @param y the y-coordinate for the pellet's position
     * @return a Renderable Pellet entity or null if an error occurs
     */
    @Override
    public Renderable createEntity(char type, int x, int y) {
        try {
            // Load the pellet image from the resources directory.
            Image pelletImage = new Image(new FileInputStream("src/main/resources/maze/pellet.png"));
            // Create a position vector for the pellet.
            Vector2D position = new Vector2D(x, y); // Note: x and y are already multiplied by RESIZING_FACTOR in MazeCreator
            // Create a bounding box for the pellet.
            BoundingBox boundingBox = new BoundingBoxImpl(position, RESIZING_FACTOR/1.25, RESIZING_FACTOR/1.25);
            // Create and return a new Pellet entity.
            Pellet pellet = new Pellet(boundingBox, Renderable.Layer.FOREGROUND, pelletImage, PELLET_POINTS);
            return pellet;
        } catch (Exception e) {
            // Print an error message and return null if an exception occurs.
            System.out.println("Error creating pellet: " + e.getMessage());
            return null;
        }
    }
}