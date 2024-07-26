package io.jurai.ui.controller;

import io.jurai.data.ApplicationState;
import io.jurai.data.service.AdvogadoService;
import io.jurai.ui.menus.LoginMenu;
import io.jurai.ui.panes.AccountPane;
import io.jurai.ui.util.AccountMode;
import javafx.scene.control.Alert;

public class AccountPaneController extends AbstractController<AccountPane> {
    AdvogadoService s;

    @Override
    public void initialize(AccountPane pane) {
        s = new AdvogadoService();
        super.initialize(pane);
    }

    @Override
    protected void attachEvents(AccountPane pane) {
        // login action
        pane.getLoginMenu().getLogin().setOnAction(e -> {
            try {
                s.authenticate(pane.getLoginMenu().getEmail().getText(), pane.getLoginMenu().getPassword().getText());
                new Alert(Alert.AlertType.INFORMATION, "Você foi logado com successo!").show();
            } catch (IllegalArgumentException ignored) {
                new Alert(Alert.AlertType.WARNING, "Suas credenciais estão incorretas!").show();
            }
        });


        // mode switching handling
        pane.getLoginMenu().getCreateAccount().setOnAction(_ -> ApplicationState.setAccountMode(AccountMode.REGISTERING));
        pane.getAdvogadoRegisterMenu().getLoginHyperlink().setOnAction(_ -> ApplicationState.setAccountMode(AccountMode.LOGGING_IN));

    }

    @Override
    protected void attachNotifiers(AccountPane pane) {
        ApplicationState.addPropertyChangeListener(e -> {
            if("accountMode".equals(e.getPropertyName())) {
                modeChanged((AccountMode) e.getNewValue(), pane);
            }
        });
    }

    private void modeChanged(AccountMode newMode, AccountPane pane) {
        pane.setPane(switch(newMode) {
            case AccountMode.LOGGING_IN -> pane.getLoginMenu();
            case AccountMode.REGISTERING -> pane.getAdvogadoRegisterMenu();
            case FORGOT_PASSWORD -> null;
            case AccountMode.LOGGED_IN -> pane.getAccountDashboardMenu();
        });
    }
}
