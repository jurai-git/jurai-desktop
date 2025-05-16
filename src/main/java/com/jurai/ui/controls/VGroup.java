package com.jurai.ui.controls;

import com.jurai.ui.util.SpacerFactory;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VGroup extends VBox {
    private boolean withMargin = false;

    public VGroup() {
        getStyleClass().add("vgroup");
    }

    public VGroup withHgrow(Priority p) {
        HBox.setHgrow(this, p);
        return this;
    }

    public VGroup withVgrow(Priority p) {
        VBox.setVgrow(this, p);
        return this;
    }

    public VGroup withMargin() {
        withMargin = true;
        return this;
    }

    public VGroup withChildren(Node... es) {
        if(!withMargin) {
            getChildren().addAll(es);
            return this;
        }

        for(Node n : es) {
            HBox.setHgrow(n, Priority.ALWAYS);
            getChildren().addAll(
                    SpacerFactory.hSpacer(Priority.ALWAYS),
                    n
            );
        }
        getChildren().add(SpacerFactory.hSpacer(Priority.ALWAYS));
        return this;
    }

    public VGroup withStyleClass(String ...styleClass) {
        getStyleClass().addAll(styleClass);
        return this;
    }

    public VGroup withStyle(String style) {
        setStyle(style);
        return this;
    }

}
