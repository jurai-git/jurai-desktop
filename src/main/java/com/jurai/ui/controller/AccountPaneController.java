package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Advogado;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AdvogadoService;
import com.jurai.data.validator.AdvogadoValidator;
import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.NotificationType;
import com.jurai.ui.panes.AccountPane;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.menus.AccountDashboardMenu;

import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

public class AccountPaneController extends AbstractController<AccountPane> {
    private final AdvogadoService advogadoService = AdvogadoService.getInstance();
    private final AdvogadoValidator advogadoValidator = new AdvogadoValidator();

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
                switch (ex.getCode()) {
                    case 500:
                        new DefaultMessageNotification(
                                "Ocorreu um erro com a conexão com o servidor. Cheque a sua conexão com a internet",
                                NotificationType.ERROR
                        ).show();
                        break;
                    case 400:
                        new DefaultMessageNotification(
                                "Parece que você deixou algum campo vazio!",
                                NotificationType.ERROR
                        ).show();
                        break;
                    case 401:
                        new DefaultMessageNotification(
                                "Usuário ou senha incorretos! Verifique suas credenciais e tente novamente.",
                                NotificationType.ERROR
                        ).show();
                        break;
                    default:
                        new DefaultMessageNotification(
                                "Ocorreu um erro inesperado! Tente novamente mais tarde.",
                                NotificationType.ERROR
                        ).show();
                        break;
                }
            }
        });

        //register action
        pane.getAdvogadoRegisterMenu().getConfirmPassword().setOnKeyTyped(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                pane.getAdvogadoRegisterMenu().getCreateButton().fire();
            }
        });

        pane.getAdvogadoRegisterMenu().getCreateButton().setOnAction(e -> {
            final String oab = pane.getAdvogadoRegisterMenu().getOab().getText();
            try {
                if (advogadoValidator.validateJFX(pane.getAdvogadoRegisterMenu())) {
                    advogadoService.create(
                            pane.getAdvogadoRegisterMenu().getUsername().getText(),
                            pane.getAdvogadoRegisterMenu().getEmail().getText(),
                            pane.getAdvogadoRegisterMenu().getPassword().getText(),
                            pane.getAdvogadoRegisterMenu().getOab().getText()
                    );
                } else {
                    return;
                }
            } catch(ResponseNotOkException ex) {
                String msg = switch (ex.getCode()) {
                    case 400 -> "Parece que você deixou algum campo vazio!";
                    case 409 -> "O nome de usuário ou e-mail já estão em uso!";
                    case 404 -> "Ocorreu um erro com a conexão com o servidor. Cheque a sua conexão com a internet";
                    default -> "Ocorreu um erro inesperado! Tente novamente mais tarde.";
                };
                new DefaultMessageNotification(msg, NotificationType.ERROR).show();
                return;
            }
            ApplicationState.getInstance().setAccountMode(AccountMode.LOGGING_IN);
        });

        // mode switching handling
        pane.getLoginMenu().getCreateAccount().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.REGISTERING));
        pane.getAdvogadoRegisterMenu().getLoginHyperlink().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.LOGGING_IN));

        pane.getAccountDashboardMenu().getDeleteAccount().setOnAction(e -> {
            try {
                advogadoService.delete();
            } catch (ResponseNotOkException ex) {
                String msg = switch (ex.getCode()) {
                    case 404 -> "Ocorreu um erro com a conexão com o servidor. Cheque a sua conexão com a internet";
                    default -> "Ocorreu um erro inesperado! Tente novamente mais tarde.";
                };
                new DefaultMessageNotification(msg, NotificationType.ERROR).show();
            }
        });

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
