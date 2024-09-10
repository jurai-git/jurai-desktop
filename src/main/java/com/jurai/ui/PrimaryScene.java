package com.jurai.ui;

import com.jurai.data.ApplicationData;
import com.jurai.data.ApplicationState;
import com.jurai.ui.animation.SidebarAnimator;
import com.jurai.ui.controller.*;
import com.jurai.ui.controls.ArrowToggleButton;
import com.jurai.ui.modal.ModalHandler;
import com.jurai.ui.modal.RequerenteRegisterModal;
import com.jurai.ui.util.Pane;
import com.jurai.ui.panes.*;
import com.jurai.ui.panes.layout.NodeConstraints;
import com.jurai.ui.panes.layout.ProportionPane;
import com.jurai.util.UILogger;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.StackPane;

public class PrimaryScene {
    private Scene scene;
    private StackPane modalRoot;
    private ProportionPane mainPane;
    private Navbar navbar;
    private Sidebar sidebar;
    private ArrowToggleButton sidebarToggleButton;
    private final NodeConstraints mainContentConstraints = new NodeConstraints(0, 0.06f, 0.82f, 0.92f);
    private NodeConstraints sidebarConstraints, sidebarToggleConstraints;
    private AccountPane accountPane;
    private DashboardPane dashboardPane;
    private HomePane homePane;
    private PlanPane planPane;
    private QuickQueryPane quickQueryPane;

    public PrimaryScene() {
        initControls();
        ModalHandler.initialize(modalRoot, mainPane);
        layControls();
        attachControllers();
        attachNotifiers();
        attachEvents();
    }

    private void initControls() {
        mainPane = new ProportionPane();
        modalRoot = new StackPane(mainPane);
        mainPane.getStyleClass().add("root-pane");
        navbar = new Navbar();
        accountPane = new AccountPane();
        dashboardPane = new DashboardPane();
        homePane = new HomePane();
        planPane = new PlanPane();
        sidebar = new Sidebar();
        quickQueryPane = new QuickQueryPane();
        sidebarToggleButton = new ArrowToggleButton();
    }

    private void layControls() {
        ApplicationData.headerHeightProperty().bind(mainPane.heightProperty().multiply(0.06));
        mainPane.addConstraints(accountPane.getView(), mainContentConstraints);
        mainPane.addConstraints(dashboardPane.getView(), mainContentConstraints);
        mainPane.addConstraints(homePane.getView(), mainContentConstraints);
        mainPane.addConstraints(planPane.getView(), mainContentConstraints);
        mainPane.addConstraints(quickQueryPane.getView(), mainContentConstraints);
        mainPane.addConstraints(navbar.getView(), new NodeConstraints(0, 0, 1, 0.06f));

        mainContentConstraints.exclusiveWProperty.bind(mainPane.widthProperty().subtract(sidebar.getView().widthProperty()));
        mainContentConstraints.anchor = NodeConstraints.Anchor.TOP_RIGHT;

        sidebarConstraints = new NodeConstraints(0, 0, 0.2f, 1);
        mainPane.addConstraints(sidebar.getView(), sidebarConstraints);
        sidebarToggleConstraints = new NodeConstraints(0.16f, 0.02f, 0.04f, 0.04f);
        sidebarToggleConstraints.exclusiveWProperty.bind(sidebarToggleButton.heightProperty());
        sidebarToggleButton.translateXProperty().bind(mainPane.widthProperty().subtract(mainPane.heightProperty()).multiply(0.02));
        mainPane.addConstraints(sidebarToggleButton, sidebarToggleConstraints);

        activePaneChanged(ApplicationState.getActivePane());
        sidebarModeUpdated(false);

        scene = new Scene(modalRoot, ApplicationData.getScreenSize().width * 0.7, ApplicationData.getScreenSize().height * 0.7, false, SceneAntialiasing.BALANCED);
    }

    private void layFixedControls() {
        mainPane.getChildren().add(sidebar.getView());
        mainPane.getChildren().add(navbar.getView());
        mainPane.getChildren().add(sidebarToggleButton);
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
    }

    private void attachNotifiers() {
        ApplicationState.addPropertyChangeListener(e -> {
            if ("activePane".equals(e.getPropertyName())) {
                activePaneChanged((Pane) e.getNewValue());
            }
        });
        mainPane.widthProperty().addListener((observableValue, number, t1) -> {
            if (number.doubleValue() < (double) ApplicationData.getScreenSize().width / 2 && sidebarToggleButton.isActive()) {
                sidebarToggleButton.actuate();
            }
        });
    }

    private void attachEvents() {
        sidebarToggleButton.addActiveListener((observableValue, aBoolean, t1) -> {
            sidebarModeUpdated(aBoolean);
        });
    }

    private void sidebarModeUpdated(boolean iconsOnly) {
        sidebar.setIconsOnly(iconsOnly);
        SidebarAnimator.getSidebarAnimation(sidebar, sidebarConstraints, mainPane).playFromStart();
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
            case QuickQueryPane:
                mainPane.getChildren().removeAll(mainPane.getChildren());
                layFixedControls();
                mainPane.getChildren().add(quickQueryPane.getView());
                break;
            default:
                UILogger.logError("Tried switching to invalid main pane");
                return;
        }
        UILogger.log("active pane changed to " + pane.name());
    }
}