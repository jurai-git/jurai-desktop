package com.jurai.ui.modal;

import com.jurai.ui.menus.AbstractMenu;
import javafx.scene.layout.Pane;

public abstract class Modal<T extends Pane> extends AbstractMenu<T> {
    private String name;

    public Modal(String name) {
        super();
        this.name = name;
        getContent().getStyleClass().add("modal");
    }

    public void show() {
        ModalManager.getInstance().requestModal(name);
    }

    public void dispose() {
        ModalManager.getInstance().exitModal();
    }

}
