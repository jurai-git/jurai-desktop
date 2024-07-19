package io.jurai.ui.menus;

import io.jurai.ui.controller.Controllable;
import javafx.scene.Node;

public abstract class AbstractMenu implements Controllable {
    public AbstractMenu() {
        initControls();
        layControls();
    }

    protected abstract void initControls();
    protected abstract void layControls();

    public abstract Node getContent();
}
