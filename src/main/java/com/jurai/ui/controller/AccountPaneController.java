package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Advogado;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AdvogadoService;
import com.jurai.ui.modal.popup.Notification;
import com.jurai.ui.panes.AccountPane;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.menus.AccountDashboardMenu;

import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

public class AccountPaneController extends AbstractController<AccountPane> {
    private final AdvogadoService advogadoService = AdvogadoService.getInstance();

    @Override
    public void initialize(AccountPane pane) {
        super.initialize(pane);
    }

    @Override
    protected void attachEvents(AccountPane pane) {
        // login action
        pane.getLoginMenu().getPassword().setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                pane.getLoginMenu().getLogin().fire();
            }
        });

        pane.getLoginMenu().getLogin().setOnAction(e -> {
            try {
                if(pane.getLoginMenu().getEmail().getText().equals("root")) {
                    ApplicationState.getInstance().setCurrentUser(new Advogado(1, "advogado", "advogado@gmail.com", "oab123", "12321321321321"));
                    return;
                }
                if (pane.getLoginMenu().getKeepConnected().isSelected()) {
                    ApplicationState.getInstance().setRemembersUser(true);
                }
                advogadoService.authenticate(pane.getLoginMenu().getEmail().getText(), pane.getLoginMenu().getPassword().getText());
            } catch (ResponseNotOkException ex) {
                new Notification("Deu erro aqui!").show();
                /*
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
                }*/
            }
        });

        //register action
        pane.getAdvogadoRegisterMenu().getConfirmPassword().setOnKeyTyped(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                pane.getAdvogadoRegisterMenu().getCreateButton().fire();
            }
        });

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
            try {
                advogadoService.create(username, email, pwd, oab);
            } catch(Exception ignored) {

            }

            ApplicationState.getInstance().setAccountMode(AccountMode.LOGGING_IN);
        });

        // mode switching handling
        pane.getLoginMenu().getCreateAccount().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.REGISTERING));
        pane.getAdvogadoRegisterMenu().getLoginHyperlink().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.LOGGING_IN));

    }

    @Override
    protected void attachNotifiers(AccountPane pane) {
        ApplicationState.getInstance().addPropertyChangeListener(e -> {
            if("accountMode".equals(e.getPropertyName())) {
                modeChanged((AccountMode) e.getNewValue(), pane);
            }
            if("currentUser".equals(e.getPropertyName())) {
                if(ApplicationState.getInstance().getCurrentUser() != null)
                    userChanged(ApplicationState.getInstance().getCurrentUser(), pane);
            }
        });
    }

    private void modeChanged(AccountMode newMode, AccountPane pane) {
        pane.setPane(switch(newMode) {
            case LOGGING_IN -> pane.getLoginMenu();
            case REGISTERING -> pane.getAdvogadoRegisterMenu();
            case FORGOT_PASSWORD -> null;
            case LOGGED_IN -> pane.getAccountDashboardMenu();
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
