package com.jurai.ui.panes;

import com.jurai.ui.menus.AbstractMenu;
import com.jurai.ui.menus.AccountDashboardMenu;
import com.jurai.ui.menus.AdvogadoRegisterMenu;
import com.jurai.ui.menus.LoginMenu;
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
        view.getStyleClass().add("account-pane");
        loginMenu = new LoginMenu();
        advogadoRegisterMenu = new AdvogadoRegisterMenu();
        accountDashboardMenu = new AccountDashboardMenu();
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
