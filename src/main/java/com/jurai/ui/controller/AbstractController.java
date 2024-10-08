package com.jurai.ui.controller;

public abstract class AbstractController<T> {
    public void initialize(T pane) {
        attachEvents(pane);
        attachNotifiers(pane);
    }
    protected abstract void attachEvents(T pane);
    protected abstract void attachNotifiers(T pane);
}
