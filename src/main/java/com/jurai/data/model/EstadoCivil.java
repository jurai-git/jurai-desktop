package com.jurai.data.model;

import java.util.List;

public enum EstadoCivil implements LabeledEnum{

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

    public static List<String> asList() {
        return AbstractLabeledEnum.asList(EstadoCivil.class);
    }
}
