package com.jurai.data;

import com.jurai.data.model.Advogado;
import com.jurai.data.model.Demanda;
import com.jurai.data.model.Model;
import com.jurai.data.model.Requerente;
import com.jurai.ui.modal.Modal;
import com.jurai.ui.modal.popup.Popup;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.Pane;
import com.jurai.ui.controls.SimpleListItem;
import com.jurai.ui.util.StageType;
import com.jurai.util.StateLogger;
import javafx.stage.Stage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public final class ApplicationState {
    private static PropertyChangeSupport support;
    private static volatile ApplicationState instance;

    // account related stuff
    private static Advogado currentUser = null;
    private static StageType stageType = StageType.SECONDARY_STAGE;
    private static AccountMode accountMode = null;

    private static Pane activePane = Pane.DashboardPane;
    private static boolean debugging = false;
    private static Requerente selectedRequerente = null;
    private static Popup currentPopup = null;
    private static Stage currentStage = null;
    private static boolean remembersUser = false;
    private static String apiUrl = "http://127.0.0.1:5000";
    private static Demanda selectedDemanda = null;

    public static void initialize() {
        if (instance == null) {
            synchronized (ApplicationState.class) {
                if (instance == null) {
                    instance = new ApplicationState();
                }
            }
        }
    }

    private ApplicationState() {
        support = new PropertyChangeSupport(this);
        StateLogger.log("initialized Application state logging");
        support.addPropertyChangeListener(event -> {
            StateLogger.log(event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
        });
        support.firePropertyChange("activePane", activePane, activePane);
        support.firePropertyChange("selectedRequerente", selectedRequerente, selectedRequerente);
    }

    public static ApplicationState getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ApplicationState not initialized. Initialize it with initialize() first.");
        }
        return instance;
    }

    public void setActivePane(Pane pane) {
        Pane old = ApplicationState.activePane;
        ApplicationState.activePane = pane;
        support.firePropertyChange("activePane", old, pane);
    }

    public void setCurrentUser(Advogado newUser) {
        Advogado old = ApplicationState.currentUser;
        ApplicationState.currentUser = newUser;
        support.firePropertyChange("currentUser", old, newUser);

        if(newUser != null) {
            setAccountMode(AccountMode.LOGGED_IN);
        } else {
            setAccountMode(AccountMode.LOGGING_IN);
        }
    }

    public void setDebugging(boolean debugging) {
        boolean old = ApplicationState.debugging;
        ApplicationState.debugging = debugging;
        support.firePropertyChange("debugging", old, debugging);
    }

    public void setSelectedRequerente(Requerente selectedRequerente) {
        Requerente oldValue = ApplicationState.selectedRequerente;
        ApplicationState.selectedRequerente = selectedRequerente;
        support.firePropertyChange("selectedRequerente", oldValue, selectedRequerente);
    }

    public void setAccountMode(AccountMode accountMode) {
        AccountMode oldValue = ApplicationState.accountMode;
        ApplicationState.accountMode = accountMode;
        support.firePropertyChange("accountMode", oldValue, ApplicationState.accountMode);

        if(accountMode == AccountMode.LOGGED_IN) {
            setStageType(StageType.MAIN_STAGE);
        } else {
            setStageType(StageType.SECONDARY_STAGE);
        }
    }

    private void setStageType(StageType stageType) {
        StageType oldValue = ApplicationState.stageType;
        ApplicationState.stageType = stageType;
        support.firePropertyChange("stageType", oldValue, ApplicationState.stageType);
    }

    public void setCurrentStage(Stage currentStage) {
        var oldvalue = ApplicationState.currentStage;
        ApplicationState.currentStage = currentStage;
        support.firePropertyChange("currentStage", oldvalue, currentStage);
    }

    public Popup getCurrentPopup() {
        return currentPopup;
    }

    public void setCurrentPopup(Popup newPopup) {
        var oldValue = currentPopup;
        currentPopup = newPopup;
        if(oldValue != null) {
            oldValue.close();
        }
        support.firePropertyChange("currentPopup", oldValue, newPopup);
    }

    public void setRemembersUser(boolean remembersUser) {
        ApplicationState.remembersUser = remembersUser;
    }

    public void setApiUrl(String apiUrl) {
        support.firePropertyChange("apiUrl", ApplicationState.apiUrl, apiUrl);
        ApplicationState.apiUrl = apiUrl;
    }

    public void setSelectedDemanda(Demanda selectedDemanda) {
        support.firePropertyChange("selectedDemanda", ApplicationState.selectedDemanda, selectedDemanda);
        ApplicationState.selectedDemanda = selectedDemanda;
    }

    public Demanda getSelectedDemanda() {
        return selectedDemanda;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public boolean remembersUser() {
        return remembersUser;
    }

    public Requerente getSelectedRequerente() {
        return selectedRequerente;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public Pane getActivePane() {
        return activePane;
    }

    public Advogado getCurrentUser() {
        return currentUser;
    }

    public boolean isDebugging() {
        return debugging;
    }

    public AccountMode getAccountMode() {
        return accountMode;
    }

    public StageType getStageType() {
        return stageType;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

}
