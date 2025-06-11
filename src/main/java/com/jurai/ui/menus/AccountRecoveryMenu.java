package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.TextFieldSet;
import com.jurai.ui.controls.fluent.VGroup;
import com.jurai.ui.util.ControlWrappers;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import lombok.Getter;

public class AccountRecoveryMenu extends AbstractMenu<BorderPane> {
    private BorderPane content;
    private VBox fields;
    private VBox urls;

    private Label title;
    private TextFieldSet email;
    @Getter
    private Button sendRequest;
    @Getter
    private Hyperlink login, createAccount;


    public AccountRecoveryMenu() {
        super();
    }

    @Override
    protected void initControls() {
        content = new BorderPane();
        fields = new VBox();
        fields.getStyleClass().add("vbox");

        title = new Label("Recupere sua conta");
        title.getStyleClass().add("header");
        HBox.setHgrow(title, Priority.ALWAYS);

        email = new TextFieldSet("Insira o e-mail da sua conta");
        email.getInput().setPromptText("E-mail da sua conta");
        HBox.setHgrow(email, Priority.ALWAYS);
        email.prefWidthProperty().bind(content.widthProperty());
        email.setMaxWidth(450);

        sendRequest = new Button("Enviar e-mail");
        sendRequest.getStyleClass().add("blue-button");
        HoverAnimator.animateAll(sendRequest, 1, 1);
        HBox.setHgrow(sendRequest, Priority.ALWAYS);

        login = new Hyperlink("Sei minha senha, quero fazer log-in!");
        createAccount = new Hyperlink("Não possuo conta");
    }

    @Override
    protected void layControls() {

        content.getStyleClass().addAll("form");
        fields.setAlignment(Pos.CENTER);
        fields.getChildren().addAll(
                SpacerFactory.vSpacer(Priority.ALWAYS),
                email,
                SpacerFactory.vSpacer(content.heightProperty().multiply(0.07)),
                sendRequest,
                SpacerFactory.vSpacer(Priority.ALWAYS),
                SpacerFactory.vSpacer(Priority.ALWAYS),
                new VGroup().withAlignment(Pos.CENTER).withChildren(
                        login,
                        createAccount
                )
        );

        BorderPane.setAlignment(title, Pos.CENTER);
        title.getStyleClass().add("border-pane-region");
        content.setTop(
            new VGroup().withStyleClass("border-pane-region").withBorderPaneAlignment(Pos.CENTER).withChildren(
                title,
                ControlWrappers.wrapAlignment(new Label("Para recuperar sua conta, vamos te enviar um email com um link de recuperação.\n Com ele, você poderá trocar a sua senha, e retornar aqui para fazer login."), TextAlignment.CENTER)
            )
        );
        content.getStyleClass().add("border-pane-region");
        content.setCenter(fields);
    }

    public TextField getEmail() {
        return email.getInput();
    }

    @Override
    public BorderPane getContent() {
        return content;
    }

}
