package pacman.model.entity.factory;

import javafx.scene.image.Image;
import pacman.model.entity.factory.Renderable;
import pacman.model.entity.dynamic.player.Pacman;
import pacman.model.entity.dynamic.player.PacmanVisual;
import pacman.model.entity.dynamic.physics.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class PacmanFactory implements EntityFactory {

    private static final int RESIZING_FACTOR = 16; // Make sure this matches your MazeCreator.RESIZING_FACTOR

    @Override
    public Renderable createEntity(char type, int x, int y) {
        try {

            // Load Pacman images
            Map<PacmanVisual, Image> pacmanImages = loadPacmanImages();

            // Set up Pacman position and bounding box
            Vector2D position = new Vector2D(x, y); // Note: x and y are already multiplied by RESIZING_FACTOR in MazeCreator
            BoundingBox boundingBox = new BoundingBoxImpl(position, RESIZING_FACTOR + 5, RESIZING_FACTOR + 5);

            // Set up Pacman kinematic state
            KinematicState pacmanKinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(position)
                    .setSpeed(0.001)
                    .setDirection(Direction.UP)
                    .build();

            // Create Pacman
            Pacman pacman = Pacman.getInstance(pacmanImages.get(PacmanVisual.CLOSED), pacmanImages, boundingBox, pacmanKinematicState);

            return pacman;
        } catch (FileNotFoundException e) {
            System.out.println("Pacman image file not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error creating Pacman: " + e.getMessage());
        }
        return null; // Return null if Pacman creation fails
    }

    private Map<PacmanVisual, Image> loadPacmanImages() throws FileNotFoundException {
        Map<PacmanVisual, Image> pacmanImages = new HashMap<>();
        pacmanImages.put(PacmanVisual.CLOSED, new Image(new FileInputStream("src/main/resources/maze/pacman/playerClosed.png")));
        pacmanImages.put(PacmanVisual.UP, new Image(new FileInputStream("src/main/resources/maze/pacman/playerUp.png")));
        pacmanImages.put(PacmanVisual.DOWN, new Image(new FileInputStream("src/main/resources/maze/pacman/playerDown.png")));
        pacmanImages.put(PacmanVisual.LEFT, new Image(new FileInputStream("src/main/resources/maze/pacman/playerLeft.png")));
        pacmanImages.put(PacmanVisual.RIGHT, new Image(new FileInputStream("src/main/resources/maze/pacman/playerRight.png")));
        return pacmanImages;
    }
}