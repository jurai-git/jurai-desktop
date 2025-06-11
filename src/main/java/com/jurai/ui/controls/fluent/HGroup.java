package com.jurai.ui.controls.fluent;

import com.jurai.ui.util.Pane;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HGroup extends HBox implements FluentGroup<HGroup> {

    @Override
    public HGroup getSelf() {
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

    @Override
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
