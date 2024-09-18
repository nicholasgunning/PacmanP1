package pacman.model.entity.factory;

public interface EntityFactoryRegistry {
    Renderable createEntity(char type, int x, int y);
}
