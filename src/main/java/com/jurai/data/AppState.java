package com.jurai.data;

import com.jurai.data.model.Advogado;
import com.jurai.data.model.Demanda;
import com.jurai.data.model.Requerente;
import com.jurai.data.model.internal_state.AsyncState;
import com.jurai.ui.panes.DocumentsPane;
import com.jurai.ui.panes.QuickQueryPane;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.Pane;
import com.jurai.ui.util.StageType;
import com.jurai.util.StateLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.stage.Stage;
import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@Getter
public final class AppState {
    private static PropertyChangeSupport support;

    @Getter
    private static volatile AppState instance;

    // account related stuff
    private static Advogado currentUser = null;
    private static StageType stageType = StageType.SECONDARY_STAGE;
    private static AccountMode accountMode = null;

    private static Pane activePane = Pane.DashboardPane;
    private static boolean debugging = false;
    private static Requerente selectedRequerente = null;
    private static Stage currentStage = null;
    private static boolean remembersUser = false;
    private static String apiUrl = "http://localhost:5000/";
    private static boolean useAnimations = true;
    private static boolean useLightTheme = false;
    private static Demanda selectedDemanda = null;
    private static boolean sidebarExtended = false ;
    private static boolean viewportSmall = false; // this indicates weather the width of the app is small, for responsiveness
    private static QuickQueryPane.Mode quickQueryMode = QuickQueryPane.Mode.PDF;
    private static DocumentsPane.Mode docPaneMode = DocumentsPane.Mode.CHOOSER;
    private static AsyncState<ObservableList<Demanda>> allDemandas = new AsyncState<>(null, true, null);
    private static Demanda globalSelectedDemanda = null;

    private static final String fallbackPfpPath = "/img/user-default.jpg";

    public static void initialize() {
        if (instance == null) {
            synchronized (AppState.class) {
                if (instance == null) {
                    instance = new AppState();
                }
            }
        }
    }

