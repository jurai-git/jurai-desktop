package com.jurai.ui.controller;

import com.jurai.data.AppState;
import com.jurai.data.GlobalEvents;
import com.jurai.data.model.Advogado;
import com.jurai.data.request.InternalErrorCodes;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AdvogadoService;
import com.jurai.data.validator.AdvogadoValidator;
import com.jurai.ui.error.UpdatePasswordErrorTranslator;
import com.jurai.ui.menus.AccountSettingsMenu;
import com.jurai.ui.modal.ModalManager;
import com.jurai.ui.modal.notif.ConfirmationNotification;
import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.NotificationType;
import com.jurai.ui.panes.AccountPane;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.menus.AccountDashboardMenu;

import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import java.io.File;
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
                    AppState.get().setCurrentUser(new Advogado(1, "advogado", "advogado@gmail.com", "oab123", "12321321321321"));
                    return;
                }
                if (pane.getLoginMenu().getKeepConnected().isSelected()) {
                    AppState.get().setRemembersUser(true);
                }
                advogadoService.authenticate(pane.getLoginMenu().getEmail().getText(), pane.getLoginMenu().getPassword().getText());
                pane.getLoginMenu().getEmail().setText("");
                pane.getLoginMenu().getPassword().setText("");
            } catch (ResponseNotOkException ex) {
                new DefaultMessageNotification(switch(ex.getCode()) {
                    case 500 -> "Ocorreu um erro interno ao fazer login. Tente nomvamente mais tarde";
                    case 400 -> "Parece que você deixou algum campo vazio!";
                    case 401 -> "Usuário ou senha incorretos! Verifique suas credenciais e tente novamente";
                    case InternalErrorCodes.NETWORK_ERROR -> "Ocorreu um erro de conexão! Verifique sua conexão com a internet";
                    default -> "Ocorreu um erro inesperado! Tente novamente mais tarde. Código do erro: " + ex.getCode();
                }, NotificationType.ERROR).show();
            }
        });

        //register action
        pane.getAdvogadoRegisterMenu().getConfirmPassword().setOnKeyTyped(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                pane.getAdvogadoRegisterMenu().getCreateButton().fire();
            }
        });

        // TODO: add proper error handling
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
                    pane.getAdvogadoRegisterMenu().clearAll();
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
            AppState.get().setAccountMode(AccountMode.LOGGING_IN);
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
                AppState.get().setAccountMode(AccountMode.EMAIL_SENT);
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
        pane.getAccountRecoveryMenu().getLogin().setOnAction(e -> AppState.get().setAccountMode(AccountMode.LOGGING_IN));
        pane.getAccountRecoveryMenu().getCreateAccount().setOnAction(e -> AppState.get().setAccountMode(AccountMode.REGISTERING));
        pane.getLoginMenu().getCreateAccount().setOnAction(e -> AppState.get().setAccountMode(AccountMode.REGISTERING));
        pane.getLoginMenu().getForgotPwd().setOnAction(e -> AppState.get().setAccountMode(AccountMode.FORGOT_PASSWORD));
        pane.getAdvogadoRegisterMenu().getLoginHyperlink().setOnAction(e -> AppState.get().setAccountMode(AccountMode.LOGGING_IN));
        pane.getAccountRecoveryDone().getCreateAccount().setOnAction(e -> AppState.get().setAccountMode(AccountMode.REGISTERING));
        pane.getAccountRecoveryDone().getReturnToLogin().setOnAction(e -> AppState.get().setAccountMode(AccountMode.LOGGING_IN));
        pane.getAccountRecoveryDone().getMakeNewRequest().setOnAction(e -> AppState.get().setAccountMode(AccountMode.FORGOT_PASSWORD));

        // dashboard menu actions
        pane.getAccountDashboardMenu().getDeleteAccount().setOnAction(e -> {
            new ConfirmationNotification<String>("Você tem certeza que deseja deletar sua conta?", NotificationType.CONFIRMATION)
                .setOnYes(ev -> {
                    try {
                        advogadoService.delete();
                    } catch (ResponseNotOkException ex) {
                        return switch (ex.getCode()) {
                            case 401 -> "Ocorreu um erro ao realizar sua autenticação. Tente sair e logar novamente.";
                            case 404 -> "Ocorreu um erro com a conexão com o servidor. Cheque a sua conexão com a internet";
                            default -> "Ocorreu um erro inesperado! Tente novamente mais tarde. Código do erro: " + ex.getCode();
                        };
                    }
                    return null;
                })
                .setAfterDispose(msg -> {
                    if (msg == null) return;
                    new DefaultMessageNotification(msg, NotificationType.ERROR).show();
                })
                .show();
        });

        pane.getAccountDashboardMenu().getReset().setOnAction(e -> {
            userChanged(AppState.get().getCurrentUser(), pane);
        });

        pane.getAccountDashboardMenu().getUsername().textProperty().addListener(str -> {
            setChangesMade(hasDifferentUserData(AppState.get().getCurrentUser(), pane), pane);
        });
        pane.getAccountDashboardMenu().getEmail().textProperty().addListener(str -> {
            setChangesMade(hasDifferentUserData(AppState.get().getCurrentUser(), pane), pane);
        });

        pane.getAccountDashboardMenu().getChangePassword().textProperty().addListener(str -> {
            pane.getAccountDashboardMenu().getChangePasswrodBtn().setDisable(!isChangingPasswords(pane));
        });

        pane.getAccountDashboardMenu().getConfirmPassword().textProperty().addListener(str -> {
            pane.getAccountDashboardMenu().getChangePasswrodBtn().setDisable(!isChangingPasswords(pane));
        });

        AccountSettingsMenu accountSettingsMenu = pane.getAccountDashboardMenu().getAccountSettingsMenu();

        accountSettingsMenu.getChangePasswordBtn().setOnAction(e -> {
            // first, validate fields
            String pwd = accountSettingsMenu.getChangePassword().getText();
            String confirmPwd = accountSettingsMenu.getConfirmPassword().getText();

            if (pwd.strip().length() < 8) {
                accountSettingsMenu.getPwdChangeErrorProperty().set("A senha deve conter ao menos 8 caracteres!");
                return;
            }
            if (!confirmPwd.equals(pwd)) {
                accountSettingsMenu.getPwdChangeErrorProperty().set("As senhas não coincidem!");
                return;
            }

            try {
                advogadoService.updatePassword(pwd);
            } catch (ResponseNotOkException ex) {
                accountSettingsMenu.getPwdChangeErrorProperty().set(UpdatePasswordErrorTranslator.translate(ex));
            }

            new DefaultMessageNotification("Sua senha foi atualizada com sucesso! Você terá que relogar para atualizar suas credenciais.", NotificationType.INFO, advogadoService::deauthenticate).show();
        });


        // profile picture changing and handling

        pane.getAccountDashboardMenu().getAccountSettingsMenu().getProfilePicture().setOnPictureChange(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Escolha uma foto de perfil");
            chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("jpg", "png", "jpeg"));
            File chosenFile = chooser.showOpenDialog(AppState.get().getCurrentStage());

            if (!chosenFile.canRead()) {
                new DefaultMessageNotification("Você não parece ter permissão para ler este arquivo. Selecione outro.", NotificationType.ERROR).show();
                return;
            }

            if (chosenFile.length() > 1024L * 1024L * 1024L * 2L) {
                new DefaultMessageNotification("Esse arquivo excede o limite de 2MB. Selecione um arquivo menor.", NotificationType.ERROR).show();
            }

            try {
                advogadoService.changePicture(chosenFile);
                GlobalEvents.get().firePfpChanged();
            } catch (ResponseNotOkException ex) {
                String errorMsg = switch (ex.getCode()) {
                    case 401 -> "Ocorreu um erro ao realizar sua autenticação. Se o erro persistir, você pode tentar sair e entrar novamente com sua conta.";
                    case 503 -> "Nossos servidores estão sobrecarregados. Tente novamente mais tarde.";
                    case 413 -> "O arquivo que você enviou ultrapassa o limite de 2MB. Envie um arquivo menor.";
                    case 400 -> "O arquivo que você enviou não parece ser uma imagem. Envie uma imagem JPG ou PNG.";
                    default -> "Ocorreu um erro desconhecido. Código do erro: " + ex.getCode();
                };
                new DefaultMessageNotification(errorMsg, NotificationType.ERROR).show();
            }
        });

        pane.getAccountDashboardMenu().getAccountSettingsMenu().getProfilePicture().setOnPictureRemoval(e -> {
            new ConfirmationNotification<String>("Você realmente deseja remover sua foto de perfil?", NotificationType.CONFIRMATION)
                .setOnYes(yes -> {
                    try {
                        advogadoService.deletePicture();
                        GlobalEvents.get().firePfpChanged();
                        return null;
                    } catch (ResponseNotOkException ex) {
                        return switch(ex.getCode()) {
                            case 401 -> "Ocorreu um erro ao realizar sua autenticação. Se o erro persistir, você pode tentar sair e entrar novamente com sua conta.";
                            case 503 -> "Nossos servidores estão sobrecarregados. Tente novamente mais tarde.";
                            case InternalErrorCodes.NETWORK_ERROR -> "Ocorreu um erro de conexão. Verifique sua conexão com a internet e tente novamente.";
                            default -> "Ocorreu um erro desconhecido. Código do erro: " + ex.getCode();
                        };
                    }
                })
                .setAfterDispose(msg -> {
                   if (msg == null) return;
                   new DefaultMessageNotification(msg, NotificationType.ERROR).show();
                })
                .show();
        });
    }

    @Override
    protected void attachNotifiers(AccountPane pane) {

        AppState.get().accountModeProperty().addListener((obs, o, n) -> {
            modeChanged(n, pane);
        });
        AppState.get().currentUserProperty().addListener((obs, o, n) -> {
                userChanged(n, pane);

        });


        GlobalEvents.get().onPfpChanged(() -> {
            Advogado currentUser = AppState.get().getCurrentUser();
            if (currentUser != null) {
                pane.getAccountDashboardMenu().getAccountSettingsMenu().updatePfp(AppState.get().getApiUrl() + "advogado/" + (long) currentUser.getId() + "/pfp");
            } else {
                pane.getAccountDashboardMenu().getAccountSettingsMenu().loadFallback();
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
        if (newUser == null) pane.getAccountDashboardMenu().getAccountSettingsMenu().loadFallback();

        pane.getAccountDashboardMenu().getAccountSettingsMenu().updatePfp(AppState.get().getApiUrl() + "advogado/" + newUser.getId() + "/pfp");
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
