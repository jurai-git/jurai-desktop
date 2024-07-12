package io.jurai.data.model;

import java.util.*;

public class Advogado {

    // informacoes pessoais
    private String nullableNome;
    private String nomeUsuario;
    private String nullableNomeSocial;
    private Genero genero;
    private Date dataNascimento;
    private Nacionalidade nacionalidade;

    // informacoes tecnicas
    private TipoPessoa tipoPessoa;
    private String cpfCnpj;
    private String oab;

    // informacoes de contato
    private String email;
    private Endereco endereco;

    // informacoes do jurai

    public Advogado() {

    }

    public Advogado(String cpfCnpj, TipoPessoa tipoPessoa, String nomeUsuario, Genero genero, Date dataNascimento, Nacionalidade nacionalidade, String oab, String email, Endereco endereco, Map<String, Object> outros) {
        this();
        this.cpfCnpj = cpfCnpj;
        this.tipoPessoa = tipoPessoa;
        this.nomeUsuario = nomeUsuario;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
        this.oab = oab;
        this.email = email;
        this.endereco = endereco;

        if (outros == null) return;

        Optional.ofNullable(outros.get("nome")).ifPresent(value -> this.nullableNome = (String) value);
        Optional.ofNullable(outros.get("nomeSocial")).ifPresent(value -> this.nullableNomeSocial = (String) value);
    }

    public String getNome() {
        return nullableNome;
    }

    public void setNome(String nullableNome) {
        this.nullableNome = nullableNome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeSocial() {
        return nullableNomeSocial;
    }

    public void setNomeSocial(String nullableNomeSocial) {
        this.nullableNomeSocial = nullableNomeSocial;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getOab() {
        return oab;
    }

    public void setOab(String oab) {
        this.oab = oab;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Nacionalidade getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Nacionalidade nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    @Override
    public String toString() {
        return "{io.jurai.data.model.Advogado: " + getNomeUsuario() + "}";
    }
}
