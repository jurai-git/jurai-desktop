package com.jurai.ui.controls;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.*;

public interface FXFluent<T extends FXFluent<T>> {
    Parent getSelf();

    @SuppressWarnings("unchecked")
    default T withStyleClass(String ...classes) {
        getSelf().getStyleClass().addAll(classes);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T withChildren(Node...children) {
        if (getSelf() instanceof Pane pane) {
            pane.getChildren().addAll(children);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T withVgrow(Priority priority) {
        VBox.setVgrow(getSelf(), priority);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T withHgrow(Priority priority) {
        HBox.setHgrow(getSelf(), priority);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T withBorderPaneAlignment(Pos pos) {
        BorderPane.setAlignment(getSelf(), pos);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T withStyle(String style) {
        getSelf().setStyle(getSelf().getStyle().concat(" " + style));
        return (T) this;
    }

}
