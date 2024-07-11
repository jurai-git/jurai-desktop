package io.jurai.ui.controls;

import io.jurai.ui.menus.AccountPopupMenu;
import io.jurai.ui.util.ImageViewPane;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.PopOver;

public class AccountControl extends Button{
    private ImageViewPane iconContainer;
    AccountPopupMenu popup;
    private PopOver popOver;

    public AccountControl() {
        super("Sua Conta");
        initControls();
    }

    private void initControls() {
        ImageView usrIcon = new ImageView(new Image(getClass().getResource("/img/user.png").toExternalForm(), 255, 255, true, false));
        usrIcon.setPreserveRatio(true);
        usrIcon.setSmooth(true);
        iconContainer = new ImageViewPane(usrIcon);
        iconContainer.maxHeightProperty().bind(this.heightProperty());

        popup = new AccountPopupMenu();
        popOver = new PopOver(popup.getContent());
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.setCloseButtonEnabled(false);
        popOver.setDetachable(false);
        popOver.setTitle("Seja Bem-vindo(a)!");
        popOver.setHeaderAlwaysVisible(true);
        popOver.getStyleClass().add("popover");
        setGraphic(iconContainer);
    }


    public Button getExitBtn() {
        return popup.getExitBtn();
    }

    public Button getLoginBtn() {
        return popup.getLoginBtn();
    }

    public Button getRegisterBtn() {
        return popup.getRegisterBtn();
    }

    public BorderPane getContent() {
        return popup.getContent();
    }

    public Hyperlink getDashboardHpl() {
        return popup.getDashboardHpl();
    }

    public Hyperlink getAccountPaneHpl() {
        return popup.getAccountPaneHpl();
    }

    public PopOver getPopOver() {
        return popOver;
    }
}
