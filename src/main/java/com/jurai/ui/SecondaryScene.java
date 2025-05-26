package com.jurai.ui;

import com.jurai.data.ApplicationState;
import com.jurai.ui.controller.AccountPaneController;
import com.jurai.ui.panes.AccountPane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;

public class SecondaryScene {
    @Getter
    private Scene scene;
    @Getter
    private BorderPane content;
    @Getter
    private StackPane modalRoot;
    AccountPane accountPane;
    private ImageView headerLogo;
    private StackPane headerLogoWrapper;

    @Setter
    private Image darkImg, lightImg;

    public SecondaryScene() {
        content = new BorderPane();
        modalRoot = new StackPane(content);
        content.getStyleClass().add("secondary-scene");
        content.getStyleClass().add("root-pane");
        accountPane = new AccountPane();
        headerLogo = new ImageView();

        darkImg = new Image(getClass().getResource("/img/jurai-text-white-antialias.png").toExternalForm());
        lightImg = new Image(getClass().getResource("/img/jurai-text-dark-antialias.png").toExternalForm());

        if (ApplicationState.getInstance().isUseLightTheme()) {
            headerLogo.setImage(lightImg);
        } else {
            headerLogo.setImage(darkImg);
        }

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
        attachNotifiers();
    }

    private void attachNotifiers() {
        ApplicationState.getInstance().addPropertyChangeListener(l -> {
            if ("useLightTheme".equals(l.getPropertyName())) {
                if ((boolean) l.getNewValue()) {
                    headerLogo.setImage(lightImg);
                } else {
                    headerLogo.setImage(darkImg);
                }
            }
        });
    }

    private void attachControllers() {
        // account pane controller
        AccountPaneController accountPaneController = new AccountPaneController();
        accountPaneController.initialize(accountPane);
    }

}
