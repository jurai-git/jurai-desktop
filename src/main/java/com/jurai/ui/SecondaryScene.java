package com.jurai.ui;

import com.jurai.ui.controller.AccountPaneController;
import com.jurai.ui.panes.AccountPane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class SecondaryScene {
    private Scene scene;
    private BorderPane content;
    AccountPane accountPane;
    private ImageView headerLogo;
    private StackPane headerLogoWrapper;

    public SecondaryScene() {
        content = new BorderPane();
        content.getStyleClass().add("secondary-scene");
        accountPane = new AccountPane();
        headerLogo = new ImageView();
        Image logoImg = new Image(getClass().getResource("/img/jurai-text-white-antialias.png").toExternalForm());
        headerLogo.setImage(logoImg);
        headerLogo.setSmooth(true);
        headerLogo.setPreserveRatio(true);

        headerLogo.fitWidthProperty().bind(content.heightProperty().multiply(0.3));
        headerLogoWrapper = new StackPane(headerLogo);
        headerLogoWrapper.getStyleClass().add("logo");

        content.setCenter(accountPane.getView());
        content.setTop(headerLogoWrapper);
        scene = new Scene(content);
        attachControllers();
    }

    private void attachControllers() {
        // account pane controller
        AccountPaneController accountPaneController = new AccountPaneController();
        accountPaneController.initialize(accountPane);
    }

    public Scene getScene() {
        return scene;
    }
}
