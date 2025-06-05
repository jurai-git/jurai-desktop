package com.jurai.ui.menus;

import javafx.scene.layout.Pane;

public abstract class AbstractMenu<T extends Pane> {
    public AbstractMenu() {
        initControls();
        layControls();
    }

    protected abstract void initControls();
    protected abstract void layControls();
    public abstract T getContent();
}
