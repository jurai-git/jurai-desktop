package com.jurai.ui.modal.notif;

import com.jurai.data.model.AbstractLabeledEnum;
import com.jurai.data.model.EstadoCivil;
import com.jurai.data.model.LabeledEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum NotificationType implements LabeledEnum {
    INFO("Informação"),
    WARNING("Aviso"),
    ERROR("Erro"),
    SUCCESS("Sucesso!"),
    CONFIRMATION("Confirmação");

    private final String label;

    public static List<String> asList() {
        return AbstractLabeledEnum.asList(NotificationType.class);
    }
}
