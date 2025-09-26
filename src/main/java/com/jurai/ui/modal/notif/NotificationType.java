package com.jurai.ui.modal.notif;

import com.jurai.data.model.AbstractLabeledEnum;
import com.jurai.data.model.EstadoCivil;
import com.jurai.data.model.LabeledEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
public enum NotificationType implements LabeledEnum {
    INFO("Informação"),
    WARNING("Aviso"),
    ERROR("Erro"),
    SUCCESS("Sucesso!"),
    CONFIRMATION("Confirmação");

    private final String label;

    private NotificationType(String label) {
        this.label = label;
    }

    public static List<String> asList() {
        return AbstractLabeledEnum.asList(NotificationType.class);
    }

    public String getLabel() {
        return this.label;
    }
}
