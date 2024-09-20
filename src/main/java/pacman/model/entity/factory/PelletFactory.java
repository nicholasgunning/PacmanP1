package pacman.model.entity.factory;

import javafx.scene.image.Image;
import pacman.model.entity.factory.Renderable;
import pacman.model.entity.staticentity.collectable.Pellet;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

import java.io.FileInputStream;

public class PelletFactory implements EntityFactory {

    private static final int RESIZING_FACTOR = 16; // Make sure this matches your MazeCreator.RESIZING_FACTOR
    private static final int PELLET_POINTS = 10; // configurable
    @Override
    public Renderable createEntity(char type, int x, int y) {
        try {
            Image pelletImage = new Image(new FileInputStream("src/main/resources/maze/pellet.png"));
            Vector2D position = new Vector2D(x, y); // Note: x and y are already multiplied by RESIZING_FACTOR in MazeCreator
            BoundingBox boundingBox = new BoundingBoxImpl(position, RESIZING_FACTOR/1.25, RESIZING_FACTOR/1.25);
            Pellet pellet = new Pellet(boundingBox, Renderable.Layer.FOREGROUND, pelletImage, PELLET_POINTS);
            return pellet;
        } catch (Exception e) {
            System.out.println("Error creating pellet: " + e.getMessage());
            return null;
        }
    }
}