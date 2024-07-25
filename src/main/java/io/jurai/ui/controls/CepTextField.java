package io.jurai.ui.controls;


import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class BasicFormattedTextField extends TextField {
    private String regex;
    protected TextFormatter<String> formatter;


    public BasicFormattedTextField() {
        formatter = new TextFormatter<>(this::onChange);
    }

    private TextFormatter.Change onChange(TextFormatter.Change change) {
        String newText = change.getControlNewText();

        if(conforms(newText)) {
            change.setText(newText);
        }
    }

    private boolean conforms(String s) {
        return s.matches(regex);
    }


    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
