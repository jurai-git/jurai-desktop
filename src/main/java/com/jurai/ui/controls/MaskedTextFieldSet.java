package com.jurai.ui.controls;

import com.github.gbfragoso.MaskedTextField;

public class MaskedTextFieldSet extends FieldSet<MaskedTextField> {

    public MaskedTextFieldSet(String name) {
        super(name, MaskedTextField.class);
    }

    public void setMask(String mask) {
        input.setMask(mask);
    }

    public String getText() {
        return input.getPlainText();
    }

    public void setText(String text) {
        input.setPlainText(text);
    }
}
