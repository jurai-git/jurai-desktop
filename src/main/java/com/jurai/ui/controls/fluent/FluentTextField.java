package com.jurai.ui.controls.fluent;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FluentTextField extends TextField implements FluentControl<FluentTextField> {
    @Override
    public Control getSelf() {
        return this;
    }

    public FluentTextField() {}

    public FluentTextField withPrompt(String promptText) {
        setPromptText(promptText);
        return this;
    }

    public FluentTextField bindTextValue(StringProperty binded) {
        binded.bind(textProperty());
        return this;
    }
}
