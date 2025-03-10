package com.jurai.ui.modal;

import com.jurai.ui.LoadingStrategy;
import javafx.scene.layout.Pane;

// notification will always be lazy, as they are fairly simple
@LoadingStrategy(LoadingStrategy.Strategy.LAZY)
public abstract class Notification<T extends Pane> extends Modal<T> {
    protected boolean isTransparent = false;

    public Notification() {
        super("notification");
    }

    @Override
    public void show() {
        ModalManager.getInstance().requestNotification(this);
    }

    @Override
    public void dispose() {
        ModalManager.getInstance().exitNotification();
    }

    public boolean isTransparent() {
        return isTransparent;
    }
}
