package pacman.view.observer;

import java.util.ArrayList;
import java.util.List;

public class GameState implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private static int totalScore;
    private static int totalLives = 3;
    private static boolean gameOver;
    private static boolean gameWon;


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

    public void setTotalLives(int totalLives) {
        if (totalLives != GameState.totalLives) {
            GameState.totalLives = totalLives;
            notifyObservers();
        }
    }

    public static int getTotalLives() {
        return totalLives;
    }

}
