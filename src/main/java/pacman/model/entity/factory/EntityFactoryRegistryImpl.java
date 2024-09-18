package pacman.model.entity.factory;

import pacman.model.maze.RenderableType;

import java.util.HashMap;
import java.util.Map;

public class EntityFactoryRegistryImpl implements EntityFactoryRegistry {
    private final Map<Character, EntityFactory> factoryRegistry = new HashMap<>();

    public EntityFactoryRegistryImpl() {
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

    @Override
    public Renderable createEntity(char type, int x, int y) {
        EntityFactory factory = factoryRegistry.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
        return factory.createEntity(type, x, y);
    }
}