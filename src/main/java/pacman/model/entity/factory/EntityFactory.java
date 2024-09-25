package pacman.model.entity.factory;

/**
 * Interface for the Entity Factory.
 * This interface is part of the Factory Method pattern implementation.
 * It defines a method for creating game entities based on their type and position.
 */
public interface EntityFactory {

    /**
     * Creates and returns a Renderable entity.
     *
     * @param type The character representing the type of entity to create.
     *             Different characters correspond to different entity types
     *             (e.g., 'p' for Pacman, 'g' for Ghost, etc.).
     * @param x The x-coordinate where the entity should be placed.
     * @param y The y-coordinate where the entity should be placed.
     * @return A Renderable object representing the created entity.
     */
    Renderable createEntity(char type, int x, int y);
}
