package io.jurai.data;

import io.jurai.data.model.Advogado;
import io.jurai.data.model.Model;
import io.jurai.data.model.Pane;
import io.jurai.ui.controls.SimpleListItem;
import io.jurai.util.StateLogger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ApplicationState {
    private static final PropertyChangeSupport support = new PropertyChangeSupport(new ApplicationState());

    private static boolean loggedIn = false;
    private static Pane activePane = Pane.DashboardPane;
    private static Advogado currentUser = null;
    private static boolean debugging = false;
    private static SimpleListItem<? extends Model> selectedRequerente = null;

    public static void initialize() {
        support.addPropertyChangeListener(propertyChangeEvent -> {
            String oldValueString = (propertyChangeEvent.getOldValue() == null) ? "null" : propertyChangeEvent.getOldValue().toString();
            String newValueString = (propertyChangeEvent.getNewValue() == null) ? "null" : propertyChangeEvent.getNewValue().toString();
            StateLogger.log((propertyChangeEvent.getPropertyName() + " changed from " + oldValueString + " to " + newValueString));
        });
        StateLogger.log("initialized StateLogger");
        support.firePropertyChange("activePane", activePane, activePane);
        support.firePropertyChange("selectedRequerente", selectedRequerente, selectedRequerente);
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

    public static void setDebugging(boolean debugging) {
        boolean old = ApplicationState.debugging;
        ApplicationState.debugging = debugging;
        support.firePropertyChange("debugging", old, debugging);
    }

    public static SimpleListItem<? extends Model> getSelectedRequerente() {
        return selectedRequerente;
    }

    public static void setSelectedRequerente(SimpleListItem<? extends Model> selectedRequerente) {
        SimpleListItem<? extends Model> oldValue = ApplicationState.selectedRequerente;
        ApplicationState.selectedRequerente = selectedRequerente;
        support.firePropertyChange("selectedRequerente", oldValue, ApplicationState.selectedRequerente);
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

    public static boolean isDebugging() {
        return debugging;
    }

    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public static void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
