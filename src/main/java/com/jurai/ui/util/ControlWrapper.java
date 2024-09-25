package com.jurai.ui.util;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ControlWrapper extends StackPane {

    public ControlWrapper(Node control) {
        super(control);
    }

    public ControlWrapper withVgrow(Priority p) {
        VBox.setVgrow(this, p);
        return this;
    }

    public ControlWrapper withHgrow(Priority p) {
        HBox.setHgrow(this, p);
        return this;
    }
}
