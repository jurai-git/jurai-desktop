package com.jurai.ui;

import com.jurai.data.ApplicationState;
import com.jurai.ui.controller.AccountPaneController;
import com.jurai.ui.controller.DashboardPaneController;
import com.jurai.ui.controller.NavbarController;
import com.jurai.ui.controller.SidebarController;
import com.jurai.ui.util.Pane;
import com.jurai.ui.menus.LoginMenu;
import com.jurai.ui.panes.*;
import com.jurai.ui.panes.layout.NodeConstraints;
import com.jurai.ui.panes.layout.ProportionPane;
import com.jurai.util.UILogger;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;

import java.awt.*;

public class PrimaryScene {
    private Scene scene;
    private ProportionPane mainPane;
    private Navbar navbar;
    private LoginMenu loginMenu;
    private Sidebar sidebar;
    private final NodeConstraints mainContentConstraints = new NodeConstraints(0.18f, 0.08f, 0.82f, 0.92f);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private AccountPane accountPane;
    private DashboardPane dashboardPane;
    private HomePane homePane;
    private PlanPane planPane;
    private QuickQueryPane quickQueryPane;

    public PrimaryScene() {
        initControls();
        layControls();
        attachControllers();
        attachNotifiers();
    }

    private void initControls() {
        mainPane = new ProportionPane();
        mainPane.getStyleClass().add("root-pane");
        navbar = new Navbar();
        loginMenu = new LoginMenu();
        accountPane = new AccountPane();
        dashboardPane = new DashboardPane();
        homePane = new HomePane();
        planPane = new PlanPane();
        sidebar = new Sidebar();
        quickQueryPane = new QuickQueryPane();
    }

    private void layControls() {
        mainPane.addConstraints(accountPane.getView(), mainContentConstraints);
        mainPane.addConstraints(dashboardPane.getView(), mainContentConstraints);
        mainPane.addConstraints(homePane.getView(), mainContentConstraints);
        mainPane.addConstraints(planPane.getView(), mainContentConstraints);
        mainPane.addConstraints(quickQueryPane.getView(), mainContentConstraints);
        activePaneChanged(ApplicationState.getActivePane());

        scene = new Scene(mainPane, screenSize.width * 0.7, screenSize.height * 0.7, false, SceneAntialiasing.BALANCED);
    }

    private void layFixedControls() {
        mainPane.addConstraints(navbar.getView(), new NodeConstraints(0, 0, 1, 0.08f));
        mainPane.getChildren().add(navbar.getView());


        mainPane.addConstraints(sidebar.getView(), new NodeConstraints(
                0, 0, 0.18f, 1,
                new SimpleFloatProperty(screenSize.width * 0.1f),
                new SimpleFloatProperty(0)
        ));
        mainPane.getChildren().add(sidebar.getView());
    }

    private void attachControllers() {
        // navbar
        NavbarController navbarController = new NavbarController();
        navbarController.initialize(navbar);

        // account pane
        AccountPaneController accountPaneController = new AccountPaneController();
        accountPaneController.initialize(accountPane);

        // dashboard pane
        DashboardPaneController dashboardPaneController = new DashboardPaneController();
        dashboardPaneController.initialize(dashboardPane);

        // sidebar
        SidebarController sidebarController = new SidebarController();
        sidebarController.initialize(sidebar);

    }

    private void attachNotifiers() {
        ApplicationState.addPropertyChangeListener(e -> {
            if("activePane".equals(e.getPropertyName())) {
                activePaneChanged((Pane) e.getNewValue());
            }
        });
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