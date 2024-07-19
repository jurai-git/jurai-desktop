package io.jurai.ui.animation;

import javafx.beans.binding.Binding;
import javafx.beans.property.Property;

public class PropertyBindPair<T> {
    public Property<T> p;
    public Binding<T> b;

    public PropertyBindPair(Property<T> p, Binding<T> b) {
        this.p = p;
        this.b = b;
    }
}
