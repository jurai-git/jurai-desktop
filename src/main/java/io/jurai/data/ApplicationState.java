package io.jurai.data;

import io.jurai.data.model.Pane;
import io.jurai.data.notifier.StateNotifier;
import java.util.ArrayList;

public abstract class ApplicationState {

    //logged in property
    private static boolean loggedIn = false;
    private static ArrayList<StateNotifier<Boolean>> loggedInListeners = new ArrayList<>();

    public static void setLoggedIn(boolean loggedIn) {
        ApplicationState.loggedIn = loggedIn;
        loggedInListeners.forEach(notifier -> notifier.stateChanged(loggedIn));
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void addLoggedInListener(StateNotifier<Boolean> l) {
        loggedInListeners.add(l);
    }

    //current pane property
    private static Pane activePane = Pane.HomePane;
    private static ArrayList<StateNotifier<Pane>> activePaneNotifiers = new ArrayList<>();

    public static void setActivePane(Pane pane) {
        activePane = pane;
        activePaneNotifiers.forEach(notifier -> notifier.stateChanged(activePane));
    }

    public static Pane getActivePane() {
        return activePane;
    }

    public static void addActivePaneNotifier(StateNotifier<Pane> notifier) {
        activePaneNotifiers.add(notifier);
    }
}
