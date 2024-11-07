package com.jurai.ui;

import com.jurai.data.ApplicationData;
import com.jurai.data.ApplicationState;
import com.jurai.ui.animation.SidebarAnimator;
import com.jurai.ui.controller.*;
import com.jurai.ui.util.Pane;
import com.jurai.ui.panes.*;
import com.jurai.ui.panes.layout.NodeConstraints;
import com.jurai.ui.panes.layout.ProportionPane;
import com.jurai.util.UILogger;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.StackPane;

public class PrimaryScene {
    private Scene scene;
    private StackPane modalRoot, effectPane;
    private ProportionPane mainPane;
    private Navbar navbar;
    private Sidebar sidebar;
    private final NodeConstraints mainContentConstraints = new NodeConstraints(0, 0.06f, 0.82f, 0.92f);
    private NodeConstraints sidebarConstraints;
    private AccountPane accountPane;
    private DashboardPane dashboardPane;
    private PlanPane planPane;
    private QuickQueryPane quickQueryPane;

    public PrimaryScene() {
        initControls();
        layControls();
        attachControllers();
        attachNotifiers();
        attachEvents();
    }

    private void initControls() {
        mainPane = new ProportionPane();
        effectPane = new StackPane();
        effectPane.setMouseTransparent(true);
        modalRoot = new StackPane(mainPane, effectPane);
        effectPane.minHeightProperty().bind(modalRoot.heightProperty());
        effectPane.minWidthProperty().bind(modalRoot.widthProperty());
        mainPane.getStyleClass().add("root-pane");
        navbar = new Navbar();
        accountPane = new AccountPane();
        dashboardPane = new DashboardPane();
        planPane = new PlanPane();
        sidebar = new Sidebar();
        quickQueryPane = new QuickQueryPane();
    }

    private void layControls() {
        ApplicationData.headerHeightProperty().bind(mainPane.heightProperty().multiply(0.06));
        mainPane.addConstraints(accountPane.getView(), mainContentConstraints);
        mainPane.addConstraints(dashboardPane.getView(), mainContentConstraints);
        mainPane.addConstraints(planPane.getView(), mainContentConstraints);
        mainPane.addConstraints(quickQueryPane.getView(), mainContentConstraints);
        mainPane.addConstraints(navbar.getView(), new NodeConstraints(0, 0, 1, 0.06f));

        mainContentConstraints.exclusiveWProperty.bind(mainPane.widthProperty().subtract(sidebar.getView().widthProperty()));
        mainContentConstraints.anchor = NodeConstraints.Anchor.TOP_RIGHT;

        sidebarConstraints = new NodeConstraints(0, 0, 0.2f, 1);
        mainPane.addConstraints(sidebar.getView(), sidebarConstraints);
        NodeConstraints sidebarToggleConstraints = new NodeConstraints(0.16f, 0.01f, 0.04f, 0.04f);
        activePaneChanged(ApplicationState.getInstance().getActivePane());
        sidebarModeUpdated(false);

        mainPane.setCache(true);
        mainPane.setCacheHint(CacheHint.SPEED);
        sidebar.getView().setCache(true);
        sidebar.getView().setCacheHint(CacheHint.SPEED);

        scene = new Scene(modalRoot, ApplicationData.getScreenSize().width, ApplicationData.getScreenSize().height, false, SceneAntialiasing.BALANCED);
        ApplicationState.getInstance().setSidebarExtended(ApplicationState.getInstance().isSidebarExtended());
    }

    private void layFixedControls() {
        mainPane.getChildren().add(sidebar.getView());
        mainPane.getChildren().add(navbar.getView());
    }

    private void attachControllers() {
        // navbar
        NavbarController navbarController = new NavbarController();
        navbarController.initialize(navbar);

        // account pane
        AccountPaneController accountPaneController = new AccountPaneController();
        accountPaneController.initialize(accountPane);

        DashboardPaneController dashboardPaneController = new DashboardPaneController();
        dashboardPaneController.initialize(dashboardPane);

        // sidebar
        SidebarController sidebarController = new SidebarController();
        sidebarController.initialize(sidebar);

        QuickQueryPaneController quickQueryPaneController = new QuickQueryPaneController();
        quickQueryPaneController.initialize(quickQueryPane);
    }

    private void attachNotifiers() {
        ApplicationState.getInstance().addPropertyChangeListener(e -> {
            if ("activePane".equals(e.getPropertyName())) {
                activePaneChanged((Pane) e.getNewValue());
            }
        });
        ApplicationState.getInstance().addPropertyChangeListener(e -> {
            if ("currentPopup".equals(e.getPropertyName())) {

            }
        });
        ApplicationState.getInstance().addPropertyChangeListener(e -> {
            if ("viewportSmall".equals(e.getPropertyName())) {
                ApplicationState.getInstance().setSidebarExtended(true);
            }
        });
        ApplicationState.getInstance().addPropertyChangeListener(e -> {
            if ("sidebarExtended".equals(e.getPropertyName())) {
                sidebarModeUpdated((Boolean) e.getNewValue());
            }
        });
    }

    private void attachEvents() {
    }

    private void sidebarModeUpdated(boolean iconsOnly) {
        sidebar.setIconsOnly(iconsOnly);
        SidebarAnimator.getSidebarAnimation(sidebar, sidebarConstraints, mainPane).playFromStart();
    }

    public void setSidebarMode(boolean iconsOnly) {
        sidebarModeUpdated(iconsOnly);
    }

    public Scene getScene() {
        return scene;
    }

    private void activePaneChanged(Pane pane) {
        switch (pane) {
            case AccountPane:
                mainPane.getChildren().removeAll(mainPane.getChildren());
                layFixedControls();
                mainPane.getChildren().add(accountPane.getView());
                break;
            case DashboardPane:
                mainPane.getChildren().removeAll(mainPane.getChildren());
                layFixedControls();
                mainPane.getChildren().add(dashboardPane.getView());
                break;
            case PlanPane:
                mainPane.getChildren().removeAll(mainPane.getChildren());
                layFixedControls();
                mainPane.getChildren().add(planPane.getView());
                break;
            case QuickQueryPane:
                mainPane.getChildren().removeAll(mainPane.getChildren());
                layFixedControls();
                mainPane.getChildren().add(quickQueryPane.getView());
                break;
            case DocPane:
                mainPane.getChildren().removeAll(mainPane.getChildren());
                layFixedControls();
                mainPane.getChildren().add(planPane.getView());
                break;
            default:
                UILogger.logError("Tried switching to invalid main pane");
                return;
        }
        UILogger.log("active pane changed to " + pane.name());
    }

    public StackPane getEffectPane() {
        return effectPane;
    }

    public StackPane getModalRoot() {
        return modalRoot;
    }

    public ProportionPane getContent() {
        return mainPane;
    }
}