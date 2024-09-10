package com.jurai.ui.controls;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class TextFieldSet extends VBox {
    private Label name;
    private TextField input;

    public TextFieldSet(String name) {
        super();
        getStyleClass().add("fieldset");
        this.name = new Label(name);
        input = new TextField();
        input.setPromptText(name);
        initComponents();
    }

    private void initComponents() {
        VBox.setVgrow(input, Priority.ALWAYS);
        VBox.setVgrow(name, Priority.ALWAYS);
        getChildren().addAll(name, input);
    }

    
    public String getText() {
        return input.getText();
    }

    public void setText(String text) {
        input.setText(text);
    }

}
