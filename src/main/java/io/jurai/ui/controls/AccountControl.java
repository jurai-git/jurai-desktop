package io.jurai.ui.controls;

import io.jurai.data.ApplicationState;
import io.jurai.ui.uti.ImageViewPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class AccountControl extends StackPane {
    private Hyperlink accountHyperlink;
    private MenuItem accountInfoBtn, dashboardBtn;
    private MenuButton dropdown;
    private ImageViewPane iconContainer;

    public AccountControl() {
        super();
        initControls();
        attachNotifiers();
        accountStateChanged(ApplicationState.isLoggedIn());
    }

    private void initControls() {
        accountHyperlink = new Hyperlink("Cadastro / Entrar");
        accountHyperlink.getStyleClass().add("navbar-button");
        accountInfoBtn = new MenuItem("Sua Conta");
        dashboardBtn = new MenuItem("Dashboard");

        dropdown = new MenuButton("Sua conta");
        ImageView usrIcon = new ImageView(new Image(getClass().getResource("/img/user.png").toExternalForm(), 255, 255, true, false));
        usrIcon.setPreserveRatio(true);
        usrIcon.setSmooth(true);
        iconContainer = new ImageViewPane(usrIcon);
        iconContainer.maxHeightProperty().bind(dropdown.heightProperty());

        dropdown.setGraphic(iconContainer);

        dropdown.getItems().add(accountInfoBtn);
        dropdown.getItems().add(dashboardBtn);
    }

    public void attachNotifiers() {
        ApplicationState.addLoggedInListener(this::accountStateChanged);
    }

    private void accountStateChanged(Boolean loggedIn) {
        this.getChildren().clear();
        if(loggedIn) {
            this.getChildren().add(dropdown);
        } else {
            this.getChildren().add(accountHyperlink);
        }
    }

    public Hyperlink getAccountHyperlink() {
        return accountHyperlink;
    }

    public MenuItem getAccountInfoBtn() {
        return accountInfoBtn;
    }

    public MenuItem getDashboardBtn() {
        return dashboardBtn;
    }
}
