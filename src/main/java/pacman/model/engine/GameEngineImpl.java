package pacman.model.engine;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pacman.model.entity.factory.Renderable;
import pacman.model.level.Level;
import pacman.model.level.LevelImpl;
import pacman.model.maze.Maze;
import pacman.model.maze.MazeCreator;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of GameEngine - responsible for coordinating the Pac-Man model
 * This class uses the Singleton pattern to ensure only one instance exists.
 */
public class GameEngineImpl implements GameEngine {

    private Level currentLevel;
    private int numLevels;
    private final int currentLevelNo;
    private Maze maze;
    private List<JSONObject> levelConfigs;

    /**
     * Private constructor for Singleton pattern.
     * Initializes the game engine with the given configuration.
     * @param configPath Path to the game configuration file
     */
    private GameEngineImpl(String configPath) {
        this.currentLevelNo = 0;
        init(new GameConfigurationReader(configPath));
    }

    /**
     * Static method to get the single instance of GameEngineImpl.
     * @param configPath Path to the game configuration file
     * @return The GameEngineImpl instance
     */
    public static GameEngineImpl getInstance(String configPath){
        return new GameEngineImpl(configPath);
    }

    /**
     * Initializes the game engine with the provided configuration.
     * Sets up the maze and loads level configurations.
     * @param gameConfigurationReader Reader for game configuration
     */
    private void init(GameConfigurationReader gameConfigurationReader) {
        // Set up map
        String mapFile = gameConfigurationReader.getMapFile();
        MazeCreator mazeCreator = new MazeCreator(mapFile);
        this.maze = mazeCreator.createMaze();
        this.maze.setNumLives(gameConfigurationReader.getNumLives());

        // Get level configurations
        JSONArray levelConfigArray = gameConfigurationReader.getLevelConfigs();
        this.levelConfigs = new ArrayList<>();
        for (Object obj : levelConfigArray) {
            this.levelConfigs.add((JSONObject) obj);
        }
        this.numLevels = levelConfigs.size();
        if (levelConfigs.isEmpty()) {
            System.exit(0);
        }
    }

    @Override
    public List<Renderable> getRenderables() {
        return this.currentLevel.getRenderables();
    }

    @Override
    public void moveUp() {
        currentLevel.moveUp();
    }

    @Override
    public void moveDown() {
        currentLevel.moveDown();
    }

    @Override
    public void moveLeft() {
        currentLevel.moveLeft();
    }

    @Override
    public void moveRight() {
        currentLevel.moveRight();
    }

    @Override
    public void startGame() {
        startLevel();
    }

    /**
     * Starts a new level by resetting the maze and creating a new Level instance.
     */
    private void startLevel() {
        maze.reset();
        this.currentLevel = new LevelImpl(levelConfigs, maze);
    }

    @Override
    public void tick() {
        currentLevel.tick();
    }
}