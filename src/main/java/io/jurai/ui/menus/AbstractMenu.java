package io.jurai.ui.menus;

import javafx.scene.Node;

public abstract class AbstractMenu {
    public AbstractMenu() {
        initControls();
        layControls();
    }

    protected abstract void initControls();
    protected abstract void layControls();

    protected abstract Node getContent();
}
