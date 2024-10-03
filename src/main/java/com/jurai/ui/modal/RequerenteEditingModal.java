package com.jurai.ui.modal;

import com.jurai.data.model.Requerente;
import com.jurai.ui.LoadingStrategy;
import com.jurai.ui.util.SpacerFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;

@LoadingStrategy(LoadingStrategy.Strategy.LAZY)
public final class RequerenteEditingModal extends RequerenteModal {
    private final Requerente object;
    private Button delete;

    public RequerenteEditingModal(Requerente object) {
        super("requerenteEditingModal");
        this.object = object;
        fillRequerenteInfo();
    }

    private void fillRequerenteInfo() {
        cpfCnpj.setText(object.getCpfCnpj());
        nome.setText(object.getNome());
        nomeSocial.setText(object.getNomeSocial());
        genero.setValue(switch (object.getGenero()) {
           case "M" -> "Masculino";
           case "F" -> "Feminino";
           default -> "Outro";
        });
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
        delete = new Button("Deletar");
        delete.getStyleClass().add("red-button");
    }

    @Override
    protected void layControls() {
        super.layControls();
        personalInfoActions.getChildren().setAll(
                personalCancel,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                delete,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                personalInfoNext
        );
    }

    public Button getDelete() {
        return delete;
    }

    public Requerente getObject() {
        return object;
    }
}
