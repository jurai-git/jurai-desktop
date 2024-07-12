package io.jurai.data.model;

public enum EstadoCivil {

    SOLTEIRO("Solteiro(a)"),
    CASADO("Casado(a)"),
    SEPARADO("Separado(a)/Divorciado(a)"),
    VIUVO("Viuvo(a)");

    private String label;

    EstadoCivil(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
