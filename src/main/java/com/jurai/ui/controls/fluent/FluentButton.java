package com.jurai.ui.controls.fluent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FluentButton extends Button implements FluentControl<FluentButton> {
    @Override
    public Control getSelf() {
        return this;
    }

    public FluentButton(String text) {
        super(text);
    }

    public FluentButton withAction(EventHandler<ActionEvent> handler) {
        setOnAction(handler);
        return this;
    }

}
