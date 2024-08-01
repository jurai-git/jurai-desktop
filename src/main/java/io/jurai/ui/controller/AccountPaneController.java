package io.jurai.ui.controller;

import io.jurai.data.ApplicationState;
import io.jurai.data.model.Advogado;
import io.jurai.data.service.AdvogadoService;
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

        //register action
        pane.getAdvogadoRegisterMenu().getCreateButton().setOnAction(e -> {
            final String pwd = pane.getAdvogadoRegisterMenu().getPassword().getText();
            if(!
                    pwd.equals(
                    pane.getAdvogadoRegisterMenu().getConfirmPassword().getText()
            )) {
                new Alert(Alert.AlertType.ERROR, "As duas senhas não coincidem!").show();
                return;
            }

            if(pwd.length() < 8) {
                new Alert(Alert.AlertType.ERROR, "A senha deve ter ao menos 8 caracteres!").show();
                return;
            }

            final String username = pane.getAdvogadoRegisterMenu().getUsername().getText();
            if(username.length() < 3) {
                new Alert(Alert.AlertType.ERROR, "O nome de usuário deve ter ao menos 3 caracteres!").show();
                return;
            }

            final String email = pane.getAdvogadoRegisterMenu().getEmail().getText();
            if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                new Alert(Alert.AlertType.ERROR, "O email fornecido é inválido!").show();
                return;
            }

            Advogado advogado = new Advogado(
                username,
                email,
                pwd
            );
            s.create(advogado);
            s.authenticate(email, pwd);
            new Alert(Alert.AlertType.INFORMATION, "Advogado criado com sucesso!");
        });


        // mode switching handling
        pane.getLoginMenu().getCreateAccount().setOnAction(e -> ApplicationState.setAccountMode(AccountMode.REGISTERING));
        pane.getAdvogadoRegisterMenu().getLoginHyperlink().setOnAction(e -> ApplicationState.setAccountMode(AccountMode.LOGGING_IN));

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
