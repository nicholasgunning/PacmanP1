package pacman.model.entity.dynamic.ghost;

import javafx.scene.image.Image;
import pacman.model.entity.factory.Renderable;
import pacman.model.entity.dynamic.physics.*;
import pacman.model.level.Level;
import pacman.model.maze.Maze;

import java.util.*;

/**
 * Concrete implementation of Ghost entity in Pac-Man Game
 */
public class GhostImpl implements Ghost {

    private final Layer layer = Layer.FOREGROUND;
    private final Image image;
    private final BoundingBox boundingBox;
    private final Vector2D startingPosition;
    private final Vector2D targetCorner;
    private KinematicState kinematicState;
    private GhostMode ghostMode;
    private Vector2D targetLocation;
    private Direction currentDirection;
    private Set<Direction> possibleDirections;
    private Vector2D playerPosition;
    private Map<GhostMode, Double> speeds;
    private final Vector2D intialTarget;
    private boolean isInGhostHouse;

    /**
     * Constructor for GhostImpl
     * @param image The ghost's image
     * @param boundingBox The ghost's bounding box for collision detection
     * @param kinematicState The ghost's initial kinematic state
     * @param ghostMode The initial ghost mode
     * @param targetCorner The corner the ghost targets in scatter mode
     * @param currentDirection The initial direction of the ghost
     * @param intialTarget The initial target position (usually the exit of the ghost house)
     */
    public GhostImpl(Image image, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode, Vector2D targetCorner, Direction currentDirection, Vector2D intialTarget) {
        this.image = image;
        this.boundingBox = boundingBox;
        this.kinematicState = kinematicState;
        this.startingPosition = kinematicState.getPosition();
        this.ghostMode = ghostMode;
        this.currentDirection = currentDirection;
        this.possibleDirections = new HashSet<>();
        this.targetCorner = targetCorner;
        this.targetLocation = getTargetLocation();
        this.intialTarget = intialTarget;
        this.isInGhostHouse = true;
    }

    /**
     * Updates the ghost's knowledge of the player's position
     * @param playerPosition The current position of the player
     */
    @Override
    public void updatePlayerPosition(Vector2D playerPosition) {
        this.playerPosition = playerPosition;
    }

    /**
     * Sets the speeds for different ghost modes
     * @param speeds A map of ghost modes to their corresponding speeds
     */
    @Override
    public void setSpeeds(Map<GhostMode, Double> speeds) {
        this.speeds = speeds;
    }

    /**
     * @return The ghost's image
     */
    @Override
    public Image getImage() {
        return image;
    }

    /**
     * Updates the ghost's position and state
     */
    @Override
    public void update() {
        if (isInGhostHouse) {
            Vector2D currentPosition = this.getCenter();
            if (Vector2D.calculateEuclideanDistance(currentPosition, intialTarget) < 50) {
                isInGhostHouse = false;
            }
        }

        this.updateDirection();
        this.kinematicState.update();
        this.boundingBox.setTopLeft(this.kinematicState.getPosition());
    }

    /**
     * Updates the ghost's direction based on its current mode and position
     */
    private void updateDirection() {
        // Ghosts update their target location when they reach an intersection
        if (Maze.isAtIntersection(this.possibleDirections)) {
            this.targetLocation = getTargetLocation();
        }

        this.currentDirection = selectDirection(possibleDirections);

        switch (currentDirection) {
            case LEFT -> this.kinematicState.left();
            case RIGHT -> this.kinematicState.right();
            case UP -> this.kinematicState.up();
            case DOWN -> this.kinematicState.down();
        }
    }

    /**
     * Determines the ghost's target location based on its current mode
     * @return The target location for the ghost
     */
    private Vector2D getTargetLocation() {
        if (isInGhostHouse) {
            return intialTarget;
        }

        return switch (this.ghostMode) {
            case CHASE -> this.playerPosition != null ? this.playerPosition : this.targetCorner;
            case SCATTER -> this.targetCorner;
        };
    }

