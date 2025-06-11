package com.jurai.ui.controls.fluent;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Region;
import org.fxmisc.richtext.InlineCssTextArea;

public class ReactiveRichLabel extends InlineCssTextArea {
    private final String prefix;

    private ReactiveRichLabel(String prefix) {
        super();
        this.prefix = prefix;

        setEditable(false);
        setWrapText(true);
        setFocusTraversable(false);
        setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        // Remove extra padding and shrink to content
        setPadding(javafx.geometry.Insets.EMPTY);
        setPrefHeight(Region.USE_COMPUTED_SIZE);
        setMinHeight(Region.USE_PREF_SIZE);
        setMaxHeight(Double.MAX_VALUE);

        // Listen to text changes to auto-resize

    }

    public ReactiveRichLabel(String prefix, StringProperty watchedValue) {
        this(prefix);
        watchedValue.addListener((obs, oldVal, newVal) -> update(newVal));
        update(watchedValue.get());
    }

    public ReactiveRichLabel(String prefix, DoubleProperty watchedValue) {
        this(prefix);
        watchedValue.addListener((obs, oldVal, newVal) -> update(newVal));
        update(watchedValue.get());
    }

    private void update(Object newValue) {
        replaceText("");
        appendText(""); // Force internal layout refresh
        append(prefix, "-fx-font-weight: bold;");
        append(newValue.toString(), "-fx-font-weight: normal;");
    }
}
