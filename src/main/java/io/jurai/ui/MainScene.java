package io.jurai.ui;

import io.jurai.data.ApplicationState;
import io.jurai.data.notifier.StateNotifier;
import io.jurai.ui.controller.NavbarController;
import io.jurai.data.model.Pane;
import io.jurai.ui.menus.LoginMenu;
import io.jurai.ui.menus.Navbar;
import io.jurai.ui.panes.*;
import io.jurai.util.UILogger;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MainScene {
    private Scene scene;
    private BorderPane mainPane;
    private Navbar navbar;
    private LoginMenu loginMenu;

    private AccountPane accountPane;
    private DashboardPane dashboardPane;
    private HomePane homePane;
    private PlanPane planPane;
    private QuickQueryPane quickQueryPane;

    public MainScene() {
        mainPane = new BorderPane();
        mainPane.getStyleClass().add("root-pane");
        scene = new Scene(mainPane, 1200, 800);
        initControls();
        layControls();
        attachControllers();
        attachNotifiers();
    }

    private void initControls() {
        navbar = new Navbar();
        loginMenu = new LoginMenu();
        accountPane = new AccountPane();
        dashboardPane = new DashboardPane();
        homePane = new HomePane();
        planPane = new PlanPane();
        quickQueryPane = new QuickQueryPane();
    }

    private void layControls() {
        activePaneChanged(ApplicationState.getActivePane());
        mainPane.setTop(navbar.getView());
        mainPane.getStyleClass().add("main");
    }

    private void attachControllers() {
        NavbarController navbarController = new NavbarController(navbar);
    }

    private void attachNotifiers() {
        ApplicationState.addActivePaneNotifier(this::activePaneChanged);
    }

    public Scene getScene() {
        return scene;
    }

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
                mainPane.setCenter(planPane.getView());
                break;
            default:
                UILogger.logError("Tried switching to invalid main pane");
                return;
        }
        UILogger.log("active pane changed to " + pane.name());
    }
}
