package com.jurai.ui.util;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class ControlWrappers {
    public static <T extends Node> T wrapHgrow(T c, Priority p) {
        HBox.setHgrow(c, p);
        return c;
    }
    public static <T extends Node> T wrapHgrow(T c) {
        return wrapHgrow(c, Priority.ALWAYS);
    }

    public static <T extends Node> T wrapVgrow(T c, Priority p) {
        VBox.setVgrow(c, p);
        return c;
    }
    public static <T extends Node> T wrapVgrow(T c) {
        return wrapVgrow(c, Priority.ALWAYS);
    }

    public static <T extends Node> T wrapStyleClasses(T c, String ...classes) {
        c.getStyleClass().addAll(classes);
        return c;
    }

    public static <T extends Node> T wrapStyle(T c, String style) {
        c.setStyle(c.getStyle().concat(style));
        return c;
    }

    public static Label wrapAlignment(Label l, TextAlignment pos) {
        l.setTextAlignment(pos);
        return l;
    }
}
