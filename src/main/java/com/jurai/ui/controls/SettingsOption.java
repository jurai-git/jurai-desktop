package com.jurai.ui.controls;

import com.jurai.ui.util.SpacerFactory;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import static com.jurai.ui.util.ControlWrappers.wrapHgrow;

public class SettingsOption<T extends Control> extends HBox {
    T control;

    public SettingsOption(String name, T control) {
        super();
        this.control = control;
        getChildren().addAll(
                wrapHgrow(new Label(name)),
                SpacerFactory.hSpacer(Priority.ALWAYS),
                wrapHgrow(control)
        );
    }

    public T getControl() {
        return control;
    }
}
