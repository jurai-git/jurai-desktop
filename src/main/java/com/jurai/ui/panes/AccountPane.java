package com.jurai.ui.panes;

import com.jurai.data.service.AdvogadoService;
import com.jurai.ui.menus.*;
import com.jurai.ui.viewmodel.LoginMenuVM;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class AccountPane extends AbstractPane {
    private StackPane view;

    private LoginMenu loginMenu;
    private AdvogadoRegisterMenu advogadoRegisterMenu;
    private AccountDashboardMenu accountDashboardMenu;
    private AccountRecoveryMenu accountRecoveryMenu;
    private AccountRecoveryDone accountRecoveryDone;

    private LoginMenuVM loginMenuVM;

    public AccountPane() {
        super();
    }

    @Override
    protected void initControls() {
        view  = new StackPane();
        view.getStyleClass().add("account-pane");
        loginMenuVM = new LoginMenuVM(AdvogadoService.getInstance());
        loginMenu = new LoginMenu(loginMenuVM);
        advogadoRegisterMenu = new AdvogadoRegisterMenu();
        accountDashboardMenu = new AccountDashboardMenu();
        accountRecoveryMenu = new AccountRecoveryMenu();
        accountRecoveryDone = new AccountRecoveryDone();
    }

    @Override
    protected void layControls() {
        view.getChildren().add(loginMenu);
    }

    private void removeAll() {
        view.getChildren().removeAll(view.getChildren());
    }

    public void setPane(Node pane) {
        removeAll();
        view.getChildren().add(pane);
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

    public AccountRecoveryMenu getAccountRecoveryMenu() {
        return accountRecoveryMenu;
    }

    public AccountRecoveryDone getAccountRecoveryDone() {
        return accountRecoveryDone;
    }

    @Override
    public StackPane getView() {
        return view;
    }
}
