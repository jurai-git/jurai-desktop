package com.jurai.ui.modal;

import com.jurai.data.model.Requerente;
import javafx.scene.control.Button;

@LoadingStrategy(LoadingStrategy.Strategy.LAZY)
public class RequerenteEditingModal extends RequerenteModal {
    private final Requerente object;

    public RequerenteEditingModal(Requerente object) {
        super("requerenteEditingModal");
        this.object = object;
        fillRequerenteInfo();
    }

    private void fillRequerenteInfo() {
        cpfCnpj.setText(object.getCpfCnpj());
        nome.setText(object.getNome());
        nomeSocial.setText(object.getNomeSocial());
        genero.setText(object.getGenero());
        isIdoso.setSelected(object.isIdoso());
        rg.setText(object.getRg());
        orgaoEmissor.setText(object.getOrgaoEmissor());
        estadoCivil.setText(object.getEstadoCivil());
        nacionalidade.setText(object.getNacionalidade());
        profissao.setText(object.getProfissao());
        cep.setText(object.getCep());
        logradouro.setText(object.getLogradouro());
        estado.setText(object.getEstado());
        email.setText(object.getEmail());
        numero.setText(object.getNumImovel());
        complemento.setText(object.getComplemento());
        bairro.setText(object.getBairro());
        cidade.setText(object.getCidade());
    }

    @Override
    protected void initButtons() {
        addressCancel = new Button("Sair");
        generalCancel = new Button("Sair");
        personalCancel = new Button("Sair");
        create = new Button("Salvar");
        addressInfoPrevious = new Button("Anterior");
        generalInfoPrevious = new Button("Anterior");
        generalInfoNext = new Button("Próximo");
        personalInfoNext = new Button("Próximo");
    }

    public Requerente getObject() {
        return object;
    }
}
