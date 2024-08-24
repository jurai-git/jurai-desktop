package com.jurai.data.model;

import com.jurai.util.EventLogger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Map;

public class Endereco implements Model {

    private StringProperty cep = new SimpleStringProperty();
    private StringProperty logradouro = new SimpleStringProperty();
    private StringProperty numImovel = new SimpleStringProperty();
    private StringProperty bairro = new SimpleStringProperty();
    private StringProperty estado = new SimpleStringProperty();
    private StringProperty cidade = new SimpleStringProperty();

    private StringProperty complemento = new SimpleStringProperty("");

    private ObjectProperty<Requerente> requerente = new SimpleObjectProperty<>();

    public Endereco(String cep,
                    String logradouro,
                    String numImovel,
                    String bairro,
                    String estado,
                    String cidade,
                    Map<String, Object> optional) {

        this.cep.set(cep);
        this.logradouro.set(logradouro);
        this.bairro.set(bairro);
        this.numImovel.set(numImovel);
        this.estado.set(estado);
        this.cidade.set(cidade);

        optional.forEach((key, object) -> {
            try {
                switch (key) {
                    case "complemento":
                        this.complemento.set((String) object);
                        break;
                    default:
                        break;
                }
            } catch(Exception e) {
                EventLogger.logError("Wrong type in optional field in Endereco constructor");
            }
        });

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

    public String getNumImovel() {
        return numImovel.get();
    }

    public StringProperty numImovelProperty() {
        return numImovel;
    }

    public void setNumImovel(String numImovel) {
        this.numImovel.set(numImovel);
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

    public String getComplemento() {
        return complemento.get();
    }

    public StringProperty complementoProperty() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento.set(complemento);
    }

    public Requerente getRequerente() {
        return requerente.get();
    }

    public ObjectProperty<Requerente> requerenteProperty() {
        return requerente;
    }

    public void setRequerente(Requerente requerente) {
        this.requerente.set(requerente);
        requerente.enderecoProperty().bind(new SimpleObjectProperty<>(this));
    }


    @EmptyProperty
    @Override
    public StringProperty nomeProperty() {
        return new SimpleStringProperty("");
    }
}
