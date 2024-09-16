package com.jurai.ui.modal;

import javafx.scene.control.Button;

@LoadingStrategy(LoadingStrategy.Strategy.LAZY)
public class DemandaRegisterModal extends DemandaModal {

    public DemandaRegisterModal() {
        super("demandaRegisterModal");
    }

    @Override
    protected void initButtons() {
        save = new Button("Criar");
    }
}
