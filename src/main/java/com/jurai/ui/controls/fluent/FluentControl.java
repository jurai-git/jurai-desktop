package com.jurai.ui.controls.fluent;

import javafx.scene.control.Control;

import java.util.function.Consumer;

public interface FluentControl<T extends FluentControl<T>> extends FluentFX<T> {
    Control getSelf();

    @SuppressWarnings("unchecked")
    default T applyCustomFunction(Consumer<T> consumer) {
        consumer.accept((T) this);
        return (T) this;
    }
}
