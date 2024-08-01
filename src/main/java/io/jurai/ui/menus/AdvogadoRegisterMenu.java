package io.jurai.ui.menus;

import io.jurai.ui.controller.Controllable;
import io.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class AdvogadoRegisterMenu extends AbstractMenu<BorderPane> implements Controllable {

    private BorderPane content;

    private TextField email, username;
    PasswordField password, confirmPassword;
    private CheckBox keepConnected;
    private Button create;
    private Label alreadyHasAccountLabel, headerLabel;
    private HBox alreadyHasAccountHBox;
    private Hyperlink loginHyperlink;
    private VBox fields;

    @Override
    protected void initControls() {
        content = new BorderPane();
        content.getStyleClass().addAll("advogado-register", "form");

        headerLabel = new Label("Crie sua conta");
        headerLabel.getStyleClass().add("header");


        email = new TextField();
        email.maxWidthProperty().bind(content.widthProperty().multiply(0.6));
        email.setPromptText("E-mail");
        VBox.setVgrow(email, Priority.ALWAYS);

        username = new TextField();
        username.maxWidthProperty().bind(content.widthProperty().multiply(0.6));
        username.setPromptText("Nome de usuário");
        VBox.setVgrow(username, Priority.ALWAYS);

        password = new PasswordField();
        password.maxWidthProperty().bind(content.widthProperty().multiply(0.45));
        password.setPromptText("Senha");
        VBox.setVgrow(password, Priority.ALWAYS);

        confirmPassword = new PasswordField();
        confirmPassword.maxWidthProperty().bind(content.widthProperty().multiply(0.45));
        confirmPassword.setPromptText("Confirmar senha");
        VBox.setVgrow(confirmPassword, Priority.ALWAYS);

        keepConnected = new CheckBox();
        keepConnected.setText("Mantenha-me conectado");
        VBox.setVgrow(keepConnected, Priority.ALWAYS);

        create = new Button("Criar conta");
        VBox.setVgrow(create, Priority.ALWAYS);

        alreadyHasAccountLabel = new Label("Já tem uma conta? ");
        loginHyperlink = new Hyperlink("Entre aqui!");

        fields = new VBox();
        fields.getStyleClass().add("vbox");
    }

    @Override
    protected void layControls() {
        alreadyHasAccountHBox = new HBox(alreadyHasAccountLabel, loginHyperlink);
        alreadyHasAccountHBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(alreadyHasAccountHBox, Priority.SOMETIMES);

        fields.getChildren().addAll(
                email,
                SpacerFactory.createVBoxSpacer(null),
                username,
                SpacerFactory.createVBoxSpacer(null),
                password,
                SpacerFactory.createVBoxSpacer(null),
                confirmPassword,
                SpacerFactory.createVBoxSpacer(null),
                keepConnected,
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                create,
                SpacerFactory.createVBoxSpacer(null),
                alreadyHasAccountHBox
        );

        content.setTop(headerLabel);
        headerLabel.getStyleClass().add("border-pane-region");
        BorderPane.setAlignment(headerLabel, Pos.CENTER);
        headerLabel.getStyleClass().add("border-pane-region");
        content.setCenter(fields);
    }

    @Override
    public BorderPane getContent() {
        return content;
    }

    public Button getCreateButton() {
        return create;
    }

    public CheckBox getKeepConnected() {
        return keepConnected;
    }

    public TextField getConfirmPassword() {
        return confirmPassword;
    }

    public TextField getPassword() {
        return password;
    }

    public TextField getEmail() {
        return email;
    }

    public TextField getUsername() {
        return username;
    }

    public HBox getAlreadyHasAccountHBox() {
        return alreadyHasAccountHBox;
    }

    public Hyperlink getLoginHyperlink() {
        return loginHyperlink;
    }
}