package com.jurai.ui.modal;

import com.jurai.ui.controls.BasicTab;
import com.jurai.ui.controls.BasicTabbedPane;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.TextFieldSet;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

@LoadingStrategy(LoadingStrategy.Strategy.EAGER)
public class RequerenteRegisterModal extends Modal<BasicTabbedPane> {
    private BasicTabbedPane content;
    BasicTab personalInfo, generalInfo, addressInfo;
    private VBox personalInfoContent, generalInfoContent, addressInfoContent;
    private VBox personalInfoForm, generalInfoForm, addressInfoForm;
    private HBox personalInfoActions, generalInfoActions, addressInfoActions;
    private Button personalInfoNext, personalCancel, generalCancel, addressCancel, generalInfoNext, generalInfoPrevious, addressInfoPrevious, create;

    private TextFieldSet
            cpfCnpj, nome, nomeSocial, genero, rg, orgaoEmissor, estadoCivil,
            nacionalidade, profissao, cep, logradouro, email, numero,
            complemento, bairro, estado, cidade;
    private RadioButton isIdoso;

    public RequerenteRegisterModal() {
        super("requerenteRegisterModal");
        System.out.println("Creating requerenteRegisterModal");
    }

    @Override
    protected void initControls() {
        content = new BasicTabbedPane();
        cpfCnpj = new TextFieldSet("CPF/CNPJ*");
        nome = new TextFieldSet("Nome*");
        nomeSocial = new TextFieldSet("Nome Social");
        genero = new TextFieldSet("Genero*");
        rg = new TextFieldSet("RG");
        orgaoEmissor = new TextFieldSet("Orgão Emissor*");
        estadoCivil = new TextFieldSet("Estado Civil*");
        nacionalidade = new TextFieldSet("Nacionalidade*");
        profissao = new TextFieldSet("Profissão*");
        cep = new TextFieldSet("CEP*");
        logradouro = new TextFieldSet("Logradouro*");
        email = new TextFieldSet("E-mail*");
        numero = new TextFieldSet("Número*");
        complemento = new TextFieldSet("Complemento");
        bairro = new TextFieldSet("Bairro*");
        estado = new TextFieldSet("Estado*");
        cidade = new TextFieldSet("Cidade*");
        isIdoso = new RadioButton("É idoso*");

        addressCancel = new Button("Cancelar");
        generalCancel = new Button("Cancelar");
        personalCancel = new Button("Cancelar");
        create = new Button("Criar");
        addressInfoPrevious = new Button("Anterior");
        generalInfoPrevious = new Button("Anterior");
        generalInfoNext = new Button("Próximo");
        personalInfoNext = new Button("Próximo");

        // personalInfo
        personalInfoContent = new VBox();
        personalInfoForm = new VBox();
        personalInfoActions = new HBox();
        personalInfo = new BasicTab("Infos. pessoais", personalInfoContent);

        // generalInfo
        generalInfoContent = new VBox();
        generalInfoForm = new VBox();
        generalInfoActions = new HBox();
        generalInfo = new BasicTab("Infos. Gerais", generalInfoContent);

        // addressInfo
        addressInfoContent = new VBox();
        addressInfoActions = new HBox();
        addressInfoForm = new VBox();
        addressInfo = new BasicTab("Endereço", addressInfoContent);

        content.addTab(personalInfo);
        content.addTab(generalInfo);
        content.addTab(addressInfo);
        content.setActiveTab(personalInfo);
        content.getStyleClass().addAll("form", "no-decoration");
    }

    @Override
    protected void layControls() {

        // first page
        VBox.setVgrow(nome, Priority.ALWAYS);
        nome.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(nomeSocial, Priority.ALWAYS);
        nomeSocial.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(email, Priority.ALWAYS);
        email.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(isIdoso, Priority.ALWAYS);
        isIdoso.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        isIdoso.setAlignment(Pos.CENTER_LEFT);
        VBox.setVgrow(genero, Priority.ALWAYS);
        genero.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        personalInfoForm.getChildren().addAll(
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                nome,
                nomeSocial,
                email,
                isIdoso,
                genero,
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS)
        );

        personalInfoNext.getStyleClass().add("blue-button");
        personalCancel.getStyleClass().add("red-button");
        personalInfoActions.getChildren().addAll(
                personalCancel, SpacerFactory.createHBoxSpacer(Priority.ALWAYS), personalInfoNext
        );

        VBox.setVgrow(personalInfoForm, Priority.ALWAYS);
        VBox.setVgrow(personalInfoActions, Priority.NEVER);
        personalInfoContent.getChildren().addAll(personalInfoForm, personalInfoActions);

