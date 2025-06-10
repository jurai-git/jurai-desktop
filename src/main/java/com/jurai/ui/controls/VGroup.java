package com.jurai.ui.controls;

import com.jurai.ui.util.SpacerFactory;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import com.jurai.util.Ref;

import java.lang.ref.Reference;

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

    public VGroup bindMaxHeightProperty(ReadOnlyDoubleProperty binding) {
        maxHeightProperty().bind(binding);
        return this;
    }

    public VGroup bindPrefHeightProperty(ReadOnlyDoubleProperty binding) {
        prefHeightProperty().bind(binding);
        return this;
    }

    public VGroup getHeightProperty(Ref<ReadOnlyDoubleProperty> bindingRef) {
        bindingRef.value = heightProperty();
        return this;
    }

    public VGroup bindMaxWidthProperty(DoubleBinding binding) {
        maxWidthProperty().bind(binding);
        return this;
    }

    public VGroup bindPrefWidthProperty(DoubleBinding binding) {
        prefWidthProperty().bind(binding);
        return this;
    }

}