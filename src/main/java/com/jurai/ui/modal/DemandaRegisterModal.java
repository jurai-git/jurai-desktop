package com.jurai.ui.modal;

import com.jurai.ui.LoadingStrategy;
import javafx.scene.control.Button;

@LoadingStrategy(LoadingStrategy.Strategy.EAGER)
public class DemandaRegisterModal extends DemandaModal {

    public DemandaRegisterModal() {
        super("demandaRegisterModal");
    }

    @Override
    protected void initButtons() {
        save = new Button("Criar");
    }
}
