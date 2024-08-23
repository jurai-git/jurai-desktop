package io.jurai.ui.util;

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
        System.out.println(l.getHeight());
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
