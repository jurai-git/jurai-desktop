package com.jurai.ui.controls;

import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import static com.jurai.ui.util.ControlWrappers.wrapHgrow;
import static com.jurai.ui.util.ControlWrappers.wrapStyleClasses;

public class SettingsOption<T extends Control> extends HBox {
    T control;

    public SettingsOption(String name, T control) {
        super();
        this.control = control;
        setAlignment(Pos.CENTER);
        getChildren().addAll(
                wrapHgrow(new Label(name)),
                SpacerFactory.hSpacer(Priority.ALWAYS),
                control
        );
    }

    public T getControl() {
        return control;
    }
}
