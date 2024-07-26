package io.jurai.ui.menus;

import io.jurai.ui.controller.Controllable;
import javafx.scene.Parent;

public abstract class AbstractMenu<T extends Parent> implements Controllable {
    public AbstractMenu() {
        initControls();
        layControls();
    }

    protected abstract void initControls();
    protected abstract void layControls();

    public abstract T getContent();
}
