package io.jurai.ui.panes;

import io.jurai.ui.menus.AbstractMenu;
import io.jurai.ui.menus.AccountDashboardMenu;
import io.jurai.ui.menus.AdvogadoRegisterMenu;
import io.jurai.ui.menus.LoginMenu;
import javafx.scene.layout.StackPane;

public class AccountPane extends AbstractPane {
    private StackPane view;
    private LoginMenu loginMenu;
    private AdvogadoRegisterMenu advogadoRegisterMenu;
    private AccountDashboardMenu accountDashboardMenu;

    public AccountPane() {
        super();
    }

    @Override
    protected void initControls() {
        view  = new StackPane();
        view.getStyleClass().add("content");
        loginMenu = new LoginMenu();
        loginMenu.getContent().maxWidthProperty().bind(view.widthProperty().divide(8).multiply(5));
        loginMenu.getContent().maxHeightProperty().bind(view.heightProperty().divide(8).multiply(6));

        advogadoRegisterMenu = new AdvogadoRegisterMenu();
        advogadoRegisterMenu.getContent().maxWidthProperty().bind(view.widthProperty().divide(8).multiply(5));
        advogadoRegisterMenu.getContent().maxHeightProperty().bind(view.heightProperty().divide(8).multiply(6));

    }

    @Override
    protected void layControls() {
        view.getChildren().add(loginMenu.getContent());
    }

    private void removeAll() {
        view.getChildren().removeAll(view.getChildren());
    }

    public void setPane(AbstractMenu<?> pane) {
        removeAll();
        view.getChildren().add(pane.getContent());
    }


    public LoginMenu getLoginMenu() {
        return loginMenu;
    }

    public AdvogadoRegisterMenu getAdvogadoRegisterMenu() {
        return advogadoRegisterMenu;
    }

    public AccountDashboardMenu getAccountDashboardMenu() {
        return accountDashboardMenu;
    }

    @Override
    public StackPane getView() {
        return view;
    }
}
