package com.jurai.ui.controls;

import javafx.scene.control.TextArea;

public class TextAreaSet extends FieldSet<TextArea> {

    public TextAreaSet(String name) {
        super(name, TextArea.class);
        input.setPromptText(name);
    }

    public String getText() {
        return input.getText();
    }

    public void setText(String s) {
        input.setText(s);
    }

}
