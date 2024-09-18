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
 * Responsible for creating renderables and storing it in the Maze
 */
public class MazeCreator {

    private final String fileName;
    public static final int RESIZING_FACTOR = 16;
    private final EntityFactoryRegistry entityFactoryRegistry;



    private KinematicStateImpl.KinematicStateBuilder pacmanKinematicStateBuilder = new KinematicStateImpl.KinematicStateBuilder();

    private KinematicStateImpl.KinematicStateBuilder ghostKinematicStateBuilder = new KinematicStateImpl.KinematicStateBuilder();

    public MazeCreator(String fileName) {
        this.entityFactoryRegistry = new EntityFactoryRegistryImpl();
        this.fileName = fileName;
    }

    public Maze createMaze() {
        /**
         * TO DO: Implement Factory Method Pattern
         */
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
                    if (c == RenderableType.PACMAN) {
                        Renderable pacman = entityFactoryRegistry.createEntity(c, x * RESIZING_FACTOR, y * RESIZING_FACTOR);
                        if (pacman != null) {
                            maze.addRenderable(pacman, c, x, y);
                        }
                    } else if (c == RenderableType.GHOST) {

                        Renderable ghost = entityFactoryRegistry.createEntity(c, x * RESIZING_FACTOR, y * RESIZING_FACTOR);
                        if (ghost != null) {
                            maze.addRenderable(ghost, c, x, y);
                        }

                    } else if (c == RenderableType.PELLET) {
                        Renderable pellet = entityFactoryRegistry.createEntity(c, x * RESIZING_FACTOR, y * RESIZING_FACTOR);
                        if (pellet != null) {
                            maze.addRenderable(pellet, c, x, y);
                        }
                    } else if (c == RenderableType.HORIZONTAL_WALL || c == RenderableType.VERTICAL_WALL ||
                            c == RenderableType.UP_LEFT_WALL || c == RenderableType.UP_RIGHT_WALL ||
                            c == RenderableType.DOWN_LEFT_WALL || c == RenderableType.DOWN_RIGHT_WALL) {
                        Renderable wall = entityFactoryRegistry.createEntity(c, x * RESIZING_FACTOR, y * RESIZING_FACTOR);
                        if (wall != null) {
                            maze.addRenderable(wall, c, x, y);
                        }
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
}