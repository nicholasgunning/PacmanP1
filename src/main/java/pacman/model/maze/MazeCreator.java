package pacman.model.maze;

import pacman.model.entity.factory.EntityFactoryRegistry;
import pacman.model.entity.factory.EntityFactoryRegistryImpl;
import pacman.model.entity.factory.Renderable;
import pacman.model.entity.dynamic.physics.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Responsible for creating renderables and storing them in the Maze
 */
public class MazeCreator {

    private final String fileName;
    public static final int RESIZING_FACTOR = 16;
    private final EntityFactoryRegistry entityFactoryRegistry;

    // These builders are not used in the current implementation
    private KinematicStateImpl.KinematicStateBuilder pacmanKinematicStateBuilder = new KinematicStateImpl.KinematicStateBuilder();
    private KinematicStateImpl.KinematicStateBuilder ghostKinematicStateBuilder = new KinematicStateImpl.KinematicStateBuilder();

    /**
     * Constructor for MazeCreator
     * @param fileName The name of the file containing the maze layout
     */
    public MazeCreator(String fileName) {
        this.entityFactoryRegistry = new EntityFactoryRegistryImpl();
        this.fileName = fileName;
    }

    /**
     * Creates and returns a Maze object based on the file content
     * @return Maze object containing all the game entities
     */
    public Maze createMaze() {
        File f = new File(this.fileName);
        Maze maze = new Maze();

        try {
            Scanner scanner = new Scanner(f);
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] row = line.toCharArray();
                for (int x = 0; x < row.length; x++) {
                    char c = row[x];
                    // Create entity based on character type
                    if (c == RenderableType.PACMAN) {
                        createAndAddEntity(maze, c, x, y);
                    } else if (c == RenderableType.GHOST) {
                        createAndAddEntity(maze, c, x, y);
                    } else if (c == RenderableType.PELLET) {
                        createAndAddEntity(maze, c, x, y);
                    } else if (c == RenderableType.HORIZONTAL_WALL || c == RenderableType.VERTICAL_WALL ||
                            c == RenderableType.UP_LEFT_WALL || c == RenderableType.UP_RIGHT_WALL ||
                            c == RenderableType.DOWN_LEFT_WALL || c == RenderableType.DOWN_RIGHT_WALL) {
                        createAndAddEntity(maze, c, x, y);
                    }
                }
                y += 1;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No maze file was found.");
            exit(0);
        } catch (Exception e) {
            System.out.println("Error");
            exit(0);
        }
        return maze;
    }

    /**
     * Helper method to create and add an entity to the maze
     * @param maze The Maze object to add the entity to
     * @param type The character type of the entity
     * @param x The x-coordinate of the entity
     * @param y The y-coordinate of the entity
     */
    private void createAndAddEntity(Maze maze, char type, int x, int y) {
        Renderable entity = entityFactoryRegistry.createEntity(type, x * RESIZING_FACTOR, y * RESIZING_FACTOR);
        if (entity != null) {
            maze.addRenderable(entity, type, x, y);
        }
    }
}