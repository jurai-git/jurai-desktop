package com.jurai.data.model;


public enum TipoPessoa {
    PESSOA_FISICA("Pessoa Física"),
    PESOA_JURIDICA("Pessoa Jurídica");

    public final String label;

    TipoPessoa(String label) {
        this.label = label;
    }
}
