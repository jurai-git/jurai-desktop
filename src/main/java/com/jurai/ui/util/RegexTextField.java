package com.jurai.ui.util;

import javafx.scene.control.TextField;

public class RegexTextField extends TextField {
    private String regex = "";
    private int maxLength = Integer.MAX_VALUE;

    public RegexTextField() {
        super();

        textProperty().addListener((observableValue, s, t1) -> {
            if(t1.length() > maxLength || !t1.matches(regex)) {
                setText(s);
            }
        });
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
