package com.jurai.ui.controls;

import com.jurai.ui.util.SpacerFactory;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HGroup extends HBox {
    private boolean withMargin = false;

    public HGroup() {
        getStyleClass().add("hgroup");
    }

    public HGroup withVgrow(Priority p) {
        VBox.setVgrow(this, p);
        return this;
    }

    public HGroup withMargin() {
        withMargin = true;
        return this;
    }

    public HGroup withStyleClass(String styleClass) {
        getStyleClass().add(styleClass);
        return this;
    }

    public HGroup withChildren(Node... es) {
        if(!withMargin) getChildren().addAll(es);
        else {
            for(Node n : es) {
                HBox.setHgrow(n, Priority.ALWAYS);
                getChildren().addAll(
                        SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                        n
                );
            }
            getChildren().add(SpacerFactory.createHBoxSpacer(Priority.ALWAYS));
        }
        return this;
    }

}
