package com.jurai.ui.animation;

import javafx.beans.property.Property;

public class PropertyValuePair<T> {
    public final Property<T> property;
    public final T value;

    public PropertyValuePair(Property<T> property, T value) {
        this.property = property;
        this.value = value;
    }
}