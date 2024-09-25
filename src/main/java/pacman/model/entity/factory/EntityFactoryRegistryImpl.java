package pacman.model.entity.factory;

import pacman.model.maze.RenderableType;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the EntityFactoryRegistry interface.
 * This class manages a collection of EntityFactory instances and delegates
 * entity creation to the appropriate factory based on the entity type.
 */
public class EntityFactoryRegistryImpl implements EntityFactoryRegistry {
    // Map to store factories for each entity type
    private final Map<Character, EntityFactory> factoryRegistry = new HashMap<>();

    /**
     * Constructor initializes the factory registry with factories for all entity types.
     */
    public EntityFactoryRegistryImpl() {
        // Register factories for each entity type
        factoryRegistry.put(RenderableType.PACMAN, new PacmanFactory());
        factoryRegistry.put(RenderableType.GHOST, new GhostFactory());
        factoryRegistry.put(RenderableType.HORIZONTAL_WALL, new WallFactory());
        factoryRegistry.put(RenderableType.VERTICAL_WALL, new WallFactory());
        factoryRegistry.put(RenderableType.UP_LEFT_WALL, new WallFactory());
        factoryRegistry.put(RenderableType.UP_RIGHT_WALL, new WallFactory());
        factoryRegistry.put(RenderableType.DOWN_LEFT_WALL, new WallFactory());
        factoryRegistry.put(RenderableType.DOWN_RIGHT_WALL, new WallFactory());
        factoryRegistry.put(RenderableType.PELLET, new PelletFactory());
    }

    /**
     * Creates an entity using the appropriate factory based on the entity type.
     *
     * @param type The character representing the type of entity to create.
     * @param x The x-coordinate where the entity should be placed.
     * @param y The y-coordinate where the entity should be placed.
     * @return A Renderable object representing the created entity.
     * @throws IllegalArgumentException if the entity type is unknown.
     */
    @Override
    public Renderable createEntity(char type, int x, int y) {
        EntityFactory factory = factoryRegistry.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
        return factory.createEntity(type, x, y);
    }
}