package com.jurai.ui.animation;

import javafx.beans.property.Property;

public class PropertyPair<T> {
    public Property<T> p1;
    public Property<T> p2;

    public PropertyPair(Property<T> p1, Property<T> p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
