package com.jurai.ui.util;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ControlWrappers {
    public static Node wrapHgrow(Node c, Priority p) {
        HBox.setHgrow(c, p);
        return c;
    }
    public static Node wrapHgrow(Node c) {
        return wrapHgrow(c, Priority.ALWAYS);
    }

    public static Node wrapVgrow(Node c, Priority p) {
        VBox.setVgrow(c, p);
        return c;
    }
    public static Node wrapVgrow(Node c) {
        return wrapVgrow(c, Priority.ALWAYS);
    }

    public static Node wrapStyleClasses(Node c, String ...classes) {
        c.getStyleClass().addAll(classes);
        return c;
    }
}
