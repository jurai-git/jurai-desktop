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
    private StackPane modalRoot;
    AccountPane accountPane;
    private ImageView headerLogo;
    private StackPane headerLogoWrapper;

    public SecondaryScene() {
        content = new BorderPane();
        modalRoot = new StackPane(content);
        content.getStyleClass().add("secondary-scene");
        content.getStyleClass().add("root-pane");
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
        content.getStyleClass().add("root");
        scene = new Scene(modalRoot);
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

    public StackPane getModalRoot() {
        return modalRoot;
    }

    public BorderPane getContent() {
        return content;
    }
}
