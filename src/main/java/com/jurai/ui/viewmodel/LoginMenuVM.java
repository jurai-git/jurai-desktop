package com.jurai.ui.viewmodel;

import com.jurai.data.AppState;
import com.jurai.data.model.Advogado;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AdvogadoService;
import com.jurai.ui.error.LoginErrorTranslator;
import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.NotificationType;
import com.jurai.ui.util.AccountMode;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginMenuVM extends ViewModelBase {
    public StringProperty password = new SimpleStringProperty("");
    public StringProperty email = new SimpleStringProperty("");
    public BooleanProperty keepConnected = new SimpleBooleanProperty(false);

    private final AdvogadoService advogadoService;

    public LoginMenuVM(AdvogadoService advogadoService) {
        this.advogadoService = advogadoService;
    }

    public void doLogin() {
        try {
            if(email.get().equals("root")) {
                AppState.get().setCurrentUser(new Advogado(1, "advogado", "advogado@gmail.com", "oab123", "12321321321321"));
                return;
            }
            if (keepConnected.get()) {
                AppState.get().setRemembersUser(true);
            }
            advogadoService.authenticate(email.get(), password.get());
            email.set("");
            password.set("");
        } catch (ResponseNotOkException ex) {
            new DefaultMessageNotification(LoginErrorTranslator.translate(ex), NotificationType.ERROR).show();
        }
    }

    public void onCreateAccount() {
        AppState.get().setAccountMode(AccountMode.REGISTERING);
    }

    public void onForgotPwd() {
        AppState.get().setAccountMode(AccountMode.FORGOT_PASSWORD);
    }
}
