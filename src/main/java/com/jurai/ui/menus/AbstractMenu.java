package com.jurai.ui.menus;

import com.jurai.ui.BaseView;
import javafx.scene.layout.Pane;

public abstract class AbstractMenu<T extends Pane> implements BaseView {
    public AbstractMenu() {
        initControls();
        layControls();
    }

    protected abstract void initControls();
    protected abstract void layControls();
    public abstract T getContent();
}
