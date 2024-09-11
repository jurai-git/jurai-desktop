package com.jurai.data.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class Advogado implements Model, Serializable {

    // informacoes pessoais
    private DoubleProperty id = new SimpleDoubleProperty();
    private StringProperty username = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty oab = new SimpleStringProperty();
    private StringProperty accessToken = new SimpleStringProperty();
    private ObservableList<Requerente> requerentes = FXCollections.observableArrayList();

    public Advogado(int id, String nome, String email, String oab, String accessToken) {
        this.id.set(id);
        this.username.set(nome);
        this.email.set(email);
        this.oab.set(oab);
        this.accessToken.set(accessToken);
    }

    public String getAccessToken() {
        return accessToken.get();
    }

    public ReadOnlyStringProperty accessTokenProperty() {
        return accessToken;
    }

    // getters and setters

    public double getId() {
        return id.get();
    }

    public String getNome() {
        return username.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getOab() {
        return oab.get();
    }

    public void setUsername(String nome) {
        this.username.set(nome);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setOab(String oab) {
        this.oab.set(oab);
    }

    //property access

    public StringProperty oabProperty() {
        return oab;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public ReadOnlyDoubleProperty idProperty() {
        return id;
    }

    public ObservableList<Requerente> getRequerentes() {
        return requerentes;
    }

    @Override
    public StringProperty nomeProperty() {
        return username;
    }

    @Override
    public String toString() {
        return "{io.jurai.data.model.Advogado: " + getNome() + "}";
    }

}
