package com.jurai.ui.controls;

import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HGroup extends HBox implements FXFluent<HGroup> {

    @Override
    public Parent getSelf() {
        return this;
    }

    private boolean withMargin = false;

    public HGroup() {
        getStyleClass().add("hgroup");
    }

    public HGroup withMargin() {
        withMargin = true;
        return this;
    }

    public HGroup withChildren(Node... es) {
        if(!withMargin) getChildren().addAll(es);
        else {
            for(Node n : es) {
                HBox.setHgrow(n, Priority.ALWAYS);
                getChildren().addAll(
                        SpacerFactory.hSpacer(Priority.ALWAYS),
                        n
                );
            }
            getChildren().add(SpacerFactory.hSpacer(Priority.ALWAYS));
        }
        return this;
    }

    public HGroup withAlignment(Pos alignment) {
        setAlignment(alignment);
        return this;
    }
}
