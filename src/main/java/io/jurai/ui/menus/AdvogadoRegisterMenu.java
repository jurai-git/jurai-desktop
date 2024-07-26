package io.jurai.ui.menus;

import io.jurai.ui.controller.Controllable;
import io.jurai.ui.util.SpacerFactory;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class AdvogadoRegisterMenu extends AbstractMenu<BorderPane> implements Controllable {

    private BorderPane content;

    private TextField email, password, confirmPassword;
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

        headerLabel = new Label("Criar conta");
        headerLabel.getStyleClass().add("header");

        email = new TextField();
        email.setPromptText("E-mail");
        VBox.setVgrow(email, Priority.SOMETIMES);

        password = new TextField();
        password.setPromptText("Senha");
        VBox.setVgrow(password, Priority.SOMETIMES);

        confirmPassword = new TextField();
        confirmPassword.setPromptText("Confirmar senha");
        VBox.setVgrow(confirmPassword, Priority.SOMETIMES);

        keepConnected = new CheckBox();
        keepConnected.setText("Mantenha-me conectado");
        VBox.setVgrow(keepConnected, Priority.SOMETIMES);

        create = new Button("Criar conta");
        VBox.setVgrow(create, Priority.SOMETIMES);

        alreadyHasAccountLabel = new Label("Já tem uma conta? ");
        loginHyperlink = new Hyperlink("Entre aqui!");

        fields = new VBox();
        fields.getStyleClass().add("vbox");
    }

    @Override
    protected void layControls() {
        alreadyHasAccountHBox = new HBox(alreadyHasAccountLabel, loginHyperlink);
        VBox.setVgrow(alreadyHasAccountHBox, Priority.SOMETIMES);

        fields.getChildren().addAll(
                email,
                SpacerFactory.createVBoxSpacer(),
                password,
                SpacerFactory.createVBoxSpacer(),
                confirmPassword,
                SpacerFactory.createVBoxSpacer(),
                keepConnected,
                SpacerFactory.createVBoxSpacer(),
                create,
                SpacerFactory.createVBoxSpacer(),
                alreadyHasAccountHBox
        );

        content.setTop(headerLabel);
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

    public HBox getAlreadyHasAccountHBox() {
        return alreadyHasAccountHBox;
    }

    public Hyperlink getLoginHyperlink() {
        return loginHyperlink;
    }
}
