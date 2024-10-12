package com.jurai.data.validator;

import com.jurai.ui.modal.RequerenteModal;

public class RequerenteValidator extends AbstractValidator<RequerenteModal> {

    public RequerenteValidator() {
        ruleFor(r -> !r.getNome().isEmpty(), "O campo Nome é de preenchimento obrigatório!");
        ruleFor(r -> r.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"), "O e-mail fornecido é inválido!");
        ruleFor(r -> r.getCpfCnpj().matches("^\\d{11}|\\d{14}$"), "O campo CPF/CNPJ deve ter ou 11 ou 14 digitos!");
        ruleFor(r -> r.getRg().matches("^\\d{9}$"), "O campo RG deve ter 9 dígitos");
        ruleFor(r -> !r.getOrgaoEmissor().isEmpty(), "O campo Orgão Emissor é de preenchimento obrigatório!");
        ruleFor(r -> !r.getProfissao().isEmpty(), "O campo Profissão é de preenchimento obrigatório!");
    }

}