    /**
     * Selects the best direction for the ghost to move towards its target
     * @param possibleDirections The set of possible directions the ghost can move
     * @return The selected direction
     */
    private Direction selectDirection(Set<Direction> possibleDirections) {
        if (isInGhostHouse && possibleDirections.contains(Direction.UP)) {
            return Direction.UP;
        }

        if (possibleDirections.isEmpty()) {
            return currentDirection;
        }

        if (targetLocation == null) {
            System.err.println("Warning: Target location is null");
            return currentDirection;
        }

        Map<Direction, Double> distances = new HashMap<>();

        for (Direction direction : possibleDirections) {
            // ghosts never choose to reverse travel
            if (direction != currentDirection.opposite()) {
                Vector2D potentialPosition = this.kinematicState.getPotentialPosition(direction);
                if (potentialPosition != null) {
                    double distance = Vector2D.calculateEuclideanDistance(potentialPosition, this.targetLocation);
                    distances.put(direction, distance);
                } else {
                    System.err.println("Warning: Potential position for direction " + direction + " is null");
                }
            }
        }
        if (distances.isEmpty()) {
            System.err.println("Warning: No valid directions found");
            return currentDirection;
        }

        // select the direction that will reach the target location fastest
        return Collections.min(distances.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /**
     * Sets the ghost's mode and updates its speed accordingly
     * @param ghostMode The new ghost mode
     */
    @Override
    public void setGhostMode(GhostMode ghostMode) {
        this.ghostMode = ghostMode;
        this.kinematicState.setSpeed(speeds.get(ghostMode));
    }

    /**
     * Checks if the ghost collides with another renderable
     * @param renderable The renderable to check collision with
     * @return True if collision occurs, false otherwise
     */
    @Override
    public boolean collidesWith(Renderable renderable) {
        return boundingBox.collidesWith(kinematicState.getDirection(), renderable.getBoundingBox());
    }

    /**
     * Handles collision with another renderable
     * @param level The current game level
     * @param renderable The renderable collided with
     */
    @Override
    public void collideWith(Level level, Renderable renderable) {
        if (level.isPlayer(renderable)) {
            level.handleLoseLife();
        }
    }

    @Override
    public Vector2D getPositionBeforeLastUpdate() {
        return this.kinematicState.getPreviousPosition();
    }

    @Override
    public double getHeight() {
        return this.boundingBox.getHeight();
    }

    @Override
    public double getWidth() {
        return this.boundingBox.getWidth();
    }

    @Override
    public Vector2D getPosition() {
        return this.kinematicState.getPosition();
    }

    @Override
    public void setPosition(Vector2D position) {
        this.kinematicState.setPosition(position);
    }

    @Override
    public Layer getLayer() {
        return this.layer;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public void reset() {
        // return ghost to starting position
        this.kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                .setPosition(startingPosition)
                .build();
    }

    @Override
    public void resetAfterDeath() {
        this.kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                .setPosition(startingPosition)
                .setSpeed(speeds.get(GhostMode.SCATTER)) // Set initial speed
                .setDirection(Direction.UP) // Set initial direction
                .build();
        this.isInGhostHouse = true;
        this.currentDirection = Direction.UP; // or whatever the initial direction should be
        this.ghostMode = GhostMode.CHASE; // Reset to initial mode
        this.targetLocation = getTargetLocation(); // Recalculate target location
        this.possibleDirections.clear(); // Clear possible directions
    }

    @Override
    public void setPossibleDirections(Set<Direction> possibleDirections) {
        this.possibleDirections = possibleDirections;
    }

    @Override
    public Direction getDirection() {
        return this.kinematicState.getDirection();
    }

    @Override
    public Vector2D getCenter() {
        return new Vector2D(boundingBox.getMiddleX(), boundingBox.getMiddleY());
    }
}