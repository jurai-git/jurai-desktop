package com.jurai.ui.modal.notif;

import com.jurai.data.model.AbstractLabeledEnum;
import com.jurai.data.model.EstadoCivil;
import com.jurai.data.model.LabeledEnum;

import java.util.List;

public enum NotificationType implements LabeledEnum {
    INFO("Informação"),
    WARNING("Aviso"),
    ERROR("Erro"),
    SUCCESS("Sucesso!");

    private String label;

    NotificationType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static List<String> asList() {
        return AbstractLabeledEnum.asList(EstadoCivil.class);
    }
}
