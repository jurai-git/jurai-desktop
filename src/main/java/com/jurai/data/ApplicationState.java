package com.jurai.data;

import com.jurai.data.model.Advogado;
import com.jurai.data.model.Model;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.Pane;
import com.jurai.ui.controls.SimpleListItem;
import com.jurai.ui.util.StageType;
import com.jurai.util.StateLogger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public final class ApplicationState {
    private static final PropertyChangeSupport support = new PropertyChangeSupport(new ApplicationState());

    private static Pane activePane = Pane.DashboardPane;
    private static Advogado currentUser = null;
    private static boolean debugging = false;
    private static SimpleListItem<? extends Model> selectedRequerente = null;
    private static AccountMode accountMode = null;
    private static StageType stageType = null;

    public static void initialize() {
        ApplicationData.initializeSupportLogging(support);
        StateLogger.log("initialized Application state logging");
        support.firePropertyChange("activePane", activePane, activePane);
        support.firePropertyChange("selectedRequerente", selectedRequerente, selectedRequerente);
        setAccountMode(AccountMode.LOGGING_IN);
    }

    public static void setActivePane(Pane pane) {
        Pane old = ApplicationState.activePane;
        ApplicationState.activePane = pane;
        support.firePropertyChange("activePane", old, pane);
    }

    public static void setCurrentUser(Advogado newUser) {
        Advogado old = ApplicationState.currentUser;
        ApplicationState.currentUser = newUser;
        support.firePropertyChange("currentUser", old, newUser);

        if(newUser != null) {
            setAccountMode(AccountMode.LOGGED_IN);
        } else {
            setAccountMode(AccountMode.LOGGING_IN);
        }
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

    public static void setAccountMode(AccountMode accountMode) {
        AccountMode oldValue = ApplicationState.accountMode;
        ApplicationState.accountMode = accountMode;
        support.firePropertyChange("accountMode", oldValue, ApplicationState.accountMode);

        if(accountMode == AccountMode.LOGGED_IN) {
            setStageType(StageType.MAIN_STAGE);
        } else {
            setStageType(StageType.SECONDARY_STAGE);
        }
    }

    private static void setStageType(StageType stageType) {
        StageType oldValue = ApplicationState.stageType;
        ApplicationState.stageType = stageType;
        support.firePropertyChange("stageType", oldValue, ApplicationState.stageType);
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

    public static AccountMode getAccountMode() {
        return accountMode;
    }

    public static StageType getStageType() {
        return stageType;
    }

    public static void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

}
