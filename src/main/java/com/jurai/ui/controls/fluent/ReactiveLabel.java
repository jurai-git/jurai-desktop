package com.jurai.ui.controls.fluent;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class ReactiveLabel extends Label {
    String prefix;

    private ReactiveLabel(String prefix) {
        super();
        setWrapText(true);
        this.prefix = prefix;
    }

    public ReactiveLabel(String prefix, StringProperty watchedValue) {
        this(prefix);
        watchedValue.addListener((obs, oldVal, newVal) -> {
            update(newVal);
        });
        update(watchedValue.get());
    }

    public ReactiveLabel(String prefix, DoubleProperty watchedValue) {
        this(prefix);
        watchedValue.addListener((obs, oldVal, newVal) -> {
            update(newVal);
        });
        update(watchedValue.get());
    }

    public void update(Object newValue) {
        setText(prefix.concat(newValue.toString()));
    }

}
