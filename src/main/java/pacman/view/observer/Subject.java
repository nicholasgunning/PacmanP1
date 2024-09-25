package pacman.view.observer;

/**
 * Subject interface for the Observer design pattern.
 * Classes that implement this interface can be observed by Observer objects.
 * This interface defines methods to manage observers and notify them of changes.
 */
public interface Subject {

    /**
     * Adds an observer to the list of observers for this subject.
     *
     * @param observer The Observer to be added.
     */
    void addObserver(Observer observer);

    /**
     * Removes an observer from the list of observers for this subject.
     *
     * @param observer The Observer to be removed.
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all registered observers that the subject's state has changed.
     * This method should be called whenever a change occurs that observers might be interested in.
     */
    void notifyObservers();
}