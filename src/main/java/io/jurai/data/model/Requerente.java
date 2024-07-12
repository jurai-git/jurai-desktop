package io.jurai.data.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.Optional;

public class Requerente {
    private final PropertyChangeSupport support;

    //dados pessoais
    private TipoPessoa tipoPessoa;
    private String nullableCpfCnpj;
    private String nome;
    private String nullableNomeSocial;
    private Genero genero;
    private boolean idoso;
    private String nullableRG;
    private EstadoCivil estadoCivil;
    private Nacionalidade nacionalidade;
    private String profissao;

    //dados de contato
    private Endereco endereco;
    private String nullableEmail;

    private Requerente() {
        nullableEmail = "";
        nullableNomeSocial = "";
        nullableCpfCnpj = "";
        nullableRG = "";
        support = new PropertyChangeSupport(new Requerente());
    }

    public Requerente(TipoPessoa tipoPessoa, String nome, Genero genero, boolean idoso, EstadoCivil estadoCivil,
                      Nacionalidade nacionalidade, String profissao, Endereco endereco, Map<String, Object> optionals) {
        this();
        this.tipoPessoa = tipoPessoa;
        this.nome = nome;
        this.genero = genero;
        this.idoso = idoso;
        this.estadoCivil = estadoCivil;
        this.nacionalidade = nacionalidade;
        this.profissao = profissao;
        this.endereco = endereco;

        if(optionals == null) return;

        Optional.ofNullable(optionals.get("cpfCnpj")).ifPresent(object -> nullableCpfCnpj = (String) object);
        Optional.ofNullable(optionals.get("rg")).ifPresent(object -> nullableRG = (String) object);
        Optional.ofNullable(optionals.get("nomeSocial")).ifPresent(object -> nullableNomeSocial = (String) object);
        Optional.ofNullable(optionals.get("email")).ifPresent(object -> nullableEmail = (String) object);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public String getCpfCnpj() {
        return nullableCpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeSocial() {
        return nullableNomeSocial;
    }

    public Genero getGenero() {
        return genero;
    }

    public boolean isIdoso() {
        return idoso;
    }

    public String getRg() {
        return nullableRG;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public Nacionalidade getNacionalidade() {
        return nacionalidade;
    }

    public String getProfissao() {
        return profissao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String getEmail() {
        return nullableEmail;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        TipoPessoa oldValue = this.tipoPessoa;
        this.tipoPessoa = tipoPessoa;
        support.firePropertyChange("tipoPessoa", oldValue, this.tipoPessoa);
    }

    public void setCpfCnpj(String nullableCpfCnpj) {
        String oldValue = this.nullableCpfCnpj;
        this.nullableCpfCnpj = nullableCpfCnpj;
        support.firePropertyChange("nullableCpfCnpj", oldValue, this.nullableCpfCnpj);
    }

    public void setNome(String nome) {
        String oldValue = this.nome;
        this.nome = nome;
        support.firePropertyChange("nome", oldValue, this.nome);
    }

    public void setNomeSocial(String nullableNomeSocial) {
        String oldValue = this.nullableNomeSocial;
        this.nullableNomeSocial = nullableNomeSocial;
        support.firePropertyChange("nullableNomeSocial", oldValue, this.nullableNomeSocial);
    }

    public void setGenero(Genero genero) {
        Genero oldValue = this.genero;
        this.genero = genero;
        support.firePropertyChange("genero", oldValue, this.genero);
    }

    public void setIdoso(boolean idoso) {
        boolean oldValue = this.idoso;
        this.idoso = idoso;
        support.firePropertyChange("idoso", oldValue, this.idoso);
    }

    public void setRG(String nullableRG) {
        String oldValue = this.nullableRG;
        this.nullableRG = nullableRG;
        support.firePropertyChange("nullableRG", oldValue, this.nullableRG);
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        EstadoCivil oldValue = this.estadoCivil;
        this.estadoCivil = estadoCivil;
        support.firePropertyChange("estadoCivil", oldValue, this.estadoCivil);
    }

    public void setNacionalidade(Nacionalidade nacionalidade) {
        Nacionalidade oldValue = this.nacionalidade;
        this.nacionalidade = nacionalidade;
        support.firePropertyChange("nacionalidade", oldValue, this.nacionalidade);
    }

    public void setProfissao(String profissao) {
        String oldValue = this.profissao;
        this.profissao = profissao;
        support.firePropertyChange("profissao", oldValue, this.profissao);
    }

    public void setEndereco(Endereco endereco) {
        Endereco oldValue = this.endereco;
        this.endereco = endereco;
        support.firePropertyChange("endereco", oldValue, this.endereco);
    }

    public void setEmail(String nullableEmail) {
        String oldValue = this.nullableEmail;
        this.nullableEmail = nullableEmail;
        support.firePropertyChange("nullableEmail", oldValue, this.nullableEmail);
    }
}
