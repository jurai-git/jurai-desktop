package io.jurai.ui.menus;

import io.jurai.ui.animation.DefaultButtonAnimator;
import io.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class AccountDashboardMenu extends AbstractMenu<BorderPane> {
    private BorderPane content, centerBox;
    private Label title, subtitle, usernameInfo, emailInfo, oabInfo;
    private Button editInfo, deleteAccount;
    private VBox infoList;
    private HBox buttonsBox, centerContent;

    public static final String TITLE_TEMPLATE = "Bem-vindo(a), %s!";
    public static final String USERNAME_TEMPLATE = "Username: %s";
    public static final String EMAIL_TEMPLATE = "E-mail: %s";
    public static final String OAB_TEMPLATE = "OAB: %s";

    @Override
    protected void initControls() {
        content = new BorderPane();
        content.getStyleClass().addAll("content", "border-box");

        title = new Label("Bem-vindo(a)!");
        title.getStyleClass().add("header");

        subtitle = new Label("Aqui estão algumas informações da sua conta");
        subtitle.getStyleClass().add("subheader");

        usernameInfo = new Label("usernameInfo");
        usernameInfo.setAlignment(Pos.TOP_LEFT);
        emailInfo = new Label("emailInfo");
        emailInfo.setAlignment(Pos.TOP_LEFT);
        oabInfo = new Label("oabInfo");
        oabInfo.setAlignment(Pos.TOP_LEFT);

        editInfo = new Button("Editar informações");
        DefaultButtonAnimator.animate(editInfo);
        deleteAccount = new Button("Deletar conta");
        DefaultButtonAnimator.animate(deleteAccount);
        deleteAccount.getStyleClass().add("red-button");

        centerBox = new BorderPane();
        infoList = new VBox();
        infoList.getStyleClass().add("content-box");
        buttonsBox = new HBox();

        centerContent = new HBox();
    }

    @Override
    protected void layControls() {

        HBox.setHgrow(editInfo, Priority.ALWAYS);
        HBox.setHgrow(deleteAccount, Priority.ALWAYS);
        buttonsBox.getChildren().addAll(
                editInfo,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                deleteAccount
        );

        VBox.setVgrow(usernameInfo, Priority.ALWAYS);
        VBox.setVgrow(emailInfo, Priority.ALWAYS);
        VBox.setVgrow(oabInfo, Priority.ALWAYS);
        VBox.setVgrow(buttonsBox, Priority.NEVER);
        infoList.getChildren().addAll(
                usernameInfo,
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                emailInfo,
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                oabInfo,
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                buttonsBox
        );

        Region r = new Region();
        HBox.setHgrow(infoList, Priority.ALWAYS);
        HBox.setHgrow(r, Priority.ALWAYS);
        centerContent.getChildren().addAll(infoList, r);

        centerBox.setTop(subtitle);
        centerBox.setCenter(centerContent);

        content.setTop(title);
        content.setCenter(centerBox);
    }

    public Label getUsernameInfo() {
        return usernameInfo;
    }

    public Label getEmailInfo() {
        return emailInfo;
    }

    public Label getOabInfo() {
        return oabInfo;
    }

    public Button getDeleteAccount() {
        return deleteAccount;
    }

    public Button getEditInfo() {
        return editInfo;
    }

    public Label getTitle() {
        return title;
    }

    @Override
    public BorderPane getContent() {
        return content;
    }
}
