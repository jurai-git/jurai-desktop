package com.jurai.ui.controls.fluent;

import com.jurai.util.Ref;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public interface FluentFX<T extends FluentFX<T>> {
    Node getSelf();

    @SuppressWarnings("unchecked")
    default T withStyleClass(String ...classes) {
        getSelf().getStyleClass().addAll(classes);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T withVgrow(Priority p) {
        VBox.setVgrow(getSelf(), p);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T withHgrow(Priority p) {
        HBox.setHgrow(getSelf(), p);
        return (T) this;
    }

    default T hGrow() {
        return withHgrow(Priority.ALWAYS);
    }

    default T vGrow() {
        return withVgrow(Priority.ALWAYS);
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

    @SuppressWarnings("unchecked")
    default T withVboxMargin(Insets margin) {
        VBox.setMargin(getSelf(), margin);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T withHboxMargin(Insets margin) {
        HBox.setMargin(getSelf(), margin);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T grabInstance(Ref<T> ref) {
        ref.value = (T) this;
        return (T) this;
    }
}
