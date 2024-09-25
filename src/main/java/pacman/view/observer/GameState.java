package pacman.view.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the current state of the game.
 * Implements the Subject interface to allow observers to monitor game state changes.
 */
public class GameState implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private static int totalScore;
    private static int totalLives = 3;
    private static boolean gameOver;
    private static boolean gameWon;
    private static boolean GameReady;
    private int currentLevel;

    /**
     * Adds an observer to the list of observers.
     * @param observer The observer to add
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers.
     * @param observer The observer to remove
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of a state change.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    /**
     * Gets the total score.
     * @return The total score
     */
    public static int getTotalScore() {
        return totalScore;
    }

    /**
     * Sets the total score and notifies observers if changed.
     * @param totalScore The new total score
     */
    public void setTotalScore(int totalScore) {
        if (totalScore != GameState.totalScore) {
            GameState.totalScore = totalScore;
            notifyObservers();
        }
    }

    /**
     * Gets the total lives.
     * @return The total lives
     */
    public static int getTotalLives() {
        return totalLives;
    }

    /**
     * Sets the total lives and notifies observers if changed.
     * @param totalLives The new total lives
     */
    public void setTotalLives(int totalLives) {
        if (totalLives != GameState.totalLives) {
            GameState.totalLives = totalLives;
            notifyObservers();
        }
    }

    /**
     * Checks if the game is over.
     * @return True if the game is over, false otherwise
     */
    public static boolean isGameOver() {
        return gameOver;
    }

    /**
     * Checks if the game is ready to start.
     * @return True if the game is ready, false otherwise
     */
    public static boolean isGameReady() {
        return GameReady;
    }

    /**
     * Checks if the game is won.
     * @return True if the game is won, false otherwise
     */
    public static boolean isGameWon() {
        return gameWon;
    }

    /**
     * Sets the game over state and notifies observers if changed.
     * @param gameOver The new game over state
     */
    public void setGameOver(boolean gameOver) {
        if (gameOver != GameState.gameOver) {
            GameState.gameOver = gameOver;
            notifyObservers();
        }
    }

    /**
     * Sets the game ready state and notifies observers if changed.
     * @param gameReady The new game ready state
     */
    public void setGameReady(boolean gameReady) {
        if (gameReady != GameState.GameReady) {
            GameState.GameReady = gameReady;
            notifyObservers();
        }
    }

    /**
     * Sets the game won state and notifies observers if changed.
     * @param gameWon The new game won state
     */
    public void setGameWon(boolean gameWon) {
        if (gameWon != GameState.gameWon) {
            GameState.gameWon = gameWon;
            notifyObservers();
        }
    }

    /**
     * Gets the current level.
     * @return The current level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets the current level and notifies observers.
     * @param currentLevel The new current level
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        notifyObservers();
    }
}