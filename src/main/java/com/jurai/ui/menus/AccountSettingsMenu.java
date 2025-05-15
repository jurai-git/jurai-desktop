package com.jurai.ui.menus;
import com.jurai.ui.controls.FieldSet;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.TextFieldSet;
import com.jurai.ui.controls.VGroup;
import com.jurai.ui.util.SpacerFactory;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


import java.awt.*;

import static com.jurai.ui.util.ControlWrappers.*;

public class AccountSettingsMenu extends AbstractMenu<VBox> {
    private VBox content;
    private ImageView userImageView;
    private Label usernameLabel, oabLabel;
    private TextFieldSet username, email, changePassword, confirmPassword, oab;
    private Button saveChanges, resetChanges, deleteAccount;

    @Override
    protected void initControls() {
        content = new VBox();
        final Image img = new Image(getClass().getResource("/img/user-default.jpg").toExternalForm());
        userImageView = new ImageView(img);
        userImageView.setSmooth(true);
        userImageView.setPreserveRatio(true);
        userImageView.getStyleClass().addAll("bg-radius-full", "border-radius-full");
        userImageView.setFitWidth(128);
        userImageView.setFitHeight(128);
        userImageView.setClip(new Circle(64, 64, 64));

        username = new TextFieldSet("Nome de usuário");
        username.getInput().getStyleClass().add("text-field-base");
        username.getInput().setPromptText("username");
        usernameLabel = new Label();
        usernameLabel.getStyleClass().addAll("subheader", "");
        usernameLabel.textProperty().bind(username.getInput().textProperty());

        email = new TextFieldSet("E-mail");
        email.getInput().getStyleClass().add("text-field-base");
        email.getInput().setPromptText("email");

        changePassword = new TextFieldSet("Troque sua senha");
        changePassword.getInput().getStyleClass().add("text-field-base");
        changePassword.getInput().setPromptText("Nova senha");

        confirmPassword = new TextFieldSet("Confirme a senha");
        confirmPassword.getInput().getStyleClass().add("text-field-base");
        confirmPassword.getInput().setPromptText("Confirme a senha");

        oab = new TextFieldSet("OAB");
        oab.getInput().getStyleClass().add("text-field-base");
        oab.getInput().setEditable(false);
        oab.getInput().setText("243101");
        oabLabel = new Label();
        oabLabel.getStyleClass().addAll("text-secondary");
        oabLabel.textProperty().bind(Bindings.createStringBinding(
                () -> "OAB: " + oab.getInput().getText(),
                oab.getInput().textProperty()
        ));

        saveChanges = new Button("Salvar");
        saveChanges.getStyleClass().addAll("blue-button");
        saveChanges.setDisable(true);

        resetChanges = new Button("Limpar dados");
        resetChanges.setDisable(true);

        deleteAccount = new Button("Deletar conta");
        deleteAccount.getStyleClass().addAll("red-button");
    }

    @Override
    protected void layControls() {
        content.getChildren().addAll(
                wrapVgrow(new VGroup().withStyleClass("spacing-3", "small-content-box", "p-6").withChildren(
                        new HGroup().withStyleClass("spacing-3", "pl-4").withChildren(
                                userImageView,
                                new VGroup().withChildren(
                                        SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                                        usernameLabel,
                                        oabLabel,
                                        SpacerFactory.createVBoxSpacer(Priority.ALWAYS)
                                )
                        ),
                        SpacerFactory.createVBoxSpacer(content.heightProperty().multiply(0.04)),
                        username,
                        email,
                        oab,
                        SpacerFactory.createVBoxSpacer(content.heightProperty().multiply(0.03).add(4)),
                        wrapStyleClasses(new Label("Troque sua senha"), "subheader"),
                        changePassword,
                        confirmPassword,
                        SpacerFactory.createVBoxSpacer(Priority.ALWAYS)
                )),
                new HGroup().withStyleClass("spacing-4").withChildren(
                        resetChanges,
                        SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                        deleteAccount,
                        saveChanges
                )
        );
        content.getStyleClass().add("spacing-4");
    }

    public Button getDeleteAccount() {
        return deleteAccount;
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
