package com.jurai.data.validator;

import com.jurai.data.model.Requerente;

public class RequerenteValidator extends AbstractValidator<Requerente> {

    public RequerenteValidator() {
        ruleFor(r -> r.getNome().strip().length() >= 3, "O campo Nome deve ter ao menos 3 caracteres!");
        ruleFor(r -> r.getEmail().strip().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"), "O e-mail fornecido é inválido!");
        ruleFor(r -> r.getCpfCnpj().strip().matches("^\\d{11}|\\d{14}$"), "O campo CPF deve ter 11 digitos!");
        ruleFor(r -> {
            if (r.getCpfCnpj().length() != 11 || r.getCpfCnpj().chars().distinct().count() == 1)
                return false;

            try {
                int d1 = calculateDigit(r.getCpfCnpj(), 10);
                int d2 = calculateDigit(r.getCpfCnpj(), 11);
                System.out.println("calculated digits: " + d1 + d2);
                return r.getCpfCnpj().endsWith("" + d1 + d2);
            } catch (NumberFormatException e) {
                return false;
            }
        }, "O CPF inserido não é válido!");
        ruleFor(r -> r.getRg().strip().matches("^\\d{9}$"), "O campo RG deve ter 9 dígitos");
        ruleFor(r -> !r.getOrgaoEmissor().isBlank(), "O Orgão emissor é de preenchimento obrigatório");
        ruleFor(r -> !r.getProfissao().isBlank(), "O campo Profissão é de preenchimento obrigatório!");
        ruleFor(r -> !r.getEstadoCivil().isBlank(), "O campo Estado Civil é de preenchimento obrigatório!");
        ruleFor(r -> !r.getNacionalidade().isBlank(), "O campo Nacionalidade é de preenchimento obrigatório!");
        ruleFor(r -> !r.getCep().isBlank(), "O campo CEP é de preenchimento obrigatório!");
        ruleFor(r -> !r.getEstado().isBlank(), "O Estado é de preenchimento obrigatório!");
        ruleFor(r -> !r.getCidade().isBlank(), "O campo Cidade é de preenchimento obrigatório!");
        ruleFor(r -> !r.getCidade().isBlank(), "O campo Cidade é de preenchimento obrigatório!");
        ruleFor(r -> !r.getBairro().isBlank(), "O campo Bairro é de preenchimento obrigatório!");
        ruleFor(r -> !r.getLogradouro().isBlank(), "O campo Logradouro é de preenchimento obrigatório!");
        ruleFor(r -> !r.getNumImovel().isBlank(), "O campo Número é de preenchimento obrigatório!");

    }

    private static int calculateDigit(String cpf, int weightStart) {
        int sum = 0;
        for (int i = 0; i < weightStart - 1; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (weightStart - i);
        }
        int rest = (sum * 10) % 11;
        return (rest == 10) ? 0 : rest;
    }

}
