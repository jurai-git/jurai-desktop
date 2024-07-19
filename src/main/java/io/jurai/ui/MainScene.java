package io.jurai.ui;

import io.jurai.data.ApplicationState;
import io.jurai.ui.controller.NavbarController;
import io.jurai.data.model.Pane;
import io.jurai.ui.menus.LoginMenu;
import io.jurai.ui.panes.Navbar;
import io.jurai.ui.panes.*;
import io.jurai.ui.panes.layout.NodeConstraints;
import io.jurai.ui.panes.layout.ProportionPane;
import io.jurai.util.EventLogger;
import io.jurai.util.UILogger;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.BorderPane;

import java.awt.*;

public class MainScene {
    private Scene scene;
    private ProportionPane mainPane;
    private Navbar navbar;
    private LoginMenu loginMenu;
    private Sidebar sidebar;

    private AccountPane accountPane;
    private DashboardPane dashboardPane;
    private HomePane homePane;
    private PlanPane planPane;
    private QuickQueryPane quickQueryPane;

    public MainScene() {
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
        //activePaneChanged(ApplicationState.getActivePane());
        mainPane.addConstraints(navbar.getView(), new NodeConstraints(0, 0, 1, 0.08f));
        mainPane.getChildren().add(navbar.getView());

        mainPane.addConstraints(sidebar.getView(), new NodeConstraints(0, 0, 0.15f, 1));
        mainPane.getChildren().add(sidebar.getView());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        scene = new Scene(mainPane, screenSize.width * 0.7, screenSize.height * 0.7, false, SceneAntialiasing.BALANCED);
    }

    private void attachControllers() {
        NavbarController navbarController = new NavbarController();
        navbarController.initialize(navbar);
    }

    private void attachNotifiers() {
        ApplicationState.addPropertyChangeListener(e -> {
            if("activePane".equals(e.getPropertyName())) {
                //activePaneChanged((Pane) e.getNewValue());
            }
        });
    }

    public Scene getScene() {
        return scene;
    }
    /*
    private void activePaneChanged(Pane pane) {
        switch (pane) {
            case AccountPane:
                mainPane.setCenter(accountPane.getView());
                break;
            case DashboardPane:
                mainPane.setCenter(dashboardPane.getView());
                break;
            case HomePane:
                mainPane.setCenter(homePane.getView());
                break;
            case PlanPane:
                mainPane.setCenter(planPane.getView());
                break;
            case QuickQueryPane:
                mainPane.setCenter(quickQueryPane.getView());
                break;
            default:
                UILogger.logError("Tried switching to invalid main pane");
                return;
        }
        UILogger.log("active pane changed to " + pane.name());
    }*/
}