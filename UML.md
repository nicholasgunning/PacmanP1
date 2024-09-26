```mermaid
    
    class Maze {
        -renderables: List<Renderable>
        -pacman: Renderable
        -ghosts: List<Renderable>
        -pellets: List<Renderable>
        -isWall: Map<String, Boolean>
        -numLives: int
        +addRenderable(Renderable, char, int, int)
    }

```

