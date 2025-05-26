package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.VGroup;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import static com.jurai.ui.util.ControlWrappers.*;

public class AccountDashboardMenu extends AbstractMenu<HBox> {
    private HBox content;
    private Label title, subtitle;
    private AccountSettingsMenu accountSettingsMenu;
    private AppSettingsMenu appSettingsMenu;

    public static final String TITLE_TEMPLATE = "Bem-vindo(a), %s!";

    @Override
    protected void initControls() {
        content = new HBox();
        content.getStyleClass().add("pane");

        title = new Label("Bem-vindo(a)!");
        title.getStyleClass().addAll("header", "pb-4-i");
        subtitle = new Label("Configurações do aplicativo");
        subtitle.getStyleClass().addAll("subheader");
        subtitle.setPadding(Insets.EMPTY);
        subtitle.minHeightProperty().bind(title.heightProperty());
        subtitle.prefHeightProperty().bind(title.heightProperty());

        accountSettingsMenu = new AccountSettingsMenu();
        appSettingsMenu = new AppSettingsMenu();
    }

    @Override
    protected void layControls() {
        content.getStyleClass().addAll("p-x16-y8", "spacing-6");
        content.getChildren().addAll(
                new VGroup().withHgrow(Priority.ALWAYS).withChildren(
                        wrapVgrow(title, Priority.NEVER),
                        wrapVgrow(accountSettingsMenu.getContent())
                ),
                new VGroup().withHgrow(Priority.ALWAYS).withChildren(
                        subtitle,
                        wrapVgrow(wrapHgrow(appSettingsMenu.getContent()))
                )
        );
    }


    public Button getDeleteAccount() {
        return accountSettingsMenu.getDeleteAccount();
    }

    public Button getSaveChanges() {
        return accountSettingsMenu.getSaveChanges();
    }

    public Button getReset() {
        return accountSettingsMenu.getResetChanges();
    }

    public TextField getUsername() {
        return accountSettingsMenu.getUsername().getInput();
    }

    public TextField getEmail() {
        return accountSettingsMenu.getEmail().getInput();
    }

    public TextField getOab() {
        return accountSettingsMenu.getOab().getInput();
    }

    public Button getChangePasswrodBtn() {
        return accountSettingsMenu.getChangePasswordBtn();
    }

    public TextField getChangePassword() {
        return accountSettingsMenu.getChangePassword().getInput();
    }

    public TextField getConfirmPassword() {
        return accountSettingsMenu.getConfirmPassword().getInput();
    }




    public Label getTitle() {
        return title;
    }

    @Override
    public HBox getContent() {
        return content;
    }
}
