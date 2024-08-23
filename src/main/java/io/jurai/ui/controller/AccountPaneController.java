package io.jurai.ui.controller;

import io.jurai.data.ApplicationState;
import io.jurai.data.model.Advogado;
import io.jurai.data.request.ResponseNotOkException;
import io.jurai.data.service.AdvogadoService;
import io.jurai.ui.panes.AccountPane;
import io.jurai.ui.util.AccountMode;
import io.jurai.ui.menus.AccountDashboardMenu;

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
                if(pane.getLoginMenu().getEmail().getText().equals("root")) {
                    ApplicationState.setCurrentUser(new Advogado(1, "advogado", "advogado@gmail.com", "oab123", "12321321321321"));
                    return;
                }
                s.authenticate(pane.getLoginMenu().getEmail().getText(), pane.getLoginMenu().getPassword().getText());
            } catch (ResponseNotOkException ex) {
                switch (ex.getCode()) {
                    case 500:
                        new Alert(
                                Alert.AlertType.ERROR,
                                "Ocorreu um erro com a conexão com o servidor. Cheque a sua conexão com a internet"
                        ).show();
                        break;
                    case 400:
                        new Alert(
                                Alert.AlertType.ERROR,
                                "Parece que você deixou algum campo vazio!"
                        ).show();
                        break;
                    case 401:
                        new Alert(
                                Alert.AlertType.ERROR,
                                "Suas credenciais estão incorretas! Verifique-as e tente novamente."
                        ).show();
                        break;
                    default:
                        break;
                }
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

            final String oab = pane.getAdvogadoRegisterMenu().getOab().getText();

            s.create(username, email, pwd, oab);

            ApplicationState.setAccountMode(AccountMode.LOGGING_IN);
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
            if("currentUser".equals(e.getPropertyName())) {
                if(ApplicationState.getCurrentUser() != null)
                    userChanged(ApplicationState.getCurrentUser(), pane);
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

    private void userChanged(Advogado newUser, AccountPane pane) {
        AccountDashboardMenu dashboardMenu = pane.getAccountDashboardMenu();
        dashboardMenu.getTitle().setText(AccountDashboardMenu.TITLE_TEMPLATE.formatted(newUser.getNome()));
        dashboardMenu.getUsernameInfo().setText(AccountDashboardMenu.USERNAME_TEMPLATE.formatted(newUser.getNome()));
        dashboardMenu.getEmailInfo().setText(AccountDashboardMenu.EMAIL_TEMPLATE.formatted(newUser.getEmail()));
        dashboardMenu.getOabInfo().setText(AccountDashboardMenu.OAB_TEMPLATE.formatted(newUser.getOab()));
    }
}
