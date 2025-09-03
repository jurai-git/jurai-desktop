package com.jurai.data.validator;

import com.jurai.data.model.Advogado;
import com.jurai.ui.menus.AdvogadoRegisterMenu;

public class AdvogadoValidator extends AbstractValidator<AdvogadoRegisterMenu> {
    private final String invalidPasswordMessage = "A senha deve ter ao menos 8 caracteres!";
    private final String passwordMatchMessage = "As senhas não coincidem!";
    private final String invalidEmailMessage = "O e-mail fornecido é inválido!";
    private final String invalidUsernameMessage = "O nome de usuário deve ter ao menos 3 caracteres!";

    public AdvogadoValidator() {
        super();
        ruleFor(a -> a.getEmail().getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"), invalidEmailMessage);
        ruleFor(a -> {
            System.out.println(a.getPassword().getText().length());
            return a.getPassword().getText().length() >= 8;
        }, invalidPasswordMessage);
        ruleFor(a -> a.getUsername().getText().length() > 3, invalidUsernameMessage);
        ruleFor(a ->{
            System.out.println(a.getPassword().getText());
            System.out.println(a.getConfirmPassword().getText());
            return a.getPassword().getText().equals(a.getConfirmPassword().getText());
        }, passwordMatchMessage);
    }
}
