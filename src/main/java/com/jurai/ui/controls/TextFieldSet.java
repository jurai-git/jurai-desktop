package com.jurai.ui.controls;

import javafx.scene.control.TextField;
public final class TextFieldSet extends FieldSet<TextField> {

    public TextFieldSet(String name) {
        super(name, TextField.class);
        input.setPromptText(name);
    }
    
    public String getText() {
        return input.getText();
    }

    public void setText(String text) {
        input.setText(text);
    }

}
