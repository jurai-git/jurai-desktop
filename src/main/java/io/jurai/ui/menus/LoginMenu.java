package io.jurai.ui.menus;

import io.jurai.ui.uti.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LoginMenu {
    private VBox view;
    private HBox urls;

    Label title;
    TextField email, password;
    CheckBox keepConnected;
    Button login;
    Hyperlink forgotPwd, createAccount;

    public LoginMenu() {
        view = new VBox();
        urls = new HBox();

        initControls();
        layControls();
    }

    private void initControls() {
        title = new Label("Bem-vindo de volta!");
        title.getStyleClass().add("title");
        HBox.setHgrow(title, Priority.ALWAYS);

        email = new TextField();
        email.setPromptText("E-mail");
        HBox.setHgrow(email, Priority.ALWAYS);
        email.maxWidthProperty().bind(view.widthProperty().divide(2));

        password = new TextField();
        password.setPromptText("Senha");
        HBox.setHgrow(password, Priority.ALWAYS);
        password.maxWidthProperty().bind(view.widthProperty().divide(2));

        keepConnected = new CheckBox("Mantenha-me conectado(a)");
        HBox.setHgrow(keepConnected, Priority.ALWAYS);

        login = new Button("Login");
        HBox.setHgrow(login, Priority.ALWAYS);

        forgotPwd = new Hyperlink("Esqueci minha senha");
        createAccount = new Hyperlink("Não possuo conta");
        urls.setAlignment(Pos.CENTER);
        urls.getChildren().addAll(SpacerFactory.createHBoxSpacer(), createAccount, SpacerFactory.createHBoxSpacer(), forgotPwd, SpacerFactory.createHBoxSpacer());
        HBox.setHgrow(urls, Priority.ALWAYS);
    }

    private void layControls() {
        view.getStyleClass().add("floating-container");
        view.setAlignment(Pos.CENTER);
        view.getChildren().addAll(title, SpacerFactory.createVBoxSpacer(), email, SpacerFactory.createVBoxSpacer(),
                password, SpacerFactory.createVBoxSpacer(), keepConnected, SpacerFactory.createVBoxSpacer(),
                urls, SpacerFactory.createVBoxSpacer(), login);
    }

    public VBox getView() {
        return view;
    }
}
