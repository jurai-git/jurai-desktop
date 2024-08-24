package com.jurai.data.model;

import javafx.beans.property.*;

import java.util.Map;

public class Requerente implements Model {

    private ObjectProperty<TipoPessoa> tipoPessoa = new SimpleObjectProperty<>();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty nomeSocial = new SimpleStringProperty("");
    private ObjectProperty<Genero> genero = new SimpleObjectProperty<>();
    private BooleanProperty idoso = new SimpleBooleanProperty(false);
    private StringProperty rg = new SimpleStringProperty("");
    private StringProperty orgaoEmissor = new SimpleStringProperty("");
    private ObjectProperty<EstadoCivil> estadoCivil = new SimpleObjectProperty<>();
    private ObjectProperty<Nacionalidade> nacionalidade = new SimpleObjectProperty<>();
    private StringProperty profissao = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty cpfCnpjCli = new SimpleStringProperty();

    private ObjectProperty<Endereco> endereco = new SimpleObjectProperty<>();
    private ObjectProperty<Advogado> advogado = new SimpleObjectProperty<>();

    public Requerente(String cpfCnpjCli,
                      TipoPessoa tipoPessoa,
                      String nome,
                      Genero genero,
                      EstadoCivil estadoCivil,
                      Nacionalidade nacionalidade,
                      String profissao,
                      String email,
                      Advogado advogado,
                      Endereco endereco,
                      Map<String, Object> optional) {

        this.cpfCnpjCli.set(cpfCnpjCli);
        this.tipoPessoa.set(tipoPessoa);
        this.nome.set(nome);
        this.genero.set(genero);
        this.estadoCivil.set(estadoCivil);
        this.nacionalidade.set(nacionalidade);
        this.profissao.set(profissao);
        this.email.set(email);
        this.advogado.set(advogado);
        this.endereco.set(endereco);

        this.endereco.get().requerenteProperty().bind(new SimpleObjectProperty<>(this));

        optional.forEach((key, object) -> {
            switch(key) {
                case "nomeSocial":
                    nomeSocial.set((String) object);
                    break;
                case "rg":
                    rg.set((String) object);
                    break;
                case "orgaoEmissor":
                    orgaoEmissor.set((String) object);
                    break;
                case "idoso":
                    idoso.set((Boolean) object);
                    break;
                default:
                    break;
            }
        });

        if(nomeSocial.get().isEmpty()) {
            nomeSocial.bind(this.nome);
        }
    }

    public String getCpfCnpjCli() {
        return cpfCnpjCli.get();
    }

    public StringProperty cpfCnpjCliProperty() {
        return cpfCnpjCli;
    }

    public void setCpfCnpjCli(String cpfCnpjCli) {
        this.cpfCnpjCli.set(cpfCnpjCli);
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa.get();
    }

    public ObjectProperty<TipoPessoa> tipoPessoaProperty() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa.set(tipoPessoa);
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeRegistroProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getNomeSocial() {
        return nomeSocial.get();
    }

    @Override
    public StringProperty nomeProperty() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial.set(nomeSocial);
        this.nome.unbind();
    }

    public Genero getGenero() {
        return genero.get();
    }

    public ObjectProperty<Genero> generoProperty() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero.set(genero);
    }

    public boolean isIdoso() {
        return idoso.get();
    }

    public BooleanProperty idosoProperty() {
        return idoso;
    }

    public void setIdoso(boolean idoso) {
        this.idoso.set(idoso);
    }

    public String getRg() {
        return rg.get();
    }

    public StringProperty rgProperty() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg.set(rg);
    }

    public String getOrgaoEmissor() {
        return orgaoEmissor.get();
    }

    public StringProperty orgaoEmissorProperty() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor.set(orgaoEmissor);
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil.get();
    }

    public ObjectProperty<EstadoCivil> estadoCivilProperty() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil.set(estadoCivil);
    }

    public Nacionalidade getNacionalidade() {
        return nacionalidade.get();
    }

    public ObjectProperty<Nacionalidade> nacionalidadeProperty() {
        return nacionalidade;
    }

    public void setNacionalidade(Nacionalidade nacionalidade) {
        this.nacionalidade.set(nacionalidade);
    }

    public String getProfissao() {
        return profissao.get();
    }

    public StringProperty profissaoProperty() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao.set(profissao);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public Endereco getEndereco() {
        return endereco.get();
    }

    public ObjectProperty<Endereco> enderecoProperty() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco.set(endereco);
        this.endereco.get().requerenteProperty().bind(new SimpleObjectProperty<>(this));
    }

    public Advogado getAdvogado() {
        return advogado.get();
    }

    public ObjectProperty<Advogado> advogadoProperty() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado.set(advogado);
    }
}