package pacman.model.level;

import javafx.application.Platform;
import org.json.simple.JSONObject;
import pacman.ConfigurationParseException;
import pacman.model.commandPattern.*;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.entity.factory.Renderable;
import pacman.model.entity.dynamic.DynamicEntity;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.PhysicsEngine;
import pacman.model.entity.dynamic.player.Controllable;
import pacman.model.entity.dynamic.player.Pacman;
import pacman.model.entity.staticentity.StaticEntity;
import pacman.model.entity.staticentity.collectable.Collectable;
import pacman.model.maze.Maze;
import pacman.view.observer.GameState;
import pacman.view.observer.LivesDisplay;
import pacman.view.observer.ScoreDisplay;
import pacman.view.observer.GameReadyDisplay;
import pacman.view.observer.GameOverDisplay;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Concrete implement of Pac-Man level
 */
public class LevelImpl implements Level {

    private static final int START_LEVEL_TIME = 100;
    private final Maze maze;
    private List<Renderable> renderables;
    private Controllable player;
    private List<Ghost> ghosts;
    private int tickCount;
    private Map<GhostMode, Integer> modeLengths;
    private int numLives;
    private List<Renderable> collectables;
    private GhostMode currentGhostMode;
    private CommandInvoker commandInvoker;
    private int secondsCount;
    private GameState gameState;
    private ScoreDisplay scoreDisplay;
    private LivesDisplay livesDisplay;
    private GameReadyDisplay gameReadyDisplay;
    private GameOverDisplay gameOverDisplay;
    private final int points;
    private boolean freezeGame = false;

    public LevelImpl(JSONObject levelConfiguration,
                     Maze maze) {
        this.renderables = new ArrayList<>();
        this.maze = maze;
        this.tickCount = 0;
        this.modeLengths = new HashMap<>();
        this.currentGhostMode = GhostMode.SCATTER;
        initLevel(new LevelConfigurationReader(levelConfiguration));
        initCommands();
        points = 100;
        gameState = new GameState();
        scoreDisplay = new ScoreDisplay(gameState);
        livesDisplay = new LivesDisplay(gameState);
        gameReadyDisplay = new GameReadyDisplay(gameState);
        gameOverDisplay = new GameOverDisplay(gameState);
    }

    static {
        javafx.scene.text.Font font = null;
        try {
            File fontFile = new File("src/main/resources/maze/PressStart2P-Regular.ttf");
            font = javafx.scene.text.Font.loadFont(new FileInputStream(fontFile), 20);
        } catch (FileNotFoundException e) {
            System.out.println("Font file not found");
        }
    }

    private void initCommands() {
        this.commandInvoker = new CommandInvoker();
        this.commandInvoker.addCommand("UP", new MoveUpCommand(player));
        this.commandInvoker.addCommand("DOWN", new MoveDownCommand(player));
        this.commandInvoker.addCommand("LEFT", new MoveLeftCommand(player));
        this.commandInvoker.addCommand("RIGHT", new MoveRightCommand(player));
    }

    private void initLevel(LevelConfigurationReader levelConfigurationReader) {
        // Fetch all renderables for the level
        this.renderables = maze.getRenderables();
        // Set up player
        if (!(maze.getControllable() instanceof Controllable)) {
            throw new ConfigurationParseException("Player entity is not controllable");
        }
        this.player = (Controllable) maze.getControllable();
        this.player.setSpeed(levelConfigurationReader.getPlayerSpeed());
        setNumLives(maze.getNumLives());
        // Set up ghosts
        this.ghosts = maze.getGhosts().stream()
                .map(element -> (Ghost) element)
                .collect(Collectors.toList());
        Map<GhostMode, Double> ghostSpeeds = levelConfigurationReader.getGhostSpeeds();

        for (Ghost ghost : this.ghosts) {
            ghost.setSpeeds(ghostSpeeds);
            ghost.setGhostMode(this.currentGhostMode);
        }
        this.modeLengths = levelConfigurationReader.getGhostModeLengths();
        // Set up collectables
        this.collectables = new ArrayList<>(maze.getPellets());
    }

    @Override
    public List<Renderable> getRenderables() {
        return this.renderables;
    }

    private List<DynamicEntity> getDynamicEntities() {
        return renderables.stream().filter(e -> e instanceof DynamicEntity).map(e -> (DynamicEntity) e).collect(
                Collectors.toList());
    }

