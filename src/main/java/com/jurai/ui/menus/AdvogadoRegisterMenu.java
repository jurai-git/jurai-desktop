package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AdvogadoRegisterMenu extends AbstractMenu<BorderPane> {

    private BorderPane content;

    private TextField email, username, oab;
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

        oab = new TextField();
        oab.maxWidthProperty().bind(content.widthProperty().multiply(0.6));
        oab.setPromptText("OAB");
        VBox.setVgrow(oab, Priority.ALWAYS);

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
        HoverAnimator.animateAll(create, 1, 1);
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
                SpacerFactory.vSpacer(Priority.ALWAYS),
                email,
                SpacerFactory.vSpacer(14),
                username,
                SpacerFactory.vSpacer(14),
                oab,
                SpacerFactory.vSpacer(16),
                SpacerFactory.vSpacer(content.heightProperty().multiply(0.03)),
                password,
                SpacerFactory.vSpacer(12),
                confirmPassword,
                SpacerFactory.vSpacer(12),
                keepConnected,
                SpacerFactory.vSpacer(Priority.ALWAYS),
                create,
                SpacerFactory.vSpacer(content.heightProperty().multiply(0.05)),
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

    public TextField getOab() {
        return oab;
    }

    public HBox getAlreadyHasAccountHBox() {
        return alreadyHasAccountHBox;
    }

    public Hyperlink getLoginHyperlink() {
        return loginHyperlink;
    }
}