        //second page
        VBox.setVgrow(rg, Priority.ALWAYS);
        rg.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(cpfCnpj, Priority.ALWAYS);
        cpfCnpj.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(orgaoEmissor, Priority.ALWAYS);
        orgaoEmissor.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(profissao, Priority.ALWAYS);
        profissao.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(estadoCivil, Priority.ALWAYS);
        estado.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(nacionalidade, Priority.ALWAYS);
        nacionalidade.maxWidthProperty().bind(content.widthProperty().multiply(0.8));

        generalInfoForm.getChildren().addAll(
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                new HGroup().withMargin().withVgrow(Priority.ALWAYS).withChildren(cpfCnpj, rg),
                orgaoEmissor,
                profissao,
                new HGroup().withMargin().withVgrow(Priority.ALWAYS).withChildren(estadoCivil, nacionalidade),
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS)
        );


        generalInfoNext.getStyleClass().add("blue-button");
        generalCancel.getStyleClass().add("red-button");
        generalInfoActions.getChildren().addAll(
                generalCancel,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                generalInfoPrevious,
                SpacerFactory.createHBoxSpacer(12),
                generalInfoNext
        );

        VBox.setVgrow(generalInfoActions, Priority.NEVER);
        VBox.setVgrow(generalInfoForm, Priority.ALWAYS);
        generalInfoContent.getChildren().addAll(generalInfoForm, generalInfoActions);

        // third page
        addressInfoForm.setAlignment(Pos.CENTER_LEFT);
        VBox.setVgrow(cep, Priority.ALWAYS);
        cep.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(logradouro, Priority.ALWAYS);
        logradouro.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(numero, Priority.ALWAYS);
        numero.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(complemento, Priority.ALWAYS);
        complemento.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(bairro, Priority.ALWAYS);
        bairro.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(cidade, Priority.ALWAYS);
        cidade.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(estado, Priority.ALWAYS);
        estado.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        addressInfoForm.getChildren().addAll(
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                cep,
                estado,
                cidade,
                bairro,
                new HGroup().withVgrow(Priority.ALWAYS).withMargin().withChildren(logradouro, numero),
                complemento,
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS)
        );

        create.getStyleClass().add("blue-button");
        addressCancel.getStyleClass().add("red-button");
        addressInfoActions.getChildren().addAll(
                addressCancel,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                addressInfoPrevious,
                SpacerFactory.createHBoxSpacer(12),
                create
        );

        VBox.setVgrow(addressInfoActions, Priority.NEVER);
        VBox.setVgrow(addressInfoForm, Priority.ALWAYS);
        addressInfoContent.getChildren().addAll(
                addressInfoForm,
                addressInfoActions
        );
    }

    public Button getPersonalInfoNext() {
        return personalInfoNext;
    }

    public Button getGeneralInfoNext() {
        return generalInfoNext;
    }

    public Button getGeneralInfoPrevious() {
        return generalInfoPrevious;
    }

    public Button getAddressInfoPrevious() {
        return addressInfoPrevious;
    }

    public Button getCreate() {
        return create;
    }

    public List<Button> getCancel() {
        return List.of(addressCancel, personalCancel, generalCancel);
    }

    public void setActiveTab(BasicTab tab) {
        content.setActiveTab(tab);
    }

    public BasicTab getPersonalInfo() {
        return personalInfo;
    }

    public BasicTab getAddressInfo() {
        return addressInfo;
    }

    public BasicTab getGeneralInfo() {
        return generalInfo;
    }

    public String getCpfCnpj() {
        return cpfCnpj.getText();
    }

    public String getNome() {
        return nome.getText();
    }

    public String getNomeSocial() {
        return nomeSocial.getText();
    }

    public String getGenero() {
        return genero.getText();
    }

    public String getRg() {
        return rg.getText();
    }

    public String getOrgaoEmissor() {
        return orgaoEmissor.getText();
    }

    public String getEstadoCivil() {
        return estadoCivil.getText();
    }

    public String getNacionalidade() {
        return nacionalidade.getText();
    }

    public String getProfissao() {
        return profissao.getText();
    }

    public String getCep() {
        return cep.getText();
    }

    public String getLogradouro() {
        return logradouro.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getNumero() {
        return numero.getText();
    }

    public String getComplemento() {
        return complemento.getText();
    }

    public String getBairro() {
        return bairro.getText();
    }

    public String getEstado() {
        return estado.getText();
    }

    public String getCidade() {
        return cidade.getText();
    }

    public boolean isIdoso() {
        return isIdoso.isArmed();
    }

    @Override
    public BasicTabbedPane getContent() {
        return content;
    }
}
