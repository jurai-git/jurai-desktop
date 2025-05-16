package com.jurai.ui.controls;

import javafx.scene.control.PasswordField;


public final class PasswordFieldSet extends FieldSet<PasswordField> {

    public PasswordFieldSet(String name) {
        super(name, PasswordField.class);
        input.setPromptText(name);
    }

    public String getText() {
        return input.getText();
    }

    public void setText(String text) {
        input.setText(text);
    }

}
