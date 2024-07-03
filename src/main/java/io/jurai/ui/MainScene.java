package io.jurai.ui;

import io.jurai.data.ApplicationState;
import io.jurai.ui.menus.LoginMenu;
import io.jurai.ui.menus.Navbar;
import io.jurai.ui.panes.AccountPane;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class MainScene {
    private Scene scene;
    private BorderPane borderPane;
    private Navbar navbar;
    private LoginMenu loginMenu;
    private AccountPane accountPane;

    public MainScene() {
        borderPane = new BorderPane();
        scene = new Scene(borderPane, 1200, 800);
        initControls();
        layControls();
    }

    private void initControls() {
        navbar = new Navbar();
        loginMenu = new LoginMenu();
        accountPane = new AccountPane();
    }

    private void layControls() {
        borderPane.setTop(navbar.getView());
        borderPane.setCenter(accountPane.getView());
        borderPane.getStyleClass().add("main");
    }

    public Scene getScene() {
        return scene;
    }
}
