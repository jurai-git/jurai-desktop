package com.jurai.ui.menus;

import com.jurai.ui.BaseView;
import com.jurai.ui.LazyLoaded;
import com.jurai.ui.controller.Controllable;
import javafx.scene.layout.Pane;

public abstract class AbstractMenu<T extends Pane> implements Controllable, BaseView {
    public AbstractMenu() {
        initControls();
        if (!LazyLoaded.class.isAssignableFrom(this.getClass())) {
            layControls();
        }
    }

    protected abstract void initControls();
    protected abstract void layControls();
    public abstract T getContent();
}
