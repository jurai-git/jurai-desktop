package io.jurai.data.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;

public class Advogado implements Model {

    // informacoes pessoais
    private double id;
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty senha = new SimpleStringProperty();



    private StringProperty oab = new SimpleStringProperty();

    public Advogado(String nome, String email, String senha, String oab) {
        this.nome.set(nome);
        this.email.set(email);
        this.senha.set(senha);
        this.oab.set(oab);
        id = new Random().nextDouble();
    }

    // getters and setters

    public double getId() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getSenha() {
        return senha.get();
    }

    public String getOab() {
        return oab.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setSenha(String senha) {
        this.senha.set(senha);
    }

    public void setOab(String oab) {
        this.oab.set(oab);
    }

    //property access

    public StringProperty oabProperty() {
        return oab;
    }

    public StringProperty senhaProperty() {
        return senha;
    }

    public StringProperty emailProperty() {
        return email;
    }

    @Override
    public StringProperty nomeProperty() {
        return nome;
    }

    @Override
    public String toString() {
        return "{io.jurai.data.model.Advogado: " + getNome() + "}";
    }
}
