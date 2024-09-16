package com.jurai.ui.panes;

import com.jurai.ui.BaseView;
import com.jurai.ui.LazyLoaded;
import javafx.scene.layout.Pane;

public abstract class AbstractPane implements BaseView {
    protected abstract void initControls();
    protected abstract void layControls();
    public abstract Pane getView();

    public AbstractPane() {
        initControls();
        if (!LazyLoaded.class.isAssignableFrom(this.getClass())) {
            layControls();
        }
    }

}
