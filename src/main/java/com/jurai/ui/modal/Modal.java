package com.jurai.ui.modal;

import com.jurai.ui.menus.AbstractMenu;
import javafx.scene.CacheHint;
import javafx.scene.layout.Pane;

@LoadingStrategy
public abstract class Modal<T extends Pane> extends AbstractMenu<T> {
    private String name;

    public Modal(String name) {
        super();
        this.name = name;
        getContent().getStyleClass().add("modal");
        getContent().setCache(true);
        getContent().setCacheHint(CacheHint.SPEED);
        getContent().setCacheShape(true);
    }

    public void show() {
        ModalManager.getInstance().requestModal(name);
    }

    public void dispose() {
        ModalManager.getInstance().exitModal();
    }

}