    private List<StaticEntity> getStaticEntities() {
        return renderables.stream().filter(e -> e instanceof StaticEntity).map(e -> (StaticEntity) e).collect(
                Collectors.toList());
    }

    @Override
    public void tick() {

        if (freezeGame) {
            return;
        }

        if (tickCount < START_LEVEL_TIME) {
            gameState.setGameReady(true);
            tickCount++;
            return;
        } else {
            gameState.setGameReady(false);
        }

        Vector2D pacmanPosition = this.player.getCenter();
        for (Ghost ghost : this.ghosts) {
            ghost.updatePlayerPosition(pacmanPosition);
        }

        //calculate seconds
        if (tickCount % 60 == 0) {
            secondsCount++;
            System.out.println("Seconds: " + secondsCount);
        }

        if (secondsCount == modeLengths.get(currentGhostMode)) {
            // update ghost mode
            System.out.println("Switching ghost mode");
            this.currentGhostMode = GhostMode.getNextGhostMode(currentGhostMode);
            for (Ghost ghost : this.ghosts) {
                ghost.setGhostMode(this.currentGhostMode);
            }
            secondsCount = 0;
        }

        if (tickCount % Pacman.PACMAN_IMAGE_SWAP_TICK_COUNT == 0) {
            this.player.switchImage();
        }

        // Update the dynamic entities
        List<DynamicEntity> dynamicEntities = getDynamicEntities();

        for (DynamicEntity dynamicEntity : dynamicEntities) {
            maze.updatePossibleDirections(dynamicEntity);
            dynamicEntity.update();
        }

        for (int i = 0; i < dynamicEntities.size(); ++i) {
            DynamicEntity dynamicEntityA = dynamicEntities.get(i);
            // handle collisions between dynamic entities
            for (int j = i + 1; j < dynamicEntities.size(); ++j) {
                DynamicEntity dynamicEntityB = dynamicEntities.get(j);

                if (dynamicEntityA.collidesWith(dynamicEntityB) ||
                        dynamicEntityB.collidesWith(dynamicEntityA)) {
                    dynamicEntityA.collideWith(this, dynamicEntityB);
                    dynamicEntityB.collideWith(this, dynamicEntityA);
                }
            }

            // handle collisions between dynamic entities and static entities
            for (StaticEntity staticEntity : getStaticEntities()) {
                if (dynamicEntityA.collidesWith(staticEntity)) {
                    dynamicEntityA.collideWith(this, staticEntity);
                    PhysicsEngine.resolveCollision(dynamicEntityA, staticEntity);
                }
            }
        }
        tickCount++;
    }

    @Override
    public boolean isPlayer(Renderable renderable) {
        return renderable == this.player;
    }

    @Override
    public boolean isCollectable(Renderable renderable) {
        return maze.getPellets().contains(renderable) && ((Collectable) renderable).isCollectable();
    }

    @Override
    public void moveLeft() {
        commandInvoker.executeCommand("LEFT");
    }

    @Override
    public void moveRight() {
        commandInvoker.executeCommand("RIGHT");
    }

    @Override
    public void moveUp() {
        commandInvoker.executeCommand("UP");
    }

    @Override
    public void moveDown() {
        commandInvoker.executeCommand("DOWN");
    }

    @Override
    public boolean isLevelFinished() {
        return collectables.isEmpty();
    }

    @Override
    public int getNumLives() {
        return this.numLives;
    }

    private void setNumLives(int numLives) {
        this.numLives = numLives;
    }

    @Override
    public void handleLoseLife() {
        int newLives = gameState.getTotalLives() - 1;

        if (newLives == -1) {
            handleGameEnd();
            return;
        }

        gameState.setTotalLives(newLives);
        for (Ghost ghost : this.ghosts) {
            ghost.resetAfterDeath();
        }
        this.player.reset();
        this.tickCount = 0;
    }

    @Override
    public void handleGameEnd() {
        gameState.setGameOver(true);
        freezeGame = true;
    }

    @Override
    public void collect(Collectable collectable) {
        int newScore = gameState.getTotalScore() + points;
        gameState.setTotalScore(newScore);
        collectables.remove(collectable);
        if (collectables.isEmpty()) {
            nextLevel();
        }
    }

    public void nextLevel() {
        //HANDLE NEXT LEVEL
    }
}
