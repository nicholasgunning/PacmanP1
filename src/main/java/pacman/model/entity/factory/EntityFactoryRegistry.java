package pacman.model.entity.factory;

/**
 * Interface for the Entity Factory Registry.
 * This interface is part of the Abstract Factory pattern implementation.
 * It provides a centralized way to create different types of entities
 * without exposing the concrete classes for each entity type.
 */
public interface EntityFactoryRegistry {

    /**
     * Creates and returns a Renderable entity based on the provided type and position.
     *
     * @param type The character representing the type of entity to create.
     *             Different characters correspond to different entity types
     *             (e.g., 'p' for Pacman, 'g' for Ghost, etc.).
     * @param x The x-coordinate where the entity should be placed in the game world.
     * @param y The y-coordinate where the entity should be placed in the game world.
     * @return A Renderable object representing the created entity.
     */
    Renderable createEntity(char type, int x, int y);
}