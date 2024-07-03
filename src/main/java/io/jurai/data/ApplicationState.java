package io.jurai.data;

import io.jurai.data.notifier.StateNotifier;
import javafx.application.Application;
import javafx.collections.ObservableList;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class ApplicationState {
    private static boolean loggedIn = false;
    private static ArrayList<StateNotifier<Boolean>> loggedInListeners = new ArrayList<>();

    public static void setLoggedIn(boolean loggedIn) {
        ApplicationState.loggedIn = loggedIn;
        loggedInListeners.forEach(listener -> listener.stateChanged(loggedIn));
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void addLoggedInListener(StateNotifier<Boolean> l) {
        loggedInListeners.add(l);
    }
}
