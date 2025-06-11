package com.jurai.ui.controls.fluent;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.*;

public interface FluentGroup<T extends FluentGroup<T>> extends FluentFX<T> {
    Pane getSelf();

    @SuppressWarnings("unchecked")
    default T withChildren(Node ...children) {
        getSelf().getChildren().addAll(children);
        return (T) this;
    }
}
