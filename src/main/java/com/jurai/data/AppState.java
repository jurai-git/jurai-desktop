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
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.stream.Stream;

public final class AppState {
    private static PropertyChangeSupport support;

    @Getter
    private static volatile AppState instance;

    private ObjectProperty<Advogado> currentUser = new SimpleObjectProperty<>(null);
    private static ObjectProperty<StageType> stageType = new SimpleObjectProperty<>(StageType.SECONDARY_STAGE);
    private static ObjectProperty<AccountMode> accountMode = new SimpleObjectProperty<>(null);
    private static ObjectProperty<Pane> activePane = new SimpleObjectProperty<>(Pane.DashboardPane);
    private static BooleanProperty debugging = new SimpleBooleanProperty(false);
    private static ObjectProperty<Requerente> selectedRequerente = new SimpleObjectProperty<>(null);
    private static ObjectProperty<Stage> currentStage = new SimpleObjectProperty<>(null);
    private static BooleanProperty remembersUser = new SimpleBooleanProperty(false);
    private static StringProperty apiUrl = new SimpleStringProperty("http://localhost:5000/");
    private static BooleanProperty useAnimations = new SimpleBooleanProperty(true);
    private static BooleanProperty useLightTheme = new SimpleBooleanProperty(false);
    private static ObjectProperty<Demanda> selectedDemanda = new SimpleObjectProperty<>(null);
    private static BooleanProperty sidebarExtended = new SimpleBooleanProperty(false);
    private static BooleanProperty viewportSmall = new SimpleBooleanProperty(false);
    private static ObjectProperty<QuickQueryPane.Mode> quickQueryMode = new SimpleObjectProperty<>(QuickQueryPane.Mode.PDF);
    private static ObjectProperty<DocumentsPane.Mode> docPaneMode = new SimpleObjectProperty<>(DocumentsPane.Mode.CHOOSER);
    private static ObjectProperty<AsyncState<ObservableList<Demanda>>> allDemandas = new SimpleObjectProperty<>(new AsyncState<>(null, true, null));
    private static ObjectProperty<Demanda> globalSelectedDemanda = new SimpleObjectProperty<>(null);

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
        registerAllListeners();
        StateLogger.log("initialized Application state logging");
    }

    private void registerAllListeners() {
        accountMode.addListener((obs, newVal, oldVal) -> {
            if(accountMode.get() == AccountMode.LOGGED_IN) {
                setStageType(StageType.MAIN_STAGE);
            } else {
                setStageType(StageType.SECONDARY_STAGE);
            }
        });

        currentUser.addListener((obs, oldVal, newVal) -> {
            if(newVal != null) {
                setAccountMode(AccountMode.LOGGED_IN);
            } else {
                setAccountMode(AccountMode.LOGGING_IN);
                setSelectedDemanda(null);
                setSelectedRequerente(null);
            }
        });

        Stream.of(
                currentUser, stageType, accountMode, activePane, debugging,
                selectedRequerente, currentStage, remembersUser, apiUrl,
                useAnimations, useLightTheme, selectedDemanda, sidebarExtended,
                viewportSmall, quickQueryMode, docPaneMode, allDemandas, globalSelectedDemanda
        ).forEach(prop -> {
            prop.addListener((obs, oldVal, newVal) -> {
                String name = getPropertyName(prop);
                StateLogger.log(name + " changed from " + oldVal + " to " + newVal);
            });
        });

    }

    private String getPropertyName(Property<?> prop) {
        if (prop == currentUser) return "currentUser";
        if (prop == stageType) return "stageType";
        if (prop == accountMode) return "accountMode";
        if (prop == activePane) return "activePane";
        if (prop == debugging) return "debugging";
        if (prop == selectedRequerente) return "selectedRequerente";
        if (prop == currentStage) return "currentStage";
        if (prop == remembersUser) return "remembersUser";
        if (prop == apiUrl) return "apiUrl";
        if (prop == useAnimations) return "useAnimations";
        if (prop == useLightTheme) return "useLightTheme";
        if (prop == selectedDemanda) return "selectedDemanda";
        if (prop == sidebarExtended) return "sidebarExtended";
        if (prop == viewportSmall) return "viewportSmall";
        if (prop == quickQueryMode) return "quickQueryMode";
        if (prop == docPaneMode) return "docPaneMode";
        if (prop == allDemandas) return "allDemandas";
        if (prop == globalSelectedDemanda) return "globalSelectedDemanda";
        return "unknown";
    }


    public static AppState get() {
        if (instance == null) {
            throw new IllegalStateException("ApplicationState not initialized. Initialize it with initialize() first.");
        }
        return instance;
    }

    // currentUser
    public Advogado getCurrentUser() {
        return currentUser.get();
    }
    public void setCurrentUser(Advogado user) {
        currentUser.set(user);
    }
    public ObjectProperty<Advogado> currentUserProperty() {
        return currentUser;
    }

    // stageType
    public StageType getStageType() {
        return stageType.get();
    }
    public void setStageType(StageType stage) {
        stageType.set(stage);
    }
    public ObjectProperty<StageType> stageTypeProperty() {
        return stageType;
    }

    // accountMode
    public AccountMode getAccountMode() {
        return accountMode.get();
    }
    public void setAccountMode(AccountMode mode) {
        accountMode.set(mode);
    }
    public ObjectProperty<AccountMode> accountModeProperty() {
        return accountMode;
    }

    // activePane
    public Pane getActivePane() {
        return activePane.get();
    }
    public void setActivePane(Pane pane) {
        activePane.set(pane);
    }
    public ObjectProperty<Pane> activePaneProperty() {
        return activePane;
    }

    // debugging
    public boolean isDebugging() {
        return debugging.get();
    }
    public void setDebugging(boolean debug) {
        debugging.set(debug);
    }
    public BooleanProperty debuggingProperty() {
        return debugging;
    }

    // selectedRequerente
    public Requerente getSelectedRequerente() {
        return selectedRequerente.get();
    }
    public void setSelectedRequerente(Requerente requerente) {
        selectedRequerente.set(requerente);
    }
    public ObjectProperty<Requerente> selectedRequerenteProperty() {
        return selectedRequerente;
    }

    // currentStage
    public Stage getCurrentStage() {
        return currentStage.get();
    }
    public void setCurrentStage(Stage stage) {
        currentStage.set(stage);
    }
    public ObjectProperty<Stage> currentStageProperty() {
        return currentStage;
    }

    // remembersUser
    public boolean isRemembersUser() {
        return remembersUser.get();
    }
    public void setRemembersUser(boolean remembers) {
        remembersUser.set(remembers);
    }
    public BooleanProperty remembersUserProperty() {
        return remembersUser;
    }

    // apiUrl
    public String getApiUrl() {
        return apiUrl.get();
    }
    public void setApiUrl(String url) {
        apiUrl.set(url);
    }
    public StringProperty apiUrlProperty() {
        return apiUrl;
    }

    // useAnimations
    public boolean isUseAnimations() {
        return useAnimations.get();
    }
    public void setUseAnimations(boolean use) {
        useAnimations.set(use);
    }
    public BooleanProperty useAnimationsProperty() {
        return useAnimations;
    }

    // useLightTheme
    public boolean isUseLightTheme() {
        return useLightTheme.get();
    }
    public void setUseLightTheme(boolean use) {
        useLightTheme.set(use);
    }
    public BooleanProperty useLightThemeProperty() {
        return useLightTheme;
    }

    // selectedDemanda
    public Demanda getSelectedDemanda() {
        return selectedDemanda.get();
    }
    public void setSelectedDemanda(Demanda demanda) {
        selectedDemanda.set(demanda);
    }
    public ObjectProperty<Demanda> selectedDemandaProperty() {
        return selectedDemanda;
    }

    // sidebarExtended
    public boolean isSidebarExtended() {
        return sidebarExtended.get();
    }
    public void setSidebarExtended(boolean extended) {
        sidebarExtended.set(extended);
    }
    public BooleanProperty sidebarExtendedProperty() {
        return sidebarExtended;
    }

    // viewportSmall
    public boolean isViewportSmall() {
        return viewportSmall.get();
    }
    public void setViewportSmall(boolean small) {
        viewportSmall.set(small);
    }
    public BooleanProperty viewportSmallProperty() {
        return viewportSmall;
    }

    // quickQueryMode
    public QuickQueryPane.Mode getQuickQueryMode() {
        return quickQueryMode.get();
    }
    public void setQuickQueryMode(QuickQueryPane.Mode mode) {
        quickQueryMode.set(mode);
    }
    public ObjectProperty<QuickQueryPane.Mode> quickQueryModeProperty() {
        return quickQueryMode;
    }

    // docPaneMode
    public DocumentsPane.Mode getDocPaneMode() {
        return docPaneMode.get();
    }
    public void setDocPaneMode(DocumentsPane.Mode mode) {
        docPaneMode.set(mode);
    }
    public ObjectProperty<DocumentsPane.Mode> docPaneModeProperty() {
        return docPaneMode;
    }

    // allDemandas
    public AsyncState<ObservableList<Demanda>> getAllDemandas() {
        return allDemandas.get();
    }
    public void setAllDemandas(AsyncState<ObservableList<Demanda>> demandas) {
        allDemandas.set(demandas);
    }
    public ObjectProperty<AsyncState<ObservableList<Demanda>>> allDemandasProperty() {
        return allDemandas;
    }

    // globalSelectedDemanda
    public Demanda getGlobalSelectedDemanda() {
        return globalSelectedDemanda.get();
    }
    public void setGlobalSelectedDemanda(Demanda demanda) {
        globalSelectedDemanda.set(demanda);
    }
    public ObjectProperty<Demanda> globalSelectedDemandaProperty() {
        return globalSelectedDemanda;
    }

    public String getFallbackPfpPath() {
        return fallbackPfpPath;
    }
}