package com.jurai.ui.controls;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.control.Label;

public class BooleanReactiveLabel extends Label {
    String prefix;

    public BooleanReactiveLabel(String prefix, ReadOnlyBooleanProperty watchedValue) {
        super();
        this.prefix = prefix;
        setWrapText(true);
        watchedValue.addListener((obs, oldVal, newVal) -> {
            update(newVal);
        });
        update(watchedValue.get());
    }

    public void update(boolean newValue) {
        setText(prefix.concat(newValue ? "Sim" : "Não"));
    }

}
