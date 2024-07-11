package io.jurai.data.model;

import java.util.*;

public class Advogado {
    private static int newID = 0;
    private int id;

    // informacoes pessoais
    private String nome;
    private String nomeUsuario;
    private String nomeSocial;
    private Genero genero;
    private Date dataNascimento;
    private Nacionalidade nacionalidade;

    // informacoes tecnicas
    private TipoPessoa tipoPessoa;
    private String cpfCnpj;
    private String oab;

    // informacoes de contato
    private String email;
    private String telefone;
    private Endereco endereco;

    public Advogado() {
        this.id = newID;
        newID++;
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

        Optional.ofNullable(outros.get("nome")).ifPresent(value -> this.nome = (String) value);
        Optional.ofNullable(outros.get("nomeSocial")).ifPresent(value -> this.nomeSocial = (String) value);
        Optional.ofNullable(outros.get("telefone")).ifPresent(value -> this.telefone = (String) value);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
}
