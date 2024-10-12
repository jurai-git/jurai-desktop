package com.jurai.ui.modal;

import com.jurai.data.ApplicationData;
import com.jurai.ui.LoadingStrategy;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.*;
import com.jurai.ui.util.ControlWrapper;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

@LoadingStrategy(LoadingStrategy.Strategy.EAGER)
public abstract class RequerenteModal extends Modal<BasicTabbedPane> {
    private BasicTabbedPane content;
    BasicTab personalInfo, generalInfo, addressInfo;
    protected VBox personalInfoContent, generalInfoContent, addressInfoContent;
    protected VBox personalInfoForm, generalInfoForm, addressInfoForm;
    protected HBox personalInfoActions, generalInfoActions, addressInfoActions;
    protected Button personalInfoNext, personalCancel, generalCancel, addressCancel, generalInfoNext, generalInfoPrevious, addressInfoPrevious, create;


    protected MaskedTextFieldSet cpfCnpj, rg, cep;
    protected TextFieldSet
            nome, nomeSocial, orgaoEmissor, estadoCivil,
            nacionalidade, profissao, logradouro, email, numero,
            complemento, bairro, estado, cidade;
    protected ComboBox<String> genero;
    protected CheckBox isIdoso;

    public RequerenteModal(String name) {
        super(name);
        System.out.println("Creating generic requerenteModal");
    }

    @Override
    protected void initControls() {
        initButtons();
        content = new BasicTabbedPane();
        cpfCnpj = new MaskedTextFieldSet("CPF/CNPJ*");

        nome = new TextFieldSet("Nome*");
        nomeSocial = new TextFieldSet("Nome Social");
        genero = new ComboBox<>();
        genero.getItems().addAll("Masculino", "Feminino", "Outro");
        genero.setValue("Masculino");
        rg = new MaskedTextFieldSet("RG");
        rg.setMask("##.###.###-#");
        orgaoEmissor = new TextFieldSet("Orgão Emissor*");
        estadoCivil = new TextFieldSet("Estado Civil*");
        nacionalidade = new TextFieldSet("Nacionalidade*");
        profissao = new TextFieldSet("Profissão*");
        cep = new MaskedTextFieldSet("CEP*");
        cep.setMask("#####-###");
        logradouro = new TextFieldSet("Logradouro*");
        email = new TextFieldSet("E-mail*");
        numero = new TextFieldSet("Número*");
        complemento = new TextFieldSet("Complemento");
        bairro = new TextFieldSet("Bairro*");
        estado = new TextFieldSet("Estado*");
        cidade = new TextFieldSet("Cidade*");
        isIdoso = new CheckBox("É idoso*");

        // personalInfo
        personalInfoContent = new VBox();
        personalInfoForm = new VBox();
        personalInfoForm.getStyleClass().add("fields");
        personalInfoActions = new HBox();
        personalInfo = new BasicTab("Infos. pessoais", personalInfoContent);

        // generalInfo
        generalInfoContent = new VBox();
        generalInfoForm = new VBox();
        generalInfoForm.getStyleClass().add("fields");
        generalInfoActions = new HBox();
        generalInfo = new BasicTab("Infos. Gerais", generalInfoContent);

        // addressInfo
        addressInfoContent = new VBox();
        addressInfoActions = new HBox();
        addressInfoForm = new VBox();
        addressInfoForm.getStyleClass().add("fields");
        addressInfo = new BasicTab("Endereço", addressInfoContent);

        content.addTab(personalInfo);
        content.addTab(generalInfo);
        content.addTab(addressInfo);
        content.setActiveTab(personalInfo);
        content.getStyleClass().addAll("form", "no-decoration");
    }

    protected abstract void initButtons();

    @Override
    protected void layControls() {

        // first page
        VBox.setVgrow(nome, Priority.ALWAYS);
        nome.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(nomeSocial, Priority.ALWAYS);
        nomeSocial.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(email, Priority.ALWAYS);
        email.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        isIdoso.setAlignment(Pos.CENTER_LEFT);
        VBox.setVgrow(genero, Priority.ALWAYS);
        genero.maxWidthProperty().bind(ApplicationData.defaultIconSizeProperty().multiply(10));
        personalInfoForm.getChildren().addAll(
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                nome,
                nomeSocial,
                email,
                cpfCnpj,
                new HGroup().withVgrow(Priority.ALWAYS).withMargin().withChildren(
                        new ControlWrapper(isIdoso).withVgrow(Priority.ALWAYS).withHgrow(Priority.ALWAYS),
                        genero
                ),
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS)
        );

        personalInfoNext.getStyleClass().add("blue-button");
        personalCancel.getStyleClass().add("red-button");
        personalInfoActions.getChildren().addAll(
                personalCancel, SpacerFactory.createHBoxSpacer(Priority.ALWAYS), personalInfoNext
        );
        HoverAnimator.animateAll(personalCancel, 1, 1);
        HoverAnimator.animateAll(personalInfoNext, 1, 1);

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
        estadoCivil.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(nacionalidade, Priority.ALWAYS);
        nacionalidade.maxWidthProperty().bind(content.widthProperty().multiply(0.8));

        generalInfoForm.getChildren().addAll(
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                rg,
                orgaoEmissor,
                profissao,
                estadoCivil,
                nacionalidade,
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
        HoverAnimator.animateAll(generalCancel, 1, 1);
        HoverAnimator.animateAll(generalInfoPrevious, 1, 1);
        HoverAnimator.animateAll(generalInfoNext, 1, 1);

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
        HoverAnimator.animateAll(create, 1, 1);
        HoverAnimator.animateAll(addressCancel, 1, 1);
        HoverAnimator.animateAll(addressInfoPrevious, 1, 1);

        VBox.setVgrow(addressInfoActions, Priority.NEVER);
        VBox.setVgrow(addressInfoForm, Priority.ALWAYS);
        addressInfoContent.getChildren().addAll(
                addressInfoForm,
                addressInfoActions
        );
    }

    public void clear() {
        cpfCnpj.getInput().clear();
        nome.getInput().clear();
        nomeSocial.getInput().clear();
        genero.setValue("Masculino");
        rg.getInput().clear();
        orgaoEmissor.getInput().clear();
        estadoCivil.getInput().clear();
        nacionalidade.getInput().clear();
        profissao.getInput().clear();
        cep.getInput().clear();
        logradouro.getInput().clear();
        email.getInput().clear();
        numero.getInput().clear();
        complemento.getInput().clear();
        bairro.getInput().clear();
        estado.getInput().clear();
        cidade.getInput().clear();
        isIdoso.setSelected(false);
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
        return cpfCnpj.getText().replaceAll("[-./]", "");
    }

    public String getNome() {
        return nome.getText();
    }

    public String getNomeSocial() {
        return nomeSocial.getText();
    }

    public String getGenero() {
        String genero = this.genero.getValue();
        return switch(genero) {
            case "Masculino" -> "M";
            case "Feminino" -> "F";
            default -> "O";
        };
    }

    public String getRg() {
        return rg.getText().replaceAll("[-.]", "");
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
        return isIdoso.isSelected();
    }

    @Override
    public BasicTabbedPane getContent() {
        return content;
    }
}
