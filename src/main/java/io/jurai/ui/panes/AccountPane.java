package io.jurai.ui.panes;

import io.jurai.ui.menus.LoginMenu;
import javafx.scene.layout.StackPane;

public class AccountPane extends AbstractPane {
    private StackPane view;
    private LoginMenu loginMenu;

    public AccountPane() {
        super();
    }

    @Override
    protected void initControls() {
        view  = new StackPane();
        loginMenu = new LoginMenu();
        loginMenu.getContent().maxWidthProperty().bind(view.widthProperty().divide(8).multiply(5));
        loginMenu.getContent().maxHeightProperty().bind(view.heightProperty().divide(8).multiply(6));
    }

    @Override
    protected void layControls() {
        view.getChildren().add(loginMenu.getContent());
    }

    public LoginMenu getLoginMenu() {
        return loginMenu;
    }

    @Override
    public StackPane getView() {
        return view;
    }
}
