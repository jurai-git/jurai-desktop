package io.jurai.ui.controller;

import io.jurai.ui.panes.AbstractPane;

public abstract class AbstractController<T extends AbstractPane> {
    public void initialize(T pane) {
        attachEvents(pane);
        attachNotifiers(pane);
    }
    protected abstract void attachEvents(T pane);
    protected abstract void attachNotifiers(T pane);
}
