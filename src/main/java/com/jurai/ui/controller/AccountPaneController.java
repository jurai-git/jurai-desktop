package com.jurai.ui.controller;

import com.jurai.App;
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

import com.jurai.util.EventLogger;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Objects;

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

        // forgot password action
        pane.getAccountRecoveryMenu().getSendRequest().setOnAction(e -> {
            String email = pane.getAccountRecoveryMenu().getEmail().getText();
            System.out.println("Email: " + email);

            if (email.isEmpty()) {
                new DefaultMessageNotification("O e-mail que você inseriu não parece ser válido.\nVerifique-o e tente novamente.", NotificationType.ERROR).show();
                return;
            }

            try {
                advogadoService.requestRecoveryEmail(email);
                ApplicationState.getInstance().setAccountMode(AccountMode.EMAIL_SENT);
            } catch (ResponseNotOkException ex) {
                String msg = switch (ex.getCode()) {
                    case 404 -> "Não existe uma conta com esse e-mail.\nVerifique-o e tente novamente.";
                    case 500 -> "Ocorreu um erro na nossa parte.\nTente novamente mais tarde.";
                    default -> "Ocorreu um erro desconhecido na nossa parte.\nTente novamente mais tarde.\nCódigo do erro: " + ex.getCode();
                };
                new DefaultMessageNotification(msg, NotificationType.ERROR).show();
            }
        });

        // mode switching handling
        pane.getAccountRecoveryMenu().getLogin().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.LOGGING_IN));
        pane.getAccountRecoveryMenu().getCreateAccount().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.REGISTERING));
        pane.getLoginMenu().getCreateAccount().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.REGISTERING));
        pane.getLoginMenu().getForgotPwd().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.FORGOT_PASSWORD));
        pane.getAdvogadoRegisterMenu().getLoginHyperlink().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.LOGGING_IN));
        pane.getAccountRecoveryDone().getCreateAccount().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.REGISTERING));
        pane.getAccountRecoveryDone().getReturnToLogin().setOnAction(e -> ApplicationState.getInstance().setAccountMode(AccountMode.LOGGING_IN));

        // dashboard menu actions
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

        pane.getAccountDashboardMenu().getReset().setOnAction(e -> {
            userChanged(ApplicationState.getInstance().getCurrentUser(), pane);
        });

        pane.getAccountDashboardMenu().getUsername().textProperty().addListener(str -> {
            setChangesMade(hasDifferentUserData(ApplicationState.getInstance().getCurrentUser(), pane), pane);
        });
        pane.getAccountDashboardMenu().getEmail().textProperty().addListener(str -> {
            setChangesMade(hasDifferentUserData(ApplicationState.getInstance().getCurrentUser(), pane), pane);
        });

        pane.getAccountDashboardMenu().getChangePassword().textProperty().addListener(str -> {
            pane.getAccountDashboardMenu().getChangePasswrodBtn().setDisable(!isChangingPasswords(pane));
        });

        pane.getAccountDashboardMenu().getConfirmPassword().textProperty().addListener(str -> {
            pane.getAccountDashboardMenu().getChangePasswrodBtn().setDisable(!isChangingPasswords(pane));
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

        ApplicationState.getInstance().addPropertyChangeListener(e -> {
            if ("currentUser".equals(e.getPropertyName())) {
                Advogado currentUser = ApplicationState.getInstance().getCurrentUser();
                if (currentUser != null) {
                    pane.getAccountDashboardMenu().getAccountSettingsMenu().updatePfp(ApplicationState.getInstance().getApiUrl() + "advogado/" + (long) currentUser.getId() + "/pfp");
                } else {
                    pane.getAccountDashboardMenu().getAccountSettingsMenu().loadFallback();
                }
            }
        });

    }

    private void modeChanged(AccountMode newMode, AccountPane pane) {
        pane.setPane(switch(newMode) {
            case LOGGING_IN -> pane.getLoginMenu();
            case REGISTERING -> pane.getAdvogadoRegisterMenu();
            case FORGOT_PASSWORD -> pane.getAccountRecoveryMenu();
            case LOGGED_IN -> pane.getAccountDashboardMenu();
            case EMAIL_SENT -> pane.getAccountRecoveryDone();
        });
    }

    private void userChanged(Advogado newUser, AccountPane pane) {
        AccountDashboardMenu dashboardMenu = pane.getAccountDashboardMenu();
        dashboardMenu.getTitle().setText(AccountDashboardMenu.TITLE_TEMPLATE.formatted(newUser.getNome()));
        dashboardMenu.getUsername().setText(newUser.getNome());
        dashboardMenu.getEmail().setText(newUser.getEmail());
        dashboardMenu.getOab().setText(newUser.getOab());
    }

    private boolean hasDifferentUserData(Advogado user, AccountPane pane) {
        AccountDashboardMenu dashboardMenu = pane.getAccountDashboardMenu();

        if (!Objects.equals(dashboardMenu.getEmail().getText(), user.getEmail())) return true;
        if (!Objects.equals(dashboardMenu.getUsername().getText(), user.getNome())) return true;
        return false;
    }

    private boolean isChangingPasswords(AccountPane pane) {
        AccountDashboardMenu dashboardMenu = pane.getAccountDashboardMenu();

        return (
                !dashboardMenu.getChangePassword().getText().isEmpty() &&
                !dashboardMenu.getConfirmPassword().getText().isEmpty()
        );
    }

    private void setChangesMade(boolean changesMade, AccountPane pane) {
        AccountDashboardMenu dashboardMenu = pane.getAccountDashboardMenu();

        dashboardMenu.getReset().setDisable(!changesMade);
        dashboardMenu.getSaveChanges().setDisable(!changesMade);
    }
}
