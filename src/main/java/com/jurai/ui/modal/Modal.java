package com.jurai.ui.modal;

import com.jurai.ui.menus.AbstractMenu;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

public abstract class Modal<T extends Pane> extends AbstractMenu<T> {

    public void show() {
        ModalHandler.requestModal(this);
    }

    public void dispose() {
        ModalHandler.exitModal();
    }

}
