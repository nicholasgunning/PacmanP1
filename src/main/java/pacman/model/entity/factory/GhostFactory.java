package pacman.model.entity.factory;

import javafx.scene.image.Image;
import pacman.model.entity.factory.Renderable;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GhostFactory implements EntityFactory {

    private static final int RESIZING_FACTOR = 16;
    private static final Vector2D[] CORNERS = {
            new Vector2D(0, 0),  // top-left corner
            new Vector2D(0, 576),  // bottom-left corner
            new Vector2D(448, 0),  // top-right corner
            new Vector2D(448, 576)  // bottom-right corner
    };

    @Override
    public Renderable createEntity(char type, int x, int y) {
        try {
            // Load ghost image
            Image ghostImage = new Image(new FileInputStream("src/main/resources/maze/ghosts/ghost.png"));

            // Set up ghost position and bounding box
            Vector2D position = new Vector2D(x, y); // Note: x and y are already multiplied by RESIZING_FACTOR in MazeCreator
            BoundingBox boundingBox = new BoundingBoxImpl(position, RESIZING_FACTOR + 5, RESIZING_FACTOR + 5);

            // Set up ghost kinematic state
            KinematicState ghostKinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(position)
                    .setSpeed(1.0)
                    .setDirection(Direction.RIGHT)
                    .build();

            // Set up ghost target corner

            // Randomly select a corner for the ghost to target
            Vector2D targetCorner = CORNERS[(int) (Math.random() * CORNERS.length)];
            Vector2D intialTarget = new Vector2D(100, 100);

            // Create ghost
            Ghost ghost = new GhostImpl(ghostImage, boundingBox, ghostKinematicState,
                    GhostMode.CHASE, targetCorner, Direction.UP, intialTarget);


            return ghost;
        } catch (FileNotFoundException e) {
            System.out.println("Ghost image file not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error creating ghost: " + e.getMessage());
            e.printStackTrace();  // This will print the full stack trace for debugging
        }
        return null; // Return null if ghost creation fails
    }
}