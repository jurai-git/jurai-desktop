package com.jurai.ui.panes.layout;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;

public class StackPaneConstraints {
    public DoubleProperty x = new SimpleDoubleProperty();
    public DoubleProperty y = new SimpleDoubleProperty();
    public DoubleProperty w = new SimpleDoubleProperty();
    public DoubleProperty h = new SimpleDoubleProperty();

    public StackPaneConstraints(DoubleBinding x, DoubleBinding y, DoubleBinding w, DoubleBinding h) {
        this.w.bind(w);
        this.x.bind(x);
        this.y.bind(y);
        this.h.bind(h);
    }
}
