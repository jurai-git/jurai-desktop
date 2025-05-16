package com.jurai.ui.util;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

public class SpacerFactory {

    public static Region hSpacer(Priority p) {
        Region spacer = new Region();

        if(p != null) {
            HBox.setHgrow(spacer, p);
        } else {
            HBox.setHgrow(spacer, Priority.NEVER);
        }

        return spacer;
    }

    public static Region hSpacer(DoubleBinding sizeProperty) {
        Region spacer = new Region();
        spacer.maxHeightProperty().bind(sizeProperty);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public static Region vSpacer(DoubleBinding sizeProperty) {
        Region spacer = new Region();
        spacer.maxHeightProperty().bind(sizeProperty);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public static Region hSpacer(double size)  {
        Region spacer = new Region();
        HBox.setMargin(spacer, new Insets(0, size, 0, 0));
        return spacer;
    }

    public static Region vSpacer(double size)  {
        Region spacer = new Region();
        VBox.setMargin(spacer, new Insets(size, 0, 0, 0));
        return spacer;
    }


    public static Region vSpacer(Priority p) {
        Region spacer = new Region();

        if(p != null) {
            VBox.setVgrow(spacer, p);
        } else {
            VBox.setVgrow(spacer, Priority.NEVER);
        }

        return spacer;
    }

}
