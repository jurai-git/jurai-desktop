package com.jurai.data.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.util.Map;

public class Requerente implements Model {

    // primary fields
    private IntegerProperty idRequerente = new SimpleIntegerProperty();

    // Main fields
    private StringProperty cpfCnpj = new SimpleStringProperty();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty nomeSocial = new SimpleStringProperty();
    private StringProperty genero = new SimpleStringProperty();
    private BooleanProperty idoso = new SimpleBooleanProperty();
    private StringProperty rg = new SimpleStringProperty();
    private StringProperty orgaoEmissor = new SimpleStringProperty();
    private StringProperty estadoCivil = new SimpleStringProperty();
    private StringProperty nacionalidade = new SimpleStringProperty();
    private StringProperty profissao = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();

    // Address fields (one-to-one, so included in the same class)
    private StringProperty cep = new SimpleStringProperty();
    private StringProperty logradouro = new SimpleStringProperty();
    private StringProperty numImovel = new SimpleStringProperty();
    private StringProperty complemento = new SimpleStringProperty();
    private StringProperty bairro = new SimpleStringProperty();
    private StringProperty estado = new SimpleStringProperty();
    private StringProperty cidade = new SimpleStringProperty();

    // FKs
    private ObservableList<Demanda> demandas = new SimpleListProperty<>();

    public Requerente(String cpfCnpj,
                      String nome, String nomeSocial, String genero, boolean idoso,
                      String rg, String orgaoEmissor, String estadoCivil,
                      String nacionalidade, String profissao, String cep,
                      String logradouro, String email, String numImovel,
                      String complemento, String bairro, String estado,
                      String cidade) {
        this.cpfCnpj.set(cpfCnpj);
        this.nome.set(nome);
        this.nomeSocial.set(nomeSocial);
        this.genero.set(genero);
        this.idoso.set(idoso);
        this.rg.set(rg);
        this.orgaoEmissor.set(orgaoEmissor);
        this.estadoCivil.set(estadoCivil);
        this.nacionalidade.set(nacionalidade);
        this.profissao.set(profissao);
        this.cep.set(cep);
        this.logradouro.set(logradouro);
        this.email.set(email);
        this.numImovel.set(numImovel);
        this.complemento.set(complemento);
        this.bairro.set(bairro);
        this.estado.set(estado);
        this.cidade.set(cidade);
    }

    public int getIdRequerente() {
        return idRequerente.get();
    }

    public IntegerProperty idRequerenteProperty() {
        return idRequerente;
    }

    public void setIdRequerente(int idRequerente) {
        this.idRequerente.set(idRequerente);
    }

    public String getCpfCnpj() {
        return cpfCnpj.get();
    }

    public StringProperty cpfCnpjProperty() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj.set(cpfCnpj);
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty rawNomeProperty() {
        return nome;
    }

    @Override
    public StringProperty nomeProperty() {
        return nomeSocial.get().strip().isEmpty() ? nome : nomeSocial;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getNomeSocial() {
        return nomeSocial.get();
    }

    public StringProperty nomeSocialProperty() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial.set(nomeSocial);
    }

    public String getGenero() {
        return genero.get();
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public void setGenero(String genero) {
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

    public String getEstadoCivil() {
        return estadoCivil.get();
    }

    public StringProperty estadoCivilProperty() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil.set(estadoCivil);
    }

    public String getNacionalidade() {
        return nacionalidade.get();
    }

    public StringProperty nacionalidadeProperty() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
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

    public String getCep() {
        return cep.get();
    }

    public StringProperty cepProperty() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep.set(cep);
    }

    public String getLogradouro() {
        return logradouro.get();
    }

    public StringProperty logradouroProperty() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro.set(logradouro);
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

    public String getNumImovel() {
        return numImovel.get();
    }

    public StringProperty numImovelProperty() {
        return numImovel;
    }

    public void setNumImovel(String numImovel) {
        this.numImovel.set(numImovel);
    }

    public String getComplemento() {
        return complemento.get();
    }

    public StringProperty complementoProperty() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento.set(complemento);
    }

    public String getBairro() {
        return bairro.get();
    }

    public StringProperty bairroProperty() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro.set(bairro);
    }

    public String getEstado() {
        return estado.get();
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public String getCidade() {
        return cidade.get();
    }

    public StringProperty cidadeProperty() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade.set(cidade);
    }

    public void addDemanda(Demanda d) {
        demandas.add(d);
    }

    public void removeDemanda(Demanda d) {
        demandas.remove(d);
    }

    public ObservableList<Demanda> demandas() {
        return demandas;
    }
}