package pacman.view.observer;

import java.util.ArrayList;
import java.util.List;

public class GameState implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private static int totalScore;
    private static int totalLives = 3;
    private static boolean gameOver;
    private static boolean gameWon;
    private static boolean GameReady;
    private int currentLevel;



    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public static int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        if (totalScore != GameState.totalScore) {
            GameState.totalScore = totalScore;
            notifyObservers();
        }
    }

    public static int getTotalLives() {
        return totalLives;
    }

    public void setTotalLives(int totalLives) {
        if (totalLives != GameState.totalLives) {
            GameState.totalLives = totalLives;
            notifyObservers();
        }
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static boolean isGameReady() {
        return GameReady;
    }

    public static boolean isGameWon() {
        return gameWon;
    }

    public void setGameOver(boolean gameOver) {
        if (gameOver != GameState.gameOver) {
            GameState.gameOver = gameOver;
            notifyObservers();
        }
    }

    public void setGameReady(boolean gameReady) {
        if (gameReady != GameState.GameReady) {
            GameState.GameReady = gameReady;
            notifyObservers();
        }
    }

    public void setGameWon(boolean gameWon) {
        if (gameWon != GameState.gameWon) {
            GameState.gameWon = gameWon;
            notifyObservers();
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        notifyObservers();
    }



}
