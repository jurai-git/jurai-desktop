package io.jurai.data;

import io.jurai.data.model.Advogado;
import io.jurai.data.model.Pane;
import io.jurai.util.StateLogger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ApplicationState {
    private static final PropertyChangeSupport support = new PropertyChangeSupport(new ApplicationState());

    private static boolean loggedIn = false;
    private static Pane activePane = Pane.HomePane;
    private static Advogado currentUser = null;

    public static void initialize() {
        support.addPropertyChangeListener(propertyChangeEvent -> {
            String oldValueString = (propertyChangeEvent.getOldValue() == null) ? "null" : propertyChangeEvent.getOldValue().toString();
            String newValueString = (propertyChangeEvent.getNewValue() == null) ? "null" : propertyChangeEvent.getNewValue().toString();
            StateLogger.log((propertyChangeEvent.getPropertyName() + " changed from " + oldValueString + " to " + newValueString));
        });
    }

    public static void setLoggedIn(boolean loggedIn) {
        if (loggedIn == ApplicationState.loggedIn)
            return; // do nothing if the state is the same
        boolean old = ApplicationState.loggedIn;
        ApplicationState.loggedIn = loggedIn;
        support.firePropertyChange("loggedIn", old, loggedIn);
        if (!loggedIn) {
            setCurrentUser(null);
        }
    }

    public static void setActivePane(Pane pane) {
        Pane old = ApplicationState.activePane;
        ApplicationState.activePane = pane;
        support.firePropertyChange("activePane", old, pane);
    }

    public static void setCurrentUser(Advogado newUser) {
        Advogado old = ApplicationState.currentUser;
        ApplicationState.currentUser = newUser;
        setLoggedIn(newUser != null);
        support.firePropertyChange("currentUser", old, newUser);
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static Pane getActivePane() {
        return activePane;
    }

    public static Advogado getCurrentUser() {
        return currentUser;
    }

    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public static void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
