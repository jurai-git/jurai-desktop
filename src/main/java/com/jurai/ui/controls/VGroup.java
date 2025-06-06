package com.jurai.ui.controls;

import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VGroup extends VBox implements FXFluent<VGroup> {

    @Override
    public Parent getSelf() {
        return this;
    }


    private boolean withMargin = false;

    public VGroup() {
        getStyleClass().add("vgroup");
    }

    public VGroup withMargin() {
        withMargin = true;
        return this;
    }

    @Override
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

    public VGroup withAlignment(Pos pos) {
        setAlignment(pos);
        return this;
    }

}
