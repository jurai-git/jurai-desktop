package com.jurai.ui.util;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class SpacerFactory {

    private static final TextField l = new TextField("aaa");

    public static void initialize() {
        Group g = new Group();
        g.getChildren().add(l);
        Scene s = new Scene(g);
        g.layout();
    }

    public static Region createHBoxSpacer(Priority p) {
        Region spacer = new Region();

        if(p != null) {
            HBox.setHgrow(spacer, p);
        } else {
            HBox.setHgrow(spacer, Priority.NEVER);
            spacer.minWidthProperty().bind(l.minHeightProperty());
        }

        return spacer;
    }

    public static Region createHBoxSpacer(DoubleBinding sizeProperty) {
        Region spacer = new Region();
        spacer.maxHeightProperty().bind(sizeProperty);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public static Region createVBoxSpacer(DoubleBinding sizeProperty) {
        Region spacer = new Region();
        spacer.maxHeightProperty().bind(sizeProperty);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public static Region createHBoxSpacer(int size)  {
        Region spacer = new Region();
        HBox.setMargin(spacer, new Insets(0, size, 0, 0));
        return spacer;
    }

    public static Region createVBoxSpacer(int size)  {
        Region spacer = new Region();
        VBox.setMargin(spacer, new Insets(size, 0, 0, 0));
        return spacer;
    }


    public static Region createVBoxSpacer(Priority p) {
        Region spacer = new Region();

        if(p != null) {
            VBox.setVgrow(spacer, p);
        } else {
            VBox.setVgrow(spacer, Priority.NEVER);
            spacer.minHeightProperty().bind(l.minHeightProperty());
        }

        return spacer;
    }

}
