package com.jurai.ui.modal;

import com.jurai.ui.menus.AbstractMenu;
import javafx.scene.layout.Pane;

public abstract class Modal<T extends Pane> extends AbstractMenu<T> {

    public Modal() {
        super();
        getContent().getStyleClass().add("modal");
    }

    public void show() {
        ModalManager.requestModal(this);
    }

    public void dispose() {
        ModalManager.exitModal();
    }

}
