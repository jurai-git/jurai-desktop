package com.jurai.ui.error;

import com.jurai.data.request.InternalErrorCodes;
import com.jurai.data.request.ResponseNotOkException;

public class LoginErrorTranslator {
    public static String translate(ResponseNotOkException e) {
        return switch (e.getCode()) {
            case 500 -> "Ocorreu um erro interno ao fazer login. Tente nomvamente mais tarde";
            case 400 -> "Parece que você deixou algum campo vazio!";
            case 401 -> "Usuário ou senha incorretos! Verifique suas credenciais e tente novamente";
            case InternalErrorCodes.NETWORK_ERROR -> Defaults.DEFAULT_600;
            default -> Defaults.unknown(e.getCode());
        };
    }
}
