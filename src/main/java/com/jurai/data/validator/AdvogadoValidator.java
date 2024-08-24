package com.jurai.data.validator;

import com.jurai.data.model.Advogado;

public class AdvogadoValidator extends AbstractValidator<Advogado> {
    private final String invalidPasswordMessage = "A senha deve ter ao menos 8 caracteres!";
    private final String invalidEmailMessage = "O e-mail fornecido é inválido!";

    public AdvogadoValidator() {
        super();
        ruleFor(a -> a.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"), invalidEmailMessage);
    }

    public String getInvalidPasswordMessage() {
        return invalidPasswordMessage;
    }

    public String getInvalidEmailMessage() {
        return invalidEmailMessage;
    }
}
