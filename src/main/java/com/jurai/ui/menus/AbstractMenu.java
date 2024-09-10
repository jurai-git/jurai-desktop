package com.jurai.ui.menus;

import com.jurai.ui.controller.Controllable;
import javafx.scene.layout.Pane;

public abstract class AbstractMenu<T extends Pane> implements Controllable {
    public AbstractMenu() {
        initControls();
        layControls();
    }

    protected abstract void initControls();
    protected abstract void layControls();
    public abstract T getContent();
}
