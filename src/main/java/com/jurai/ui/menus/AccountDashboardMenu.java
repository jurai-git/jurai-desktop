package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.VGroup;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import static com.jurai.ui.util.ControlWrappers.wrapVgrow;

public class AccountDashboardMenu extends AbstractMenu<BorderPane> {
    private BorderPane content;
    private Label title, subtitle;
    private AccountSettingsMenu accountSettingsMenu;

    public static final String TITLE_TEMPLATE = "Bem-vindo(a), %s!";
    public static final String USERNAME_TEMPLATE = "Username: %s";
    public static final String EMAIL_TEMPLATE = "E-mail: %s";
    public static final String OAB_TEMPLATE = "OAB: %s";

    @Override
    protected void initControls() {
        content = new BorderPane();
        content.getStyleClass().add("pane");

        title = new Label("Bem-vindo(a)!");
        title.getStyleClass().add("header");

        subtitle = new Label("Aqui estão algumas informações da sua conta");
        subtitle.getStyleClass().add("subheader");
        accountSettingsMenu = new AccountSettingsMenu();
    }

    @Override
    protected void layControls() {
        content.getStyleClass().addAll("p-x16-y8");
        content.setTop(title);
        content.setCenter(new HGroup().withChildren(
                wrapVgrow(accountSettingsMenu.getContent())
        ));
    }


    public Button getDeleteAccount() {
        return accountSettingsMenu.getDeleteAccount();
    }


    public Label getTitle() {
        return title;
    }

    @Override
    public BorderPane getContent() {
        return content;
    }
}