    private AppState() {
        support = new PropertyChangeSupport(this);
        StateLogger.log("initialized Application state logging");
        support.addPropertyChangeListener(event -> {
            StateLogger.log(event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
        });
        support.firePropertyChange("activePane", activePane, activePane);
        support.firePropertyChange("selectedRequerente", selectedRequerente, selectedRequerente);
    }

    public static AppState get() {
        if (instance == null) {
            throw new IllegalStateException("ApplicationState not initialized. Initialize it with initialize() first.");
        }
        return instance;
    }

    public void setActivePane(Pane pane) {
        Pane old = AppState.activePane;
        AppState.activePane = pane;
        support.firePropertyChange("activePane", old, pane);
    }

    public void setCurrentUser(Advogado newUser) {
        Advogado old = AppState.currentUser;
        AppState.currentUser = newUser;
        support.firePropertyChange("currentUser", old, newUser);

        if(newUser != null) {
            setAccountMode(AccountMode.LOGGED_IN);
        } else {
            setAccountMode(AccountMode.LOGGING_IN);
            setSelectedDemanda(null);
            setSelectedRequerente(null);
        }
    }

    public void setDebugging(boolean debugging) {
        boolean old = AppState.debugging;
        AppState.debugging = debugging;
        support.firePropertyChange("debugging", old, debugging);
    }

    public void setSelectedRequerente(Requerente selectedRequerente) {
        Requerente oldValue = AppState.selectedRequerente;
        AppState.selectedRequerente = selectedRequerente;
        support.firePropertyChange("selectedRequerente", oldValue, selectedRequerente);
        setSelectedDemanda(null);
    }

    public void setAccountMode(AccountMode accountMode) {
        AccountMode oldValue = AppState.accountMode;
        AppState.accountMode = accountMode;
        support.firePropertyChange("accountMode", oldValue, AppState.accountMode);

        if(accountMode == AccountMode.LOGGED_IN) {
            setStageType(StageType.MAIN_STAGE);
        } else {
            setStageType(StageType.SECONDARY_STAGE);
        }
    }

    void setStageType(StageType stageType) {
        StageType oldValue = AppState.stageType;
        AppState.stageType = stageType;
        support.firePropertyChange("stageType", oldValue, AppState.stageType);
    }

    public void setCurrentStage(Stage currentStage) {
        var oldvalue = AppState.currentStage;
        AppState.currentStage = currentStage;
        support.firePropertyChange("currentStage", oldvalue, currentStage);
    }

    public void setRemembersUser(boolean remembersUser) {
        AppState.remembersUser = remembersUser;
    }

    public void setApiUrl(String apiUrl) {
        String oldApiUrl = AppState.apiUrl;
        AppState.apiUrl = apiUrl;
        support.firePropertyChange("apiUrl", oldApiUrl, apiUrl);
    }

    public void setUseAnimations(boolean useAnimations) {
        if (useAnimations != AppState.useAnimations) {
            boolean oldUseAnimatios = AppState.useAnimations;
            AppState.useAnimations = useAnimations;
            support.firePropertyChange("useAnimations", oldUseAnimatios, useAnimations);
        }
    }

    public void setUseLightTheme(boolean useLightTheme) {
        boolean oldVal = AppState.useLightTheme;
        AppState.useLightTheme = useLightTheme;
        support.firePropertyChange("useLightTheme", oldVal, useLightTheme);
    }

    public void setSelectedDemanda(Demanda selectedDemanda) {
        Demanda oldValue = AppState.selectedDemanda;
        AppState.selectedDemanda = selectedDemanda;
        support.firePropertyChange("selectedDemanda", oldValue, selectedDemanda);
    }

    public void setSidebarExtended(boolean sidebarExtended) {
        boolean oldVal = AppState.sidebarExtended;
        AppState.sidebarExtended = sidebarExtended;
        support.firePropertyChange("sidebarExtended", oldVal, sidebarExtended);
    }

    public void setViewportSmall(boolean viewportSmall) {
        boolean oldVal = AppState.viewportSmall;
        AppState.viewportSmall = viewportSmall;
        support.firePropertyChange("viewportSmall", oldVal, viewportSmall);
    }

    public void setQuickQueryMode(QuickQueryPane.Mode newmode) {
        QuickQueryPane.Mode oldMode = AppState.quickQueryMode;
        AppState.quickQueryMode = newmode;
        support.firePropertyChange("quickQueryMode", oldMode, newmode);

    }

    public void setDocPaneMode(DocumentsPane.Mode newMode) {
        DocumentsPane.Mode oldMode = AppState.docPaneMode;
        AppState.docPaneMode = newMode;
        support.firePropertyChange("docPaneMode", oldMode, newMode);
    }

    public void setGlobalSelectedDemanda(Demanda newD) {
        Demanda old = AppState.globalSelectedDemanda;
        AppState.globalSelectedDemanda = newD;
        support.firePropertyChange("globalSelectedDemanda", old, newD);
    }

    public AsyncState<ObservableList<Demanda>> getAllDemandas() {
        return allDemandas;
    }

    public void setAllDemandas(AsyncState<ObservableList<Demanda>> allDemandas) {
        AsyncState<ObservableList<Demanda>> old = AppState.allDemandas;
        AppState.allDemandas = allDemandas;
        support.firePropertyChange("allDemandas", old, allDemandas);
    }

    public Demanda getGlobalSelectedDemanda() {
        return globalSelectedDemanda;
    }

    public DocumentsPane.Mode getDocPaneMode() {
        return docPaneMode;
    }

    public boolean isViewportSmall() {
        return viewportSmall;
    }

    public QuickQueryPane.Mode getQuickQueryMode() {
        return quickQueryMode;
    }

    public boolean isSidebarExtended() {
        return sidebarExtended;
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

    public boolean isUseAnimations() {
        return useAnimations;
    }

    public boolean isUseLightTheme() {
        return useLightTheme;
    }

    public StageType getStageType() {
        return stageType;
    }

    public String getFallbackPfpPath() {
        return fallbackPfpPath;
    }

    public void listen(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }
}