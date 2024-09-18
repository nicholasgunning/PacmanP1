package pacman.model.entity.factory;

public interface EntityFactory {
    Renderable createEntity(char type, int x, int y);
}

