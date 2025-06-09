package com.jurai.ui.controls;

import com.jurai.util.Ref;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class StackGroup extends StackPane implements FXFluent<StackGroup> {
    @Override
    public Parent getSelf() {
        return this;
    }

    public StackGroup() {
        super();
    }


    public StackGroup bindMaxHeightProperty(ReadOnlyDoubleProperty binding) {
        maxHeightProperty().bind(binding);
        return this;
    }

    public StackGroup bindPrefHeightProperty(ReadOnlyDoubleProperty binding) {
        prefHeightProperty().bind(binding);
        return this;
    }

    public StackGroup getHeightProperty(Ref<ReadOnlyDoubleProperty> bindingRef) {
        bindingRef.value = heightProperty();
        return this;
    }
}
