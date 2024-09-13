package com.jurai.ui.controls;

import com.jurai.util.UILogger;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FieldSet<T extends Node> extends VBox {
    private final Label name;
    protected T input;

    public FieldSet(String name, Class<T> classOfT) {
        getStyleClass().add("fieldset");
        this.name = new Label(name);
        try {
            input = classOfT.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            UILogger.logError("Unable to create FieldSet input instance of type " + classOfT.getName());
        }
        layControls();
    }

    protected void layControls() {
        VBox.setVgrow(input, Priority.ALWAYS);
        VBox.setVgrow(name, Priority.ALWAYS);
        getChildren().addAll(name, input);
    }

    public T getInput() {
        return input;
    }

}
