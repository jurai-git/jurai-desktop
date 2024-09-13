package com.jurai.ui.modal;

import javafx.scene.control.Button;

@LoadingStrategy(LoadingStrategy.Strategy.EAGER)
public class RequerenteRegisterModal extends RequerenteModal {

    public RequerenteRegisterModal() {
        super("requerenteRegisterModal");
    }

    @Override
    protected void initButtons() {
        addressCancel = new Button("Cancelar");
        generalCancel = new Button("Cancelar");
        personalCancel = new Button("Cancelar");
        create = new Button("Criar");
        addressInfoPrevious = new Button("Anterior");
        generalInfoPrevious = new Button("Anterior");
        generalInfoNext = new Button("Próximo");
        personalInfoNext = new Button("Próximo");
    }
}
