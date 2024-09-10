package com.jurai.ui.modal;

import com.jurai.ui.controller.RequerenteModalController;
import com.jurai.ui.controls.BasicTab;
import com.jurai.ui.controls.BasicTabbedPane;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.TextFieldSet;
import com.jurai.ui.menus.AbstractMenu;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RequerenteRegisterModal extends Modal<BasicTabbedPane> {
    private BasicTabbedPane content;
    BasicTab personalInfo, generalInfo, addressInfo;
    private BorderPane personalInfoContent, generalInfoContent, addressInfoContent;
    private VBox personalInfoForm, generalInfoForm, addressInfoForm;
    private HBox personalInfoActions, generalInfoActions, addressInfoActions;
    private Button personalInfoNext, cancel, generalInfoNext, generalInfoPrevious, addressInfoPrevious, create;

    private TextFieldSet
            cpfCnpj, nome, nomeSocial, genero, rg, orgaoEmissor, estadoCivil,
            nacionalidade, profissao, cep, logradouro, email, numero,
            complemento, bairro, estado, cidade;
    private RadioButton isIdoso;

    public RequerenteRegisterModal() {
        super("requerenteRegisterModal");
    }

    @Override
    protected void initControls() {
        content = new BasicTabbedPane();
        cpfCnpj = new TextFieldSet("CPF/CNPJ*");
        nome = new TextFieldSet("Nome*");
        nomeSocial = new TextFieldSet("Nome Social");
        genero = new TextFieldSet("Genero");
        rg = new TextFieldSet("RG");
        orgaoEmissor = new TextFieldSet("Orgão Emissor");
        estadoCivil = new TextFieldSet("Estado Civil");
        nacionalidade = new TextFieldSet("Nacionalidade");
        profissao = new TextFieldSet("Profissão");
        cep = new TextFieldSet("CEP");
        logradouro = new TextFieldSet("Logradouro");
        email = new TextFieldSet("E-mail");
        numero = new TextFieldSet("Número");
        complemento = new TextFieldSet("Complemento");
        bairro = new TextFieldSet("Bairro");
        estado = new TextFieldSet("Estado");
        cidade = new TextFieldSet("Cidade");
        isIdoso = new RadioButton("É idoso");

        cancel = new Button("Cancelar");
        create = new Button("Criar");
        addressInfoPrevious = new Button("Anterior");
        generalInfoPrevious = new Button("Anterior");
        generalInfoNext = new Button("Próximo");
        personalInfoNext = new Button("Próximo");

        // personalInfo
        personalInfoContent = new BorderPane();
        personalInfoForm = new VBox();
        personalInfoActions = new HBox();
        personalInfo = new BasicTab("Infos. pessoais", personalInfoContent);

        // generalInfo
        generalInfoContent = new BorderPane();
        generalInfoForm = new VBox();
        generalInfoActions = new HBox();
        generalInfo = new BasicTab("Infos. Gerais", generalInfoContent);

        content.addTab(personalInfo);
        content.addTab(generalInfo);
        content.setActiveTab(personalInfo);
        content.getStyleClass().addAll("form", "no-decoration");
    }

    @Override
    protected void layControls() {

        // first page
        VBox.setVgrow(nome, Priority.ALWAYS);
        VBox.setVgrow(nomeSocial, Priority.ALWAYS);
        VBox.setVgrow(email, Priority.ALWAYS);
        VBox.setVgrow(isIdoso, Priority.ALWAYS);
        isIdoso.setAlignment(Pos.CENTER_LEFT);
        VBox.setVgrow(genero, Priority.ALWAYS);
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
        cancel.getStyleClass().add("red-button");
        personalInfoActions.getChildren().addAll(
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS), cancel, SpacerFactory.createHBoxSpacer(12), personalInfoNext, SpacerFactory.createHBoxSpacer(Priority.ALWAYS)
        );

        personalInfoContent.setCenter(personalInfoForm);
        personalInfoContent.setBottom(personalInfoActions);

        //second page
        VBox.setVgrow(rg, Priority.ALWAYS);
        VBox.setVgrow(cpfCnpj, Priority.ALWAYS);
        VBox.setVgrow(orgaoEmissor, Priority.ALWAYS);
        VBox.setVgrow(profissao, Priority.ALWAYS);
        VBox.setVgrow(estadoCivil, Priority.ALWAYS);
        VBox.setVgrow(nacionalidade, Priority.ALWAYS);

        generalInfoForm.getChildren().addAll(
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                new HGroup().withMargin().withVgrow(Priority.ALWAYS).withChildren(cpfCnpj, rg),
                orgaoEmissor,
                profissao,
                new HGroup().withMargin().withVgrow(Priority.ALWAYS).withChildren(estadoCivil, nacionalidade),
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS)
        );


        generalInfoNext.getStyleClass().add("blue-button");
        generalInfoActions.getChildren().addAll(
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                generalInfoPrevious,
                generalInfoNext
        );

        generalInfoContent.setCenter(generalInfoForm);
        generalInfoContent.setBottom(generalInfoActions);
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

    public Button getCancel() {
        return cancel;
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

    @Override
    public BasicTabbedPane getContent() {
        return content;
    }
}
