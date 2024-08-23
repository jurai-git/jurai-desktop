package io.jurai.ui.menus;

import io.jurai.ui.animation.DefaultButtonAnimator;
import io.jurai.ui.controller.Controllable;
import io.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginMenu extends AbstractMenu<BorderPane> implements Controllable {
    private BorderPane content;
    private VBox fields;
    private VBox urls;

    private Label title;
    private TextField email;
    private PasswordField password;
    private CheckBox keepConnected;
    private Button login;
    private Hyperlink forgotPwd, createAccount;


    public LoginMenu() {
        super();
    }

    @Override
    protected void initControls() {
        content = new BorderPane();
        fields = new VBox();
        urls = new VBox();
        fields.getStyleClass().add("vbox");

        title = new Label("Bem-vindo de volta!");
        title.getStyleClass().add("header");
        HBox.setHgrow(title, Priority.ALWAYS);

        email = new TextField();
        email.setPromptText("E-mail");
        HBox.setHgrow(email, Priority.ALWAYS);
        email.maxWidthProperty().bind(content.widthProperty().divide(2));

        password = new PasswordField();
        password.setPromptText("Senha");
        HBox.setHgrow(password, Priority.ALWAYS);
        password.maxWidthProperty().bind(content.widthProperty().divide(2));

        keepConnected = new CheckBox("Mantenha-me conectado(a)");
        HBox.setHgrow(keepConnected, Priority.ALWAYS);

        login = new Button("Login");
        DefaultButtonAnimator.animate(login);
        HBox.setHgrow(login, Priority.ALWAYS);

        forgotPwd = new Hyperlink("Esqueci minha senha");
        createAccount = new Hyperlink("Não possuo conta");
    }

    @Override
    protected void layControls() {
        urls.getChildren().addAll(forgotPwd, createAccount);
        urls.setAlignment(Pos.CENTER);

        content.getStyleClass().addAll("form");
        fields.setAlignment(Pos.CENTER);
        fields.getChildren().addAll(
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                email, SpacerFactory.createVBoxSpacer(null),
                password, SpacerFactory.createVBoxSpacer(null),
                keepConnected,
                urls, SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                login
        );

        BorderPane.setAlignment(title, Pos.CENTER);
        title.getStyleClass().add("border-pane-region");
        content.setTop(title);
        content.getStyleClass().add("border-pane-region");
        content.setCenter(fields);
    }

    @Override
    public BorderPane getContent() {
        return content;
    }

    public TextField getEmail() {
        return email;
    }

    public TextField getPassword() {
        return password;
    }

    public CheckBox getKeepConnected() {
        return keepConnected;
    }

    public Button getLogin() {
        return login;
    }

    public Hyperlink getForgotPwd() {
        return forgotPwd;
    }

    public Hyperlink getCreateAccount() {
        return createAccount;
    }
}
