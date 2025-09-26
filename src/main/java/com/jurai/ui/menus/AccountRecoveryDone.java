package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.util.SpacerFactory;
import dev.mgcvale.fluidfx.components.groups.VGroup;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import static dev.mgcvale.fluidfx.components.layout.Wrappers.wAlignment;

public class AccountRecoveryDone extends AbstractMenu<BorderPane> {
    private BorderPane content;
    private VBox fields;
    private VBox urls;

    private Label title;
    private Button returnToLogin;
    private Hyperlink createAccount, makeNewRequest;


    public AccountRecoveryDone() {
        super();
    }

    @Override
    protected void initControls() {
        content = new BorderPane();
        fields = new VBox();
        fields.getStyleClass().add("vbox");

        title = new Label("E-mail enviado");
        title.getStyleClass().add("header");
        HBox.setHgrow(title, Priority.ALWAYS);

        returnToLogin = new Button("Retornar para o log-in");
        returnToLogin.getStyleClass().add("blue-button");
        HoverAnimator.animateAll(returnToLogin, 1, 1);
        HBox.setHgrow(returnToLogin, Priority.ALWAYS);

        createAccount = new Hyperlink("Criar uma conta");

        makeNewRequest = new Hyperlink("Fazer uma nova requisição");
        makeNewRequest.setTextAlignment(TextAlignment.CENTER);
    }

    @Override
    protected void layControls() {

        content.getStyleClass().addAll("form");
        fields.setAlignment(Pos.CENTER);
        fields.getChildren().addAll(
            SpacerFactory.vSpacer(Priority.ALWAYS),
            new Label("Já trocou sua senha?"),
            SpacerFactory.vSpacer(12),
            returnToLogin,
            SpacerFactory.vSpacer(Priority.ALWAYS)
        );

        BorderPane.setAlignment(title, Pos.CENTER);
        title.getStyleClass().add("border-pane-region");
        content.setTop(
            new VGroup().wStyleClass("border-pane-region", "p-8").wBorderPaneAlignment(Pos.CENTER).wChildren(
                title,
                new VGroup().wAlignment(Pos.CENTER).wChildren(
                    wAlignment(new Label("O e-mail de recuperação foi enviado com sucesso.\nCheque sua inbox e caixa de spam.\nCaso o email não chegue em 5 minutos, você poderá fazer uma nova requisição."), TextAlignment.CENTER),
                    makeNewRequest
                )
            )
        );
        content.getStyleClass().add("border-pane-region");
        content.setCenter(fields);
    }


    @Override
    public BorderPane getContent() {
        return content;
    }

    public Button getReturnToLogin() {
        return this.returnToLogin;
    }

    public Hyperlink getCreateAccount() {
        return this.createAccount;
    }

    public Hyperlink getMakeNewRequest() {
        return this.makeNewRequest;
    }
}
