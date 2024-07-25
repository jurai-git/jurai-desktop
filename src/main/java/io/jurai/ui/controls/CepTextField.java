package io.jurai.ui.controls;


import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CepTextField extends TextField {

    public CepTextField() {
        setTextFormatter(new TextFormatter<>(this::onChange));
    }

    public String getUnformattedText() {
        return getText().replace("-", "");
    }



    private TextFormatter.Change onChange(TextFormatter.Change change) {
        String newText = change.getControlNewText();


        // clear all non-number characters
        StringBuilder filtered = filter(newText);

        //append hyphen if necessary
        if(filtered.length() >=5 ) {
            filtered.insert(5, "-");
        }

        change.setText(filtered.toString());
        return change;
    }

    private StringBuilder filter(String text) {
        StringBuilder filtered = new StringBuilder();
        for (char c : text.toCharArray()) {
            if(conforms(c)) {
                filtered.append(c);
            }
        }
    }

    private boolean conforms(Character c) {
        return c.toString().matches("^[0-9]$\n");
    }
}
