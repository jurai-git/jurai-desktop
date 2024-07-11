package io.jurai.ui.panes;

import javafx.scene.layout.Pane;

public abstract class AbstractPane {
    protected abstract void initControls();
    protected abstract void layControls();
    public abstract Pane getView();

    public AbstractPane() {
        initControls();
        layControls();
    }

}
