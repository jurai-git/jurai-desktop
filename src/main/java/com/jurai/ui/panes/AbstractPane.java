package com.jurai.ui.panes;

import com.jurai.ui.controller.Controllable;
import javafx.scene.layout.Pane;

public abstract class AbstractPane implements Controllable {
    protected abstract void initControls();
    protected abstract void layControls();
    public abstract Pane getView();

    public AbstractPane() {
        initControls();
        layControls();
    }

}
