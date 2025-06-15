package com.jurai.ui.error;

import com.jurai.data.request.ResponseNotOkException;
import lombok.Builder;

public class DocChatErrorTranslator {
    public static String translateMessageError(ResponseNotOkException e) {
        return switch(e.getCode()) {
            case 400 -> Defaults.DEFAULT_400;
            case 401 -> Defaults.DEFAULT_401;
            case 402 -> Defaults.DEFAULT_402;
            case 403 -> Defaults.DEFAULT_403;
            case 500 -> Defaults.DEFAULT_500;
            case 600 -> Defaults.DEFAULT_600;
            default -> Defaults.unknown(e.getCode());
        };
    }

}
