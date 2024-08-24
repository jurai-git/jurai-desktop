package com.jurai.ui.menus;

import com.jurai.data.model.EstadoCivil;
import com.jurai.data.model.Nacionalidade;
import com.jurai.ui.controls.CepTextField;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class RequerenteRegisterMenu extends AbstractMenu<BorderPane> {
    private BorderPane currentStage;

    private BorderPane stage1, stage2, stage3;
    private HBox header, footer, firstFooter;

    private Label stageLabel, cadastroHeader, stageInfo;
    private TextField nome,
    nomeSocial,
    email,
    cpfCnpj,
    rg,
    profissao,
    logradouro,
    numero,
    bairro,
    cidade,
    estado,
    complemento;
    private CepTextField cep;
    private DatePicker dataNascimento;
    private ToggleGroup genero, tipoPessoa;
    private RadioButton masc, fem, outro, pessoaFisica, pessoaJuridica;
    private CheckBox idoso, naoPossuiCpf, naoPossuiRg;
    private ComboBox<String> nacionalidade, estadoCivil;
    private Button continuar, voltar;

    @Override
    protected void initControls() {
        stage1 = new BorderPane();
        stage2 = new BorderPane();
        stage3 = new BorderPane();
        header = new HBox();
        footer = new HBox();
        firstFooter = new HBox();
        currentStage = stage1;

        stageLabel = new Label("Etapa 1");
        cadastroHeader = new Label("Cadastro");
        stageInfo = new Label("Informações pessoais");

        continuar = new Button("Continuar");
        voltar = new Button("<");

        nome = new TextField();
        nome.setPromptText("Nome (*)");

        nomeSocial = new TextField();
        nomeSocial.setPromptText("Nome Social");
        
        email = new TextField();
        email.setPromptText("E-mail (*)");

        cpfCnpj = new TextField();
        cpfCnpj.setPromptText("CPF/CNPJ");

        rg = new TextField();
        rg.setPromptText("RG");

        profissao = new TextField();
        profissao.setPromptText("Profissão");

        cep = new CepTextField();
        cep.setPromptText("CEP");

        logradouro = new TextField("");
        logradouro.setPromptText("Logradouro");

        numero = new TextField("");
        numero.setPromptText("Número");

        bairro = new TextField();
        bairro.setPromptText("Bairro");

        cidade = new TextField();
        cidade.setPromptText("Cidade");

        estado = new TextField();
        estado.setPromptText("Estado");

        complemento = new TextField();
        complemento.setPromptText("Complemento");

        dataNascimento = new DatePicker();
        dataNascimento.setPromptText("Data de nascimento");

        genero = new ToggleGroup();
        masc = new RadioButton("Masculino");
        fem = new RadioButton("Feminino");
        outro = new RadioButton("Outro");
        genero.getToggles().addAll(masc, fem, outro);

        tipoPessoa = new ToggleGroup();
        pessoaFisica = new RadioButton("Pessoa Física");
        pessoaJuridica = new RadioButton("Pessoa Jurídica");
        tipoPessoa.getToggles().addAll(pessoaFisica, pessoaJuridica);

        idoso = new CheckBox("É idoso");
        naoPossuiCpf = new CheckBox("Não possui CPF");
        naoPossuiRg = new CheckBox("Não possui RG");

        nacionalidade = new ComboBox<>();
        nacionalidade.getItems().addAll(Nacionalidade.asList());

        estadoCivil = new ComboBox<>();
        estadoCivil.getItems().addAll(EstadoCivil.asList());
    }

    @Override
    protected void layControls() {

        header.getChildren().addAll(stageLabel, cadastroHeader);

        stage1.getChildren().addAll();
    }

    @Override
    public BorderPane getContent() {
        return null;
    }
}
